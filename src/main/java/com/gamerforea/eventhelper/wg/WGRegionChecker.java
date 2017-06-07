package com.gamerforea.eventhelper.wg;

import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Location;
import org.bukkit.World;

public final class WGRegionChecker {

	private static final RegionQuery query = ((WorldGuardPlugin)WGReflection.getWGPlugin()).getRegionContainer().createQuery();

	public static final boolean isInPrivate(World world, int x, int y, int z) throws Throwable {
		return query.getApplicableRegions(new Location(world, x, y, z)).size() > 0;
	}

}
