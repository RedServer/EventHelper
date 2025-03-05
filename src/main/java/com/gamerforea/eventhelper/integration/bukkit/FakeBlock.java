package com.gamerforea.eventhelper.integration.bukkit;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * Used to simulate the placement of a new block.
 * @author TheAndrey
 */
public final class FakeBlock implements Block
{

	private final Block origin;
	private Material cachedType;
	private byte cachedMeta;

	/**
	 * @param origin Origin block in the world
	 * @param type New block type
	 * @param metadata New block metadata
	 */
	public FakeBlock(Block origin, Material type, byte metadata)
	{
		this.origin = Objects.requireNonNull(origin, "block");
		this.cachedType = Objects.requireNonNull(type, "material");
		this.cachedMeta = metadata;
	}

	// ======== Overridden methods ========
	@Override
	public byte getData()
	{
		return cachedMeta;
	}

	@Override
	public Material getType()
	{
		return cachedType;
	}

	@Override
	@SuppressWarnings("deprecation")
	public int getTypeId()
	{
		return cachedType.getId();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setData(byte data)
	{
		origin.setData(data);
		cachedMeta = data;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setData(byte data, boolean applyPhysics)
	{
		origin.setData(data, applyPhysics);
		cachedMeta = data;
	}

	@Override
	public void setType(Material type)
	{
		origin.setType(type);
		this.cachedType = type;
	}

	@Override
	public void setType(Material type, boolean applyPhysics)
	{
		origin.setType(type, applyPhysics);
		cachedType = origin.getType();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean setTypeId(int id)
	{
		boolean result = origin.setTypeId(id);
		cachedType = origin.getType();

		return result;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean setTypeId(int id, boolean applyPhysics)
	{
		boolean result = origin.setTypeId(id, applyPhysics);
		cachedType = origin.getType();

		return result;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean setTypeIdAndData(int id, byte data, boolean applyPhysics)
	{
		boolean result = origin.setTypeIdAndData(id, data, applyPhysics);
		cachedType = origin.getType();
		cachedMeta = data;

		return result;
	}

	@Override
	public boolean isEmpty()
	{
		// Copied from CraftBlock
		return cachedType == Material.AIR;
	}

	@Override
	public boolean isLiquid()
	{
		// Copied from CraftBlock
		return cachedType == Material.WATER || cachedType == Material.STATIONARY_WATER || cachedType == Material.LAVA || cachedType == Material.STATIONARY_LAVA;
	}

	// ======== Proxy methods ========
	@Override
	public Block getRelative(int modX, int modY, int modZ)
	{
		return origin.getRelative(modX, modY, modZ);
	}

	@Override
	public Block getRelative(BlockFace face)
	{
		return origin.getRelative(face);
	}

	@Override
	public Block getRelative(BlockFace face, int distance)
	{
		return origin.getRelative(face, distance);
	}

	@Override
	public byte getLightLevel()
	{
		return origin.getLightLevel();
	}

	@Override
	public byte getLightFromSky()
	{
		return origin.getLightFromSky();
	}

	@Override
	public byte getLightFromBlocks()
	{
		return origin.getLightFromBlocks();
	}

	@Override
	public World getWorld()
	{
		return origin.getWorld();
	}

	@Override
	public int getX()
	{
		return origin.getX();
	}

	@Override
	public int getY()
	{
		return origin.getY();
	}

	@Override
	public int getZ()
	{
		return origin.getZ();
	}

	@Override
	public Location getLocation()
	{
		return origin.getLocation();
	}

	@Override
	public Location getLocation(Location loc)
	{
		return origin.getLocation(loc);
	}

	@Override
	public Chunk getChunk()
	{
		return origin.getChunk();
	}

	@Override
	public BlockFace getFace(Block block)
	{
		return block.getFace(block);
	}

	@Override
	public BlockState getState()
	{
		return origin.getState();
	}

	@Override
	public Biome getBiome()
	{
		return origin.getBiome();
	}

	@Override
	public void setBiome(Biome bio)
	{
		origin.setBiome(bio);
	}

	@Override
	public boolean isBlockPowered()
	{
		return origin.isBlockPowered();
	}

	@Override
	public boolean isBlockIndirectlyPowered()
	{
		return origin.isBlockIndirectlyPowered();
	}

	@Override
	public boolean isBlockFacePowered(BlockFace face)
	{
		return origin.isBlockFacePowered(face);
	}

	@Override
	public boolean isBlockFaceIndirectlyPowered(BlockFace face)
	{
		return origin.isBlockFacePowered(face);
	}

	@Override
	public int getBlockPower(BlockFace face)
	{
		return origin.getBlockPower(face);
	}

	@Override
	public int getBlockPower()
	{
		return origin.getBlockPower();
	}

	@Override
	public double getTemperature()
	{
		return origin.getTemperature();
	}

	@Override
	public double getHumidity()
	{
		return origin.getHumidity();
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction()
	{
		return origin.getPistonMoveReaction();
	}

	@Override
	public boolean breakNaturally()
	{
		return origin.breakNaturally();
	}

	@Override
	public boolean breakNaturally(ItemStack tool)
	{
		return origin.breakNaturally(tool);
	}

	@Override
	public Collection<ItemStack> getDrops()
	{
		return origin.getDrops();
	}

	@Override
	public Collection<ItemStack> getDrops(ItemStack tool)
	{
		return origin.getDrops(tool);
	}

	@Override
	public void setMetadata(String metadataKey, MetadataValue newMetadataValue)
	{
		origin.setMetadata(metadataKey, newMetadataValue);
	}

	@Override
	public List<MetadataValue> getMetadata(String metadataKey)
	{
		return origin.getMetadata(metadataKey);
	}

	@Override
	public boolean hasMetadata(String metadataKey)
	{
		return origin.hasMetadata(metadataKey);
	}

	@Override
	public void removeMetadata(String metadataKey, Plugin owningPlugin)
	{
		origin.removeMetadata(metadataKey, owningPlugin);
	}
}
