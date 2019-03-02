package com.builtbroken.woodenrails.entity;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.container.ContainerCartWorkbench;
import com.builtbroken.woodenrails.handler.EntityHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityWorkbenchCart extends EntityWoodenCart implements IInteractionObject {

	public EntityWorkbenchCart(World world) {
		super(EntityHandler.MINECART_WORKBENCH, world);
	}

	public EntityWorkbenchCart(World world, double xx, double yy, double zz) {
		super(EntityHandler.MINECART_WORKBENCH, world, xx, yy, zz);
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			NetworkHooks.openGui((EntityPlayerMP) player, this, buf -> {
				buf.writeInt(getEntityId());
			});

		}
		return true;
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.WORKBENCH;
	}

	@Override
	public IBlockState getDefaultDisplayTile() {
		return Blocks.CRAFTING_TABLE.getDefaultState();
	}

	@Override
	public Type getMinecartType() {
		return Type.COMMAND_BLOCK;
	}

	@Override
	public EntityType<?> getType() {
		return getCartType().getEntityType();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {
		return new ContainerCartWorkbench(playerInventory, getEntityWorld(), getPosition());
	}

	@Override
	public String getGuiID() {
		return WoodenRails.MOD_ID + ":crafting_table";
	}

	@Override
	public ITextComponent getName() {
		return new TextComponentTranslation(Blocks.CRAFTING_TABLE.getTranslationKey() + ".name");
	}
}
