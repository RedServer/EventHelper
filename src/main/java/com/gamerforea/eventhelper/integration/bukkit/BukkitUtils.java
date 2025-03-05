package com.gamerforea.eventhelper.integration.bukkit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;

public final class BukkitUtils
{
	private static final String craftPackage;
	private static final MethodHandle getBukkitEntity;

	@Nullable
	public static Player getPlayer(@Nullable EntityPlayer player)
	{
		return (Player) getEntity(player);
	}

	@Nullable
	public static org.bukkit.entity.Entity getEntity(@Nullable Entity entity)
	{
		if (entity == null)
			return null; // TheAndrey: null -> null

		try
		{
			return (org.bukkit.entity.Entity) getBukkitEntity.bindTo(entity).invoke();
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Unable to invoke getBukkitEntity() on " + entity, e);
		}
	}

	@Nonnull
	public static BlockFace getBlockFace(@Nonnull EnumFacing side)
	{
		switch (side)
		{
			case DOWN:
				return BlockFace.DOWN;
			case UP:
				return BlockFace.UP;
			case NORTH:
				return BlockFace.NORTH;
			case SOUTH:
				return BlockFace.SOUTH;
			case WEST:
				return BlockFace.WEST;
			case EAST:
				return BlockFace.EAST;
			default:
				return BlockFace.SELF;
		}
	}

	static
	{
		craftPackage = Objects.requireNonNull(Bukkit.getServer(), "CraftServer is null")
				.getClass().getPackage().getName(); // TheAndrey: Automatic package detection

		try
		{
			// TheAndrey: Use MethodHandles
			MethodHandles.Lookup lookup = MethodHandles.publicLookup();
			Class<?> craftEntity = getCraftClass("entity.CraftEntity");

			getBukkitEntity = lookup.findVirtual(Entity.class, "getBukkitEntity", MethodType.methodType(craftEntity));
		}
		catch (Throwable throwable)
		{
			throw new RuntimeException("Failed hooking CraftBukkit methods!", throwable);
		}
	}

	public static Class<?> getCraftClass(String name) throws ClassNotFoundException
	{
		return Class.forName(craftPackage + '.' + name);
	}
}
