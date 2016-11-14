package com.builtbroken.woodenrails.cart;

import com.builtbroken.woodenrails.WoodenRails;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

/**
 * Created by Dark on 7/25/2015.
 */
public abstract class EntityWoodenCart extends EntityMinecart
{
	private static final DataParameter<Integer> CART_COLOR = EntityDataManager.<Integer>createKey(EntityWoodenCart.class, DataSerializers.VARINT);
	
    //TODO add fire damage to cart
    //TODO reduce max speed
    //TODO allow breaking on impact, add config for this option as well
    //TODO add minecart types
    //TODO have coal powered cart catch fire randomly, have tool tip "This doesn't look very safe"
    public EntityWoodenCart(World world)
    {
        super(world);
    }

    public EntityWoodenCart(World world, double xx, double yy, double zz)
    {
        super(world, xx, yy, zz);
    }

    @Override
    public ItemStack getCartItem()
    {
        ItemStack stack = new ItemStack(WoodenRails.itemWoodCart, 1, getCartType().ordinal());
        if (getBlockRenderColor() != -1)
        {
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setInteger("rgb", getBlockRenderColor());
        }
        return stack;
    }

    public abstract EnumCartTypes getCartType();

    @Override
    public boolean canBeRidden()
    {
        return false;
    }

    @Override
    public void killMinecart(DamageSource p_94095_1_)
    {
        this.setDead();
        if (!p_94095_1_.isExplosion())
        {
            this.entityDropItem(getCartItem(), 0.0F);
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        //dataManager??
        this.dataManager.set(CART_COLOR, Integer.valueOf(-1));
    }

    public void setBlockRenderColor(int color)
    {
    	this.dataManager.set(CART_COLOR, Integer.valueOf(-1));
    }

    public int getBlockRenderColor()
    {
    	return this.dataManager.get(CART_COLOR);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);

        //Read block render color
        if (nbt.hasKey("blockRenderColor"))
            setBlockRenderColor(nbt.getInteger("blockRenderColor"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);

        if (getBlockRenderColor() != -1)
        {
            nbt.setInteger("blockRenderColor", getBlockRenderColor());
        }
    }

	public Vec3d func_70489_a(double d3, double d4, double d5) {
		return null;
	}

	public Vec3i func_70495_a(double d3, double d4, double d5, double d6) {
		return null;
	}
}
