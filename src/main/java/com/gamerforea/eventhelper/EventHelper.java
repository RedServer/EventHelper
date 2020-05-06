package com.gamerforea.eventhelper;

import java.io.File;
import java.util.List;
import com.gamerforea.eventhelper.util.FastUtils;
import com.gamerforea.eventhelper.wg.WGReflection;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredListener;

@SideOnly(Side.SERVER)
@Mod(modid = "EventHelper", name = "EventHelper", version = "@VERSION@", acceptableRemoteVersions = "*")
public final class EventHelper
{
	public static final File cfgDir = new File(Loader.instance().getConfigDir(), "Events");
	public static final List<RegisteredListener> listeners = Lists.newArrayList();
	public static String craftPackage;
	public static Logger logger;

	@EventHandler
	public final void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@EventHandler
	public final void serverStarted(FMLServerStartedEvent event)
	{
		craftPackage = Bukkit.getServer().getClass().getPackage().getName();
		Configuration cfg = FastUtils.getConfig("EventHelper");
		String[] plugins = cfg.getStringList("plugins", Configuration.CATEGORY_GENERAL, new String[] { "WorldGuard" }, "Plugins for sending events");
		boolean wgHooking = cfg.getBoolean("wgHooking", Configuration.CATEGORY_GENERAL, true, "Hooking WorldGuard plugin (allow checking regions)");
		cfg.save();

		PluginManager plManager = Bukkit.getPluginManager();
		for (String plName : plugins)
			listeners.addAll(HandlerList.getRegisteredListeners(plManager.getPlugin(plName)));
		if (wgHooking) {
			Plugin wg = plManager.getPlugin("WorldGuard");
			if(wg != null) {
				WGReflection.setWG(wg);
			} else {
				logger.warn("WorldGuard plugin not found!");
			}
		}
	}

	public static void callEvent(Event event) {
		for(RegisteredListener listener : listeners) {
			try {
				listener.callEvent(event);
			} catch (Throwable ex) {
				logger.log(Level.ERROR, "Error calling '" + listener + "' listener", ex);
			}
		}
	}
}
