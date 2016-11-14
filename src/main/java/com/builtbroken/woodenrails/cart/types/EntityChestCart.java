package com.builtbroken.woodenrails.cart.types;

import com.builtbroken.woodenrails.cart.EnumCartTypes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityChestCart extends EntityContainerCart
{
    public EntityChestCart(World world)
    {
        super(world);
    }

    public EntityChestCart(World world, double xx, double yy, double zz)
    {
        super(world, xx, yy, zz);
    }

    @Override
    public EnumCartTypes getCartType()
    {
        return EnumCartTypes.CHEST;
    }

    @Override
    public int getSizeInventory()
    {
        return 27;
    }

    @Override
    public EntityMinecart.Type getType()
    {
        return EntityMinecart.Type.CHEST;
    }

    @Override
    public IBlockState getDefaultDisplayTile()
    {
        return Blocks.CHEST.getDefaultState();
    }

    @Override
    public int getDefaultDisplayTileOffset()
    {
        return 8;
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

}
