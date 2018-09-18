package com.builtbroken.woodenrails.cart.types;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.cart.EntityWoodenCart;
import com.builtbroken.woodenrails.cart.EnumCartTypes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityWorkbenchCart extends EntityWoodenCart
{
    public EntityWorkbenchCart(World world)
    {
        super(world);
    }

    public EntityWorkbenchCart(World world, double xx, double yy, double zz)
    {
        super(world, xx, yy, zz);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (world.isRemote)
        {
            player.openGui(WoodenRails.INSTANCE, 1, world, getEntityId(), 0, 0);
        }
        return true;
    }

    @Override
    public EnumCartTypes getCartType()
    {
        return EnumCartTypes.WORKBENCH;
    }

    @Override
    public IBlockState getDisplayTile()
    {
        return Blocks.CRAFTING_TABLE.getDefaultState();
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
