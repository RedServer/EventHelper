package com.gamerforea.eventhelper.util;

import com.gamerforea.eventhelper.EventHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import javax.annotation.Nullable;

public final class ConvertUtils
{
	private static final MethodHandle getWorld;
	private static final MethodHandle getBukkitEntity;
	private static final MethodHandle asCraftMirror;

	@Nullable
	public static org.bukkit.entity.Entity toBukkitEntity(@Nullable Entity entity)
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

	@Nullable
	public static Player toBukkitEntity(@Nullable EntityPlayer player)
	{
		return (Player) toBukkitEntity((Entity) player);
	}

	@Nullable
	public static org.bukkit.World toBukkitWorld(@Nullable World world)
	{
		if (world == null)
			return null; // TheAndrey: null -> null

		try
		{
			return (org.bukkit.World) getWorld.bindTo(world).invoke();
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Unable to invoke getWorld() on " + world, e);
		}
	}

	@Nullable
	public static org.bukkit.inventory.ItemStack toBukkitItemStackMirror(@Nullable ItemStack stack)
	{
		if (stack == null)
			return null; // TheAndrey: null -> null

		try
		{
			return (org.bukkit.inventory.ItemStack) asCraftMirror.invoke(stack);
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Unable to invoke asCraftMirror() with " + stack, e);
		}
	}

	public static BlockFace toBukkitFace(ForgeDirection direction)
	{
		switch (direction)
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
		try
		{
			// TheAndrey: Use MethodHandles
			MethodHandles.Lookup lookup = MethodHandles.publicLookup();
			Class<?> craftWorld = getCraftClass("CraftWorld");
			Class<?> craftEntity = getCraftClass("entity.CraftEntity");
			Class<?> craftStack = getCraftClass("inventory.CraftItemStack");

			getWorld = lookup.findVirtual(World.class, "getWorld", MethodType.methodType(craftWorld));
			getBukkitEntity = lookup.findVirtual(Entity.class, "getBukkitEntity", MethodType.methodType(craftEntity));
			asCraftMirror = lookup.findStatic(craftStack, "asCraftMirror", MethodType.methodType(craftStack, ItemStack.class));
		}
		catch (Throwable throwable)
		{
			throw new RuntimeException("Failed hooking CraftBukkit methods!", throwable);
		}
	}

	public static Class<?> getCraftClass(String name) throws ClassNotFoundException
	{
		return Class.forName(EventHelper.craftPackage + '.' + name);
	}
}
