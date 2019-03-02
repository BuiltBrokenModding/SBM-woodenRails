package com.builtbroken.woodenrails.cart.recipe;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.cart.EnumCartTypes;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by Dark on 8/11/2015.
 */
public class ColoredChestCartRecipe extends ShapedOreRecipe
{
    Block coloredChest;

    public ColoredChestCartRecipe(Block coloredChest)
    {
        super(new ResourceLocation(WoodenRails.DOMAIN, WoodenRails.DOMAIN), new ItemStack(WoodenRails.itemWoodCart, 1, EnumCartTypes.CHEST.ordinal()), "c", "r", 'c', coloredChest, 'r', new ItemStack(WoodenRails.itemWoodCart));
        this.coloredChest = coloredChest;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack result = super.getCraftingResult(inv);
        if (!result.isEmpty())
        {
            if (!result.hasTagCompound())
                result.setTagCompound(new NBTTagCompound());
            for (int slot = 0; slot < inv.getSizeInventory(); slot++)
            {
                ItemStack stack = inv.getStackInSlot(slot);
                if (!stack.isEmpty() && stack.getItem() instanceof ItemBlock && Block.getBlockFromItem(stack.getItem()) == coloredChest)
                {
                    if (stack.hasTagCompound() && stack.getTagCompound().hasKey("rgb"))
                    {
                        result.getTagCompound().setInteger("rgb", stack.getTagCompound().getInteger("rgb"));
                        break;
                    }
                }
            }
        }
        return result;
    }
}
