package com.gamerforea.eventhelper.wg;

import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WGBukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class WGRegionChecker {

	/**
	 * Данный класс загружается через Plugin ClassLoader, чтобы иметь врзможность работы с плагином
	 */
	public static class Inj implements IRegionChecker {

		private final RegionQuery query;

		public Inj() {
			query = WGBukkit.getPlugin().getRegionContainer().createQuery();
		}

		@Override
		public boolean isInPrivate(World world, int x, int y, int z) {
			return query.getApplicableRegions(new Location(world, x, y, z)).size() > 0;
		}
	}

}
