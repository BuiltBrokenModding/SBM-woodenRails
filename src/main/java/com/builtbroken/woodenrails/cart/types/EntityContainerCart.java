package com.builtbroken.woodenrails.cart.types;

import com.builtbroken.woodenrails.cart.EntityWoodenCart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * Created by Dark on 8/11/2015.
 */
public abstract class EntityContainerCart extends EntityWoodenCart implements IInventory
{
    private ItemStack[] minecartContainerItems = new ItemStack[36];
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

                while (itemstack.stackSize > 0)
                {
                    int j = this.rand.nextInt(21) + 10;

                    if (j > itemstack.stackSize)
                    {
                        j = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j;
                    EntityItem entityitem = new EntityItem(this.worldObj, this.posX + (double) f, this.posY + (double) f1, this.posZ + (double) f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (double) ((float) this.rand.nextGaussian() * f3);
                    entityitem.motionY = (double) ((float) this.rand.nextGaussian() * f3 + 0.2F);
                    entityitem.motionZ = (double) ((float) this.rand.nextGaussian() * f3);
                    this.worldObj.spawnEntityInWorld(entityitem);
                }
            }
        }
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return this.minecartContainerItems[p_70301_1_];
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.minecartContainerItems[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.minecartContainerItems[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.minecartContainerItems[p_70298_1_];
                this.minecartContainerItems[p_70298_1_] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.minecartContainerItems[p_70298_1_].splitStack(p_70298_2_);

                if (this.minecartContainerItems[p_70298_1_].stackSize == 0)
                {
                    this.minecartContainerItems[p_70298_1_] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        if (this.minecartContainerItems[p_70304_1_] != null)
        {
            ItemStack itemstack = this.minecartContainerItems[p_70304_1_];
            this.minecartContainerItems[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.minecartContainerItems[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return this.isDead ? false : p_70300_1_.getDistanceSqToEntity(this) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.func_95999_t() : "container.minecart";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void travelToDimension(int p_71027_1_)
    {
        this.dropContentsWhenDead = false;
        super.travelToDimension(p_71027_1_);
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

                    while (itemstack.stackSize > 0)
                    {
                        int j = this.rand.nextInt(21) + 10;

                        if (j > itemstack.stackSize)
                        {
                            j = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j;
                        EntityItem entityitem = new EntityItem(this.worldObj, this.posX + (double) f, this.posY + (double) f1, this.posZ + (double) f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double) ((float) this.rand.nextGaussian() * f3);
                        entityitem.motionY = (double) ((float) this.rand.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double) ((float) this.rand.nextGaussian() * f3);
                        this.worldObj.spawnEntityInWorld(entityitem);
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

        for (int i = 0; i < this.minecartContainerItems.length; ++i)
        {
            if (this.minecartContainerItems[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.minecartContainerItems[i].writeToNBT(nbttagcompound1);
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
        this.minecartContainerItems = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.minecartContainerItems.length)
            {
                this.minecartContainerItems[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
    public boolean interactFirst(EntityPlayer p_130002_1_)
    {
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartInteractEvent(this, p_130002_1_)))
            return true;
        if (!this.worldObj.isRemote)
        {
            p_130002_1_.displayGUIChest(this);
        }

        return true;
    }

    @Override
    protected void applyDrag()
    {
        int i = 15 - Container.calcRedstoneFromInventory(this);
        float f = 0.98F + (float) i * 0.001F;
        this.motionX *= (double) f;
        this.motionY *= 0.0D;
        this.motionZ *= (double) f;
    }
}
