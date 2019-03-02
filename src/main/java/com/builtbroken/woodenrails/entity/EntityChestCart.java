package com.builtbroken.woodenrails.entity;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.handler.EntityHandler;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityChestCart extends EntityContainerCart {

	public EntityChestCart(World world) {
		super(EntityHandler.MINECART_CHEST, world);
	}

	public EntityChestCart(World world, double x, double y, double z) {
		super(EntityHandler.MINECART_CHEST, world, x, y, z);
	}

	@Override
	public void killMinecart(DamageSource source) {
		super.killMinecart(source);
		if (world.getGameRules().getBoolean("doEntityDrops")) {
			this.entityDropItem(Blocks.CHEST);
		}

	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.CHEST;
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public EntityType<?> getType() {
		return getCartType().getEntityType();
	}

	@Override
	public IBlockState getDefaultDisplayTile() {
		return Blocks.CHEST.getDefaultState().with(BlockChest.FACING, EnumFacing.NORTH);
	}

	@Override
	public int getDefaultDisplayTileOffset() {
		return 8;
	}

	@Override
	public Type getMinecartType() {
		return Type.CHEST;
	}

	@Override
	public String getGuiID() {
		return "minecraft:chest";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		addLoot(playerIn);
		return new ContainerChest(playerInventory, this, playerIn);
	}

}
