package com.builtbroken.woodenrails.cart.types;

import com.builtbroken.woodenrails.cart.EntityWoodenCart;
import com.builtbroken.woodenrails.cart.EnumCartTypes;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityEmptyCart extends EntityWoodenCart
{
    public EntityEmptyCart(World world)
    {
        super(world);
    }

    public EntityEmptyCart(World world, double xx, double yy, double zz)
    {
        super(world, xx, yy, zz);
    }

    @Override
    public EnumCartTypes getCartType()
    {
        return EnumCartTypes.EMPTY;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, player, hand)))
            return true;
        if (this.isBeingRidden() && this.getRidingEntity() instanceof EntityPlayer && this.getRidingEntity() != player)
        {
            return true;
        }
        else if (this.isBeingRidden() && this.getRidingEntity() != player)
        {
            return false;
        }
        else
        {
            if (!this.world.isRemote)
            {
                player.startRiding(this);
            }

            return true;
        }
    }

    @Override
    public EntityMinecart.Type getType()
    {
        return EntityMinecart.Type.RIDEABLE;
    }

}
