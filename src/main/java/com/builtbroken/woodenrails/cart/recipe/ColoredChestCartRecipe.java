package com.builtbroken.woodenrails.cart.recipe;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.handler.ItemHandler;
import com.builtbroken.woodenrails.handler.RecipeHandler;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Dark on 8/11/2015.
 */
public class ColoredChestCartRecipe extends IRecipeHidden {

	private final NonNullList<Ingredient> ingredients = NonNullList.withSize(2, Ingredient.EMPTY);
	Block coloredChest;

	public ColoredChestCartRecipe(ResourceLocation r) {
		super(new ResourceLocation(WoodenRails.MOD_ID, "colored_chests"));
		coloredChest = Blocks.CHEST;//TODO coloredChest;
		ingredients.set(0, Ingredient.fromStacks(new ItemStack(coloredChest)));
		ingredients.set(1, Ingredient.fromStacks(new ItemStack(ItemHandler.MINECART)));
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		ItemStack result = getRecipeOutput().copy();
		if (!result.isEmpty()) {
			if (!result.hasTag()) {
				result.setTag(new NBTTagCompound());
			}
			for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
				ItemStack stack = inv.getStackInSlot(slot);
				if (!stack.isEmpty() && stack.getItem() instanceof ItemBlock && Block.getBlockFromItem(stack.getItem()) == coloredChest) {
					if (stack.hasTag() && stack.getTag().contains("rgb")) {
						result.getTag().putInt("rgb", stack.getTag().getInt("rgb"));
						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return false;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= 1 && height >= 2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return RecipeHandler.COLORED_CHESTS_SERIALIZER;
	}
}
