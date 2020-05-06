package com.gamerforea.eventhelper.util;

import java.io.File;

import com.gamerforea.eventhelper.EventHelper;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public final class FastUtils
{
	public static Configuration getConfig(String cfgName)
	{
		Configuration cfg = new Configuration(new File(EventHelper.cfgDir, cfgName + ".cfg"));
		cfg.load();
		return cfg;
	}

	@SuppressWarnings("unchecked")
	public static boolean isOnline(EntityPlayer player)
	{
		if (player instanceof FakePlayer)
			return true;

		for (EntityPlayer playerOnline : (Iterable<EntityPlayer>) getServer().getConfigurationManager().playerEntityList)
			if (playerOnline.equals(player))
				return true;

		return false;
	}

	public static FakePlayer getFake(World world, FakePlayer fake)
	{
		fake.worldObj = world == null ? getEntityWorld() : world;
		return fake;
	}

	public static FakePlayer getFake(World world, GameProfile profile)
	{
		return getFake(world, FakePlayerFactory.get((WorldServer) (world == null ? getEntityWorld() : world), profile));
	}

	public static EntityPlayer getLivingPlayer(EntityLivingBase entity, FakePlayer modFake)
	{
		return entity instanceof EntityPlayer ? (EntityPlayer) entity : getFake(entity == null ? null : entity.worldObj, modFake);
	}

	public static EntityPlayer getLivingPlayer(EntityLivingBase entity, GameProfile modFakeProfile)
	{
		return entity instanceof EntityPlayer ? (EntityPlayer) entity : getFake(entity == null ? null : entity.worldObj, modFakeProfile);
	}

	public static EntityPlayer getThrowerPlayer(EntityThrowable entity, FakePlayer modFake)
	{
		return getLivingPlayer(entity.getThrower(), modFake);
	}

	public static EntityPlayer getThrowerPlayer(EntityThrowable entity, GameProfile modFakeProfile)
	{
		return getLivingPlayer(entity.getThrower(), modFakeProfile);
	}

	public static EntityLivingBase getThrower(EntityThrowable entity, FakePlayer modFake)
	{
		EntityLivingBase thrower = entity.getThrower();
		return thrower != null ? thrower : getFake(entity == null ? null : entity.worldObj, modFake);
	}

	public static EntityLivingBase getThrower(EntityThrowable entity, GameProfile modFakeProfile)
	{
		EntityLivingBase thrower = entity.getThrower();
		return thrower != null ? thrower : getFake(entity == null ? null : entity.worldObj, modFakeProfile);
	}

	private static MinecraftServer getServer()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}

	private static World getEntityWorld()
	{
		return getServer().getEntityWorld();
	}
}
