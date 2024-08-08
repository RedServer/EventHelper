package com.gamerforea.eventhelper.inject.wg;

import com.gamerforea.eventhelper.inject.PluginInjection;
import com.gamerforea.eventhelper.util.InjectionUtils;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class WGInjection
{
	public static PluginInjection getInjection()
	{
		Class<?> clazz = InjectionUtils.injectClass("WorldGuard", WGInjection.class);
		if (clazz != null)
			try
			{
				return (PluginInjection) clazz.newInstance();
			}
			catch (Throwable throwable)
			{
				throwable.printStackTrace();
			}
		return null;
	}

	public static final class Inj implements PluginInjection
	{
		private final RegionQuery query; // TheAndrey: Add field

		public Inj()
		{
			query = WorldGuardPlugin.inst().getRegionContainer().createQuery(); // TheAndrey: Use RegionQuery
		}

		@Override
		public boolean isInPrivate(World world, int x, int y, int z)
		{
			return query.getApplicableRegions(new Location(world, x, y, z)).size() > 0;
		}

		@Override
		public boolean isPrivateMember(Player player, int x, int y, int z)
		{
			WorldGuardPlugin wg = WorldGuardPlugin.inst();
			return query.getApplicableRegions(new Location(player.getWorld(), x, y, z)).isMemberOfAll(wg.wrapPlayer(player, true));
		}

		@Override
		public boolean isPrivateOwner(Player player, int x, int y, int z)
		{
			WorldGuardPlugin wg = WorldGuardPlugin.inst();
			return query.getApplicableRegions(new Location(player.getWorld(), x, y, z)).isOwnerOfAll(wg.wrapPlayer(player, true));
		}
	}
}
