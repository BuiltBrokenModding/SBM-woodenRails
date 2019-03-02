package com.builtbroken.woodenrails.entity;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.handler.EntityHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityEmptyCart extends EntityWoodenCart {
	public EntityEmptyCart(World world) {
		super(EntityHandler.MINECART_EMPTY, world);
	}

	public EntityEmptyCart(World world, double xx, double yy, double zz) {
		super(EntityHandler.MINECART_EMPTY, world, xx, yy, zz);
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.EMPTY;
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		// Not implemented in 1.13
		/*if (MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, player, hand)))
		    return true;*/
		if (isBeingRidden() && getRidingEntity() instanceof EntityPlayer && getRidingEntity() != player) {
			return true;
		}
		else if (isBeingRidden() && getRidingEntity() != player) {
			return false;
		}
		else {
			if (!world.isRemote) {
				player.startRiding(this);
			}

			return true;
		}
	}

	@Override
	public Type getMinecartType() {
		return Type.RIDEABLE;
	}

}
