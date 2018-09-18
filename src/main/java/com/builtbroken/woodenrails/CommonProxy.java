package com.builtbroken.woodenrails;

import com.builtbroken.woodenrails.cart.EntityWoodenCart;
import com.builtbroken.woodenrails.cart.EnumCartTypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by Dark on 7/25/2015.
 */
public class CommonProxy implements IGuiHandler
{
    public void preInit()
    {

    }

    public void init()
    {

    }

    public void postInit()
    {

    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == 0)
        {
            Entity entity = world.getEntityByID(x);
            if (entity instanceof EntityWoodenCart && ((EntityWoodenCart) entity).getCartType() == EnumCartTypes.HOPPER)
                return new ContainerHopper(player.inventory, (IInventory) entity, player);
            else
                WoodenRails.LOGGER.error("Unknown entity[" + x + "," + entity + "] attempted to open a Hopper Gui ");
        }
        else if (ID == 1)
        {
            //ContainerWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn)
            return new ContainerWorkbench(player.inventory, world, player.getPosition());
        }
        else
        {
            //Erroring because of Line 47
            WoodenRails.LOGGER.error("Unknown Gui ID " + ID + " was opened at Dim@" + world.provider + " " + x + "x " + y + "y " + z + "z ");
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }
}
