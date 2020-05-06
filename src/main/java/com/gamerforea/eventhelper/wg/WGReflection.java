package com.gamerforea.eventhelper.wg;

import com.gamerforea.eventhelper.EventHelper;
import com.gamerforea.eventhelper.util.InjectionUtils;
import com.google.common.base.Preconditions;
import org.bukkit.plugin.Plugin;

public final class WGReflection {

	private static IRegionChecker regionChecker;

	public static void setWG(Plugin plugin) {
		Preconditions.checkNotNull(plugin, "WorldGuard not installed!");

		try {
			Class<?> clazz = InjectionUtils.injectClass(plugin.getClass().getClassLoader(), WGRegionChecker.class);
			regionChecker = (IRegionChecker)clazz.newInstance();

			EventHelper.logger.info("WG Region checker created");
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("Unable to create region checker", e);
		}
	}

	public static IRegionChecker getRegionChecker() {
		return regionChecker;
	}

}
