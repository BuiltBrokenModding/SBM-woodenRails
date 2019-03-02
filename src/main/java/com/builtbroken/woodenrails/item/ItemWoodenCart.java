package com.builtbroken.woodenrails.item;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nullable;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.block.BlockWoodenRail;
import com.builtbroken.woodenrails.client.render.RenderWoodenCart;
import com.builtbroken.woodenrails.entity.*;
import com.builtbroken.woodenrails.handler.ItemHandler;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

/**
 * Created by Dark on 7/25/2015.
 */
public abstract class ItemWoodenCart extends Item {

	public boolean enableColoredChestSupport;

	public ItemWoodenCart() {
		super(ItemHandler.getItemProps().maxStackSize(3));
		setRegistryName(WoodenRails.MOD_ID, getCartType().name);
		BlockDispenser.registerDispenseBehavior(this, new DispenserMinecartBehavior());
		enableColoredChestSupport = ModList.get().isLoaded("coloredchests");
	}

	public abstract EnumCartTypes getCartType();

	@Override
	public EnumActionResult onItemUse(ItemUseContext context) {
		/*
		World world = context.getWorld();
		BlockPos blockpos = context.getPos();
		IBlockState iblockstate = world.getBlockState(blockpos);
		if (!iblockstate.isIn(BlockTags.RAILS) && !(iblockstate.getBlock() instanceof BlockWoodenRail)) {
			return EnumActionResult.FAIL;
		}
		else {
			ItemStack itemstack = context.getItem();
			//if (!world.isRemote) {
			RailShape railshape = iblockstate.getBlock() instanceof BlockWoodenRail ? ((BlockWoodenRail) iblockstate.getBlock()).getRailDirection(iblockstate, world, blockpos, null) : RailShape.NORTH_SOUTH;
			double d0 = 0.0D;
			if (railshape.isAscending()) {
				d0 = 0.5D;
			}

			//EntityMinecart entityminecart = EntityMinecart.create(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.0625D + d0, (double)blockpos.getZ() + 0.5D, this.minecartType);
			EntityWoodenCart entityminecart = createNewCart(world, getCartType());
			if (itemstack.hasDisplayName()) {
				entityminecart.setCustomName(itemstack.getDisplayName());
			}

			world.spawnEntity(entityminecart);
			//}

			itemstack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		*/
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		EntityPlayer player = context.getPlayer();
		if (BlockRailBase.isRail(world.getBlockState(pos)) || world.getBlockState(pos).getBlock() instanceof BlockWoodenRail) {
			ItemStack itemStack = context.getItem();

			EntityWoodenCart cart = createNewCart(world, getCartType());
			if (cart != null) {
				if (!world.isRemote) {
					cart.setPosition(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);

					if (itemStack.hasDisplayName()) {
						cart.setCustomName(itemStack.getDisplayName());
					}

					world.spawnEntity(cart);

					if (itemStack.getTag() != null && itemStack.getTag().contains("rgb")) {
						cart.setBlockRenderColor(itemStack.getTag().getInt("rgb"));
					}
				}
				if (!player.isCreative()) {
					itemStack.setCount(itemStack.getCount() - 1);
				}
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (stack.getDamage() >= 0 && stack.getDamage() < EnumCartTypes.values().length) {
			EnumCartTypes type = EnumCartTypes.values()[stack.getDamage()];
			if (type == EnumCartTypes.HOPPER || type == EnumCartTypes.WORKBENCH || type == EnumCartTypes.TANK) {
				tooltip.add(new TextComponentString("Not implemented"));
			}
		}
		if (stack != null && stack.getTag() != null) {
			if (stack.getTag().contains("rgb")) {
				Color color = RenderWoodenCart.getColor(stack.getTag().getInt("rgb"));
				tooltip.add(new TextComponentString("R: " + color.getRed() + " G: " + color.getGreen() + " B: " + color.getBlue()));
			}

			if (stack.getTag().contains("colorName")) {
				tooltip.add(new TextComponentString("N: " + stack.getTag().getString("colorName")));
			}
		}
	}

	public static EntityWoodenCart createNewCart(World world, EnumCartTypes type) {
		switch (type) {
		case CHEST:
			return new EntityChestCart(world);
		case TNT:
			return new EntityTNTCart(world);
		case FURNACE:
			return new EntityFurnaceCart(world);
		case HOPPER:
			return new EntityHopperCart(world);
		case WORKBENCH:
			return new EntityWorkbenchCart(world);
		case TANK:
			return new EntityTankCart(world);
		case SPAWNER:
			return new EntitySpawnerCart(world);
		case EMPTY:
		default:
			break;
		}
		return type.newEntity(world);
	}
}
