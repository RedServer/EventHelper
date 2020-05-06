package com.gamerforea.eventhelper.wg;

import org.bukkit.World;

public interface IRegionChecker {

	boolean isInPrivate(World world, int x, int y, int z);
}
