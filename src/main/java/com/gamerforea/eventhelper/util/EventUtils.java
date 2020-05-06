package com.gamerforea.eventhelper.util;

import com.gamerforea.eventhelper.EventHelper;
import com.gamerforea.eventhelper.wg.IRegionChecker;
import com.gamerforea.eventhelper.wg.WGReflection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.logging.log4j.Level;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import static com.gamerforea.eventhelper.util.ConvertUtils.*;

public final class EventUtils {

	public static boolean cantBreak(EntityPlayer player, int x, int y, int z) {
		try {
			Player bPlayer = toBukkitEntity(player);
			BlockBreakEvent event = new BlockBreakEvent(bPlayer.getWorld().getBlockAt(x, y, z), bPlayer);
			EventHelper.callEvent(event);
			return event.isCancelled();
		} catch (Throwable e) {
			EventHelper.logger.log(Level.ERROR, String.format("Failed call BlockBreakEvent: [Player: %s, X:%d, Y:%d, Z:%d]", player, x, y, z), e);
			return true;
		}
	}

	public static boolean cantBreak(EntityPlayer player, double x, double y, double z) {
		int xx = MathHelper.floor_double(x);
		int yy = MathHelper.floor_double(y);
		int zz = MathHelper.floor_double(z);
		return cantBreak(player, xx, yy, zz);
	}

	@SuppressWarnings("deprecation")
	public static boolean cantDamage(Entity damager, Entity damagee) {
		try {
			EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(toBukkitEntity(damager), toBukkitEntity(damagee), DamageCause.ENTITY_ATTACK, 0D);
			EventHelper.callEvent(event);
			return event.isCancelled();
		} catch (Throwable e) {
			EventHelper.logger.log(Level.ERROR, String.format("Failed call EntityDamageByEntityEvent: [Damager: %s, Damagee: %s]", damager, damagee), e);
			return true;
		}
	}

	public static boolean cantInteract(EntityPlayer player, ItemStack stack, int x, int y, int z, ForgeDirection side) {
		try {
			Player bPlayer = toBukkitEntity(player);
			PlayerInteractEvent event = new PlayerInteractEvent(bPlayer, Action.RIGHT_CLICK_BLOCK, toBukkitItemStackMirror(stack), bPlayer.getWorld().getBlockAt(x, y, z), toBukkitFace(side));
			EventHelper.callEvent(event);
			return event.isCancelled();
		} catch (Throwable e) {
			EventHelper.logger.log(Level.ERROR, String.format("Failed call PlayerInteractEvent: [Player: %s, Item: %s, X:%d, Y:%d, Z:%d, Side: %s]", player, stack, x, y, z, side), e);
			return true;
		}
	}

	public static boolean cantFromTo(World world, int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
		try {
			org.bukkit.World bWorld = toBukkitWorld(world);
			BlockFromToEvent event = new BlockFromToEvent(bWorld.getBlockAt(fromX, fromY, fromZ), bWorld.getBlockAt(toX, toY, toZ));
			EventHelper.callEvent(event);
			return event.isCancelled();
		} catch (Throwable e) {
			EventHelper.logger.log(Level.ERROR, String.format("Failed call BlockFromToEvent: [FromX: %d, FromY: %d, FromZ: %d, ToX: %d, ToY: %d, ToZ: %d]", fromX, fromY, fromZ, toX, toY, toZ), e);
			return true;
		}
	}

	public static boolean cantFromTo(World world, int fromX, int fromY, int fromZ, ForgeDirection direction) {
		try {
			org.bukkit.World bWorld = toBukkitWorld(world);
			BlockFromToEvent event = new BlockFromToEvent(bWorld.getBlockAt(fromX, fromY, fromZ), toBukkitFace(direction));
			EventHelper.callEvent(event);
			return event.isCancelled();
		} catch (Throwable e) {
			EventHelper.logger.log(Level.ERROR, String.format("Failed call BlockFromToEvent: [FromX: %d, FromY: %d, FromZ: %d, Direction: %s]", fromX, fromY, fromZ, direction), e);
			return true;
		}
	}

	public static boolean isInPrivate(World world, int x, int y, int z) {
		try {
			IRegionChecker checker = WGReflection.getRegionChecker();
			if(checker == null) return false; // No plugin
			return checker.isInPrivate(toBukkitWorld(world), x, y, z);
		} catch (Throwable e) {
			EventHelper.logger.log(Level.ERROR, String.format("Failed to check WG region: [World: %s, X: %d, Y: %d, Z: %d]", world.getWorldInfo().getWorldName(), x, y, z), e);
			return true;
		}
	}

	public static boolean isInPrivate(Entity entity) {
		int x = MathHelper.floor_double(entity.posX);
		int y = MathHelper.floor_double(entity.posY);
		int z = MathHelper.floor_double(entity.posZ);
		return isInPrivate(entity.worldObj, x, y, z);
	}
}
