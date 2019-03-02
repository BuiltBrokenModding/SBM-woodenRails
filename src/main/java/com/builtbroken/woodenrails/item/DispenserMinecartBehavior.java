package com.builtbroken.woodenrails.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Dark on 7/25/2015.
 */
public class DispenserMinecartBehavior extends BehaviorDefaultDispenseItem {

	@Override
	public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {
		if (itemStack != null && itemStack.getItem() instanceof ItemWoodenCart) {
			EnumFacing enumfacing = blockSource.getBlockState().get(BlockDispenser.FACING);
			World world = blockSource.getWorld();

			double xx = blockSource.getX() + enumfacing.getXOffset() * 1.125F;
			double yy = blockSource.getY() + enumfacing.getYOffset() * 1.125F;
			double zz = blockSource.getZ() + enumfacing.getZOffset() * 1.125F;

			double i = blockSource.getX() + enumfacing.getXOffset();
			double j = blockSource.getY() + enumfacing.getYOffset();
			double k = blockSource.getZ() + enumfacing.getZOffset();

			IBlockState block = world.getBlockState(new BlockPos(i, j, k));
			double deltaY;

			if (BlockRailBase.isRail(block)) {
				deltaY = 0.0D;
			}
			else {
				if (block.getMaterial() != Material.AIR || !BlockRailBase.isRail(world.getBlockState(new BlockPos(i, j - 1, k)))) {
					return super.dispenseStack(blockSource, itemStack);
				}

				deltaY = -1.0D;
			}

			EntityMinecart cart = ItemWoodenCart.createNewCart(world, ((ItemWoodenCart) itemStack.getItem()).getCartType());

			cart.setPosition(xx, yy + deltaY, zz);
			if (cart != null) {
				if (itemStack.hasDisplayName()) {
					cart.setCustomName(itemStack.getDisplayName());
				}

				world.spawnEntity(cart);
				playDispenseSound(blockSource);
				itemStack.split(1);
			}
		}
		return itemStack;
	}
}
