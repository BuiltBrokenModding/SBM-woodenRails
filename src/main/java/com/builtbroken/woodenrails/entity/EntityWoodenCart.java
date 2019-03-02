package com.builtbroken.woodenrails.entity;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.handler.EntityHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.*;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * Created by Dark on 7/25/2015.
 */
public abstract class EntityWoodenCart extends EntityMinecart {

	private static final DataParameter<Integer> CART_COLOR = EntityDataManager.<Integer>createKey(EntityWoodenCart.class, DataSerializers.VARINT);

	//TODO add fire damage to cart
	//TODO reduce max speed
	//TODO allow breaking on impact, add config for this option as well
	//TODO add minecart types
	//TODO have coal powered cart catch fire randomly, have tool tip "This doesn't look very safe"
	public EntityWoodenCart(EntityType<?> type, World world) {
		super(type, world);
	}

	public EntityWoodenCart(EntityType<?> type, World world, double xx, double yy, double zz) {
		super(type, world, xx, yy, zz);
	}

	@Override
	public ItemStack getCartItem() {
		ItemStack stack = getCartType().getStack().copy();
		if (getBlockRenderColor() != -1) {
			stack.setTag(new NBTTagCompound());
			stack.getTag().putInt("rgb", getBlockRenderColor());
		}
		return stack;
	}

	public abstract EnumCartTypes getCartType();

	@Override
	public IBlockState getDefaultDisplayTile() {
		return Blocks.AIR.getDefaultState();
	}

	@Override
	public IBlockState getDisplayTile() {
		return getDefaultDisplayTile();
	}

	@Override
	public boolean hasDisplayTile() {
		return false;
	}

	@Override
	public EntityType<?> getType() {
		return EntityHandler.MINECART_EMPTY;
	}

	@Override
	public boolean canBeRidden() {
		return false;
	}

	@Override
	public void killMinecart(DamageSource source) {
		if (world.getGameRules().getBoolean("doEntityDrops")) {
			ItemStack itemstack = getCartType().getStack().copy();
			if (hasCustomName()) {
				itemstack.setDisplayName(getCustomName());
			}

			this.entityDropItem(itemstack);
		}
		remove();
		if (!source.isExplosion()) {
			this.entityDropItem(getCartItem(), 0.0F);
		}
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(CART_COLOR, Integer.valueOf(-1));
	}

	public void setBlockRenderColor(int color) {
		dataManager.set(CART_COLOR, Integer.valueOf(-1));
	}

	public int getBlockRenderColor() {
		return dataManager.get(CART_COLOR);
	}

	@Override
	protected void readAdditional(NBTTagCompound nbt) {
		super.readAdditional(nbt);

		//Read block render color
		if (nbt.contains("blockRenderColor")) {
			setBlockRenderColor(nbt.getInt("blockRenderColor"));
		}
	}

	@Override
	protected void writeAdditional(NBTTagCompound nbt) {
		super.writeAdditional(nbt);

		if (getBlockRenderColor() != -1) {
			nbt.putInt("blockRenderColor", getBlockRenderColor());
		}
	}
}
