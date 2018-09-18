package com.builtbroken.woodenrails.cart.types;

import com.builtbroken.woodenrails.cart.EntityWoodenCart;
import com.builtbroken.woodenrails.cart.EnumCartTypes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityFurnaceCart extends EntityWoodenCart
{
    private int fuel;
    public double pushX;
    public double pushZ;
    private static final DataParameter<Byte> CART_OBJECT = EntityDataManager.<Byte>createKey(EntityWoodenCart.class, DataSerializers.BYTE);

    public EntityFurnaceCart(World world)
    {
        super(world);
    }

    public EntityFurnaceCart(World world, double xx, double yy, double zz)
    {
        super(world, xx, yy, zz);
    }

    @Override
    public EnumCartTypes getCartType()
    {
        return EnumCartTypes.FURNACE;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.set(CART_OBJECT, new Byte((byte)0));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.fuel > 0)
        {
            --this.fuel;
        }

        if (this.fuel <= 0)
        {
            this.pushX = this.pushZ = 0.0D;
        }

        this.setMinecartPowered(this.fuel > 0);

        if (this.isMinecartPowered() && this.rand.nextInt(4) == 0)
        {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8D, this.posZ,  0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void killMinecart(DamageSource p_94095_1_)
    {
        super.killMinecart(p_94095_1_);

        if (!p_94095_1_.isExplosion())
        {
            this.entityDropItem(new ItemStack(Blocks.FURNACE, 1), 0.0F);
        }
    }

    @Override
    protected void moveAlongTrack(BlockPos pos, IBlockState state)
    {
        super.moveAlongTrack(pos, state);
        double d2 = this.pushX * this.pushX + this.pushZ * this.pushZ;

        if (d2 > 1.0E-4D && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D)
        {
            d2 = MathHelper.sqrt(d2);
            this.pushX /= d2;
            this.pushZ /= d2;

            if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0D)
            {
                this.pushX = 0.0D;
                this.pushZ = 0.0D;
            }
            else
            {
                this.pushX = this.motionX;
                this.pushZ = this.motionZ;
            }
        }
    }

    @Override
    protected void applyDrag()
    {
        double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;

        if (d0 > 1.0E-4D)
        {
            d0 = MathHelper.sqrt(d0);
            this.pushX /= d0;
            this.pushZ /= d0;
            double d1 = 0.05D;
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.0D;
            this.motionZ *= 0.800000011920929D;
            this.motionX += this.pushX * d1;
            this.motionZ += this.pushZ * d1;
        }
        else
        {
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.0D;
            this.motionZ *= 0.9800000190734863D;
        }

        super.applyDrag();
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, player, hand))) return true;
        ItemStack itemstack = player.inventory.getCurrentItem();

        if (itemstack != null && itemstack.getItem() == Items.COAL)
        {
            if (!player.capabilities.isCreativeMode)
            {
                itemstack.setCount(itemstack.getCount() - 1);
            }

            this.fuel += 3600;
        }

        this.pushX = this.posX - player.posX;
        this.pushZ = this.posZ - player.posZ;
        return true;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setDouble("PushX", this.pushX);
        p_70014_1_.setDouble("PushZ", this.pushZ);
        p_70014_1_.setShort("Fuel", (short)this.fuel);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
        this.pushX = p_70037_1_.getDouble("PushX");
        this.pushZ = p_70037_1_.getDouble("PushZ");
        this.fuel = p_70037_1_.getShort("Fuel");
    }

    protected boolean isMinecartPowered()
    {
        return (this.dataManager.get(CART_OBJECT) & 1) != 0;
    }

    protected void setMinecartPowered(boolean p_94107_1_)
    {
        if (p_94107_1_)
        {
            this.dataManager.set(CART_OBJECT, Byte.valueOf((byte)(this.dataManager.get(CART_OBJECT) | 1)));
        }
        else
        {
            this.dataManager.set(CART_OBJECT, Byte.valueOf((byte)(this.dataManager.get(CART_OBJECT) & -2)));
        }
    }

    @Override
    public IBlockState getDisplayTile()
    {
        return Blocks.LIT_FURNACE.getDefaultState();
    }


    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
