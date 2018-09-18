package com.builtbroken.woodenrails.cart.types;

import com.builtbroken.woodenrails.cart.EntityWoodenCart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;

/**
 * Created by Dark on 8/11/2015.
 */
public abstract class EntityContainerCart extends EntityWoodenCart implements IInventory
{
    private NonNullList<ItemStack> minecartContainerItems = NonNullList.<ItemStack>withSize(36, ItemStack.EMPTY);
    private boolean dropContentsWhenDead = true;

    public EntityContainerCart(World world)
    {
        super(world);
    }

    public EntityContainerCart(World world, double xx, double yy, double zz)
    {
        super(world, xx, yy, zz);
    }

    @Override
    public void killMinecart(DamageSource damage)
    {
        super.killMinecart(damage);

        for (int i = 0; i < this.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.getStackInSlot(i);

            if (itemstack != null)
            {
                float f = this.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.getCount() > 0)
                {
                    int j = this.rand.nextInt(21) + 10;

                    if (j > itemstack.getCount())
                    {
                        j = itemstack.getCount();
                    }

                    itemstack.setCount(itemstack.getCount() - j);
                    EntityItem entityitem = new EntityItem(this.world, this.posX + f, this.posY + f1, this.posZ + f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                    entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                    this.world.spawnEntity(entityitem);
                }
            }
        }
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return this.minecartContainerItems.get(p_70301_1_);
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (!this.minecartContainerItems.get(p_70298_1_).isEmpty())
        {
            ItemStack itemstack;

            if (this.minecartContainerItems.get(p_70298_1_).getCount() <= p_70298_2_)
            {
                itemstack = this.minecartContainerItems.get(p_70298_1_);
                this.minecartContainerItems.set(p_70298_1_, ItemStack.EMPTY);
                return itemstack;
            }
            else
            {
                itemstack = this.minecartContainerItems.get(p_70298_1_).splitStack(p_70298_2_);
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    //    @Override TODO: still needed?
    //    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    //    {
    //        if (!this.minecartContainerItems.get(p_70304_1_).isEmpty())
    //        {
    //            ItemStack itemstack = this.minecartContainerItems.get(p_70304_1_).copy();
    //            this.minecartContainerItems.set(p_70304_1_, ItemStack.EMPTY);
    //            return itemstack;
    //        }
    //        else
    //        {
    //            return null;
    //        }
    //    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.minecartContainerItems.set(p_70299_1_, p_70299_2_);

        if (!p_70299_2_.isEmpty() && p_70299_2_.getCount() > this.getInventoryStackLimit())
        {
            p_70299_2_.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUsableByPlayer(EntityPlayer p_70300_1_)
    {
        return this.isDead ? false : p_70300_1_.getDistanceSq(this) <= 64.0D;
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.getCustomNameTag() : "container.minecart";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public Entity changeDimension(int p_71027_1_)
    {
        this.dropContentsWhenDead = false;
        return super.changeDimension(p_71027_1_);
    }

    @Override
    public void setDead()
    {
        if (this.dropContentsWhenDead)
        {
            for (int i = 0; i < this.getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.getStackInSlot(i);

                if (itemstack != null)
                {
                    float f = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.getCount() > 0)
                    {
                        int j = this.rand.nextInt(21) + 10;

                        if (j > itemstack.getCount())
                        {
                            j = itemstack.getCount();
                        }

                        itemstack.setCount(itemstack.getCount() - j);
                        EntityItem entityitem = new EntityItem(this.world, this.posX + f, this.posY + f1, this.posZ + f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                        entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                        this.world.spawnEntity(entityitem);
                    }
                }
            }
        }

        super.setDead();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.minecartContainerItems.size(); ++i)
        {
            if (!this.minecartContainerItems.get(i).isEmpty())
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.minecartContainerItems.get(i).writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_70014_1_.setTag("Items", nbttaglist);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
        NBTTagList nbttaglist = p_70037_1_.getTagList("Items", 10);
        this.minecartContainerItems.clear();

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.minecartContainerItems.size())
            {
                this.minecartContainerItems.set(j, new ItemStack(nbttagcompound1));
            }
        }
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, player, hand)))
            return true;
        if (!this.world.isRemote)
        {
            player.displayGUIChest(this);
        }

        return true;
    }

    @Override
    protected void applyDrag()
    {
        int i = 15 - Container.calcRedstoneFromInventory(this);
        float f = 0.98F + i * 0.001F;
        this.motionX *= f;
        this.motionY *= 0.0D;
        this.motionZ *= f;
    }

    @Override
    public World getEntityWorld()
    {
        return null;
    }
}
