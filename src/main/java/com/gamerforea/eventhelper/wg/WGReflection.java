package com.gamerforea.eventhelper.wg;


import org.bukkit.plugin.Plugin;

import com.google.common.base.Preconditions;

public final class WGReflection
{
	private static Plugin wgPlugin;
	private static ClassLoader wgClassLoader;

	public static final void setWG(Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "WorldGuard not installed!");
		wgPlugin = plugin;
		wgClassLoader = plugin.getClass().getClassLoader();
	}

	public static final ClassLoader getWGClassLoader()
	{
		if (wgClassLoader == null)
			throw new IllegalStateException("WorldGuard ClassLoader not found!");
		return wgClassLoader;
	}

	public static final Plugin getWGPlugin()
	{
		if (wgPlugin == null)
			throw new IllegalStateException("WorldGuard not found!");
		return wgPlugin;
	}

}
