package com.builtbroken.woodenrails.cart.types;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.cart.EnumCartTypes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityHopperCart extends EntityContainerCart implements IHopper
{
    private boolean isBlocked = true;
    private int transferTicker = -1;

    public EntityHopperCart(World world)
    {
        super(world);
    }

    public EntityHopperCart(World world, double xx, double yy, double zz)
    {
        super(world, xx, yy, zz);
    }

    @Override
    public EnumCartTypes getCartType()
    {
        return EnumCartTypes.HOPPER;
    }

    @Override
    public EntityMinecart.Type getType()
    {
        return EntityMinecart.Type.HOPPER;
    }

    @Override
    public IBlockState getDefaultDisplayTile()
    {
        return Blocks.HOPPER.getDefaultState();
    }

    @Override
    public int getDefaultDisplayTileOffset()
    {
        return 1;
    }

    @Override
    public int getSizeInventory()
    {
        return 5;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, player, hand)))
            return true;
        if (!this.world.isRemote)
        {
            player.openGui(WoodenRails.INSTANCE, 0, world, getEntityId(), 0, 0);
        }

        return true;
    }

    @Override
    public void onActivatorRailPass(int p_96095_1_, int p_96095_2_, int p_96095_3_, boolean p_96095_4_)
    {
        boolean flag1 = !p_96095_4_;

        if (flag1 != this.getBlocked())
        {
            this.setBlocked(flag1);
        }
    }

    public boolean getBlocked()
    {
        return this.isBlocked;
    }

    public void setBlocked(boolean p_96110_1_)
    {
        this.isBlocked = p_96110_1_;
    }

    @Override
    public World getEntityWorld()
    {
        return this.world;
    }

    @Override
    public double getXPos()
    {
        return this.posX;
    }

    @Override
    public double getYPos()
    {
        return this.posY;
    }

    @Override
    public double getZPos()
    {
        return this.posZ;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote && this.isEntityAlive() && this.getBlocked())
        {
            --this.transferTicker;

            if (!this.canTransfer())
            {
                this.setTransferTicker(0);

                if (this.func_96112_aD())
                {
                    this.setTransferTicker(4);
                    this.markDirty();
                }
            }
        }
    }

    public boolean func_96112_aD()
    {
        //TODO: code breaks
        //        if (TileEntityHopper.pullItems(this))
        //        {
        //            return true;
        //        }
        //        else
        //        {
        //            List<EntityItem> list = this.world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(0.25D, 0.0D, 0.25D), EntitySelectors.IS_ALIVE);
        //
        //            if (list.size() > 0)
        //            {
        //                TileEntityHopper.putDropInInventoryAllSlots(null, this, list.get(0)); //source can be null because only entity items are getting pulled
        //            }

        return false;
        //        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("TransferCooldown", this.transferTicker);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        this.transferTicker = nbt.getInteger("TransferCooldown");
    }

    public void setTransferTicker(int p_98042_1_)
    {
        this.transferTicker = p_98042_1_;
    }

    public boolean canTransfer()
    {
        return this.transferTicker > 0;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void closeInventory(EntityPlayer player) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getField(int id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getFieldCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public World getWorld() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEmpty()
    {
        for(int i = 0; i < getSizeInventory(); i++)
        {
            if(!getStackInSlot(i).isEmpty())
                return false;
        }

        return true;
    }
}