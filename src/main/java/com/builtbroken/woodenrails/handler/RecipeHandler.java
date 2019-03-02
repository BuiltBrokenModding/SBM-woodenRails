package com.builtbroken.woodenrails.handler;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.cart.recipe.ColoredChestCartRecipe;

import net.minecraft.item.crafting.RecipeSerializers;

/**
 * @author p455w0rd
 *
 */
public class RecipeHandler {

	public static final RecipeSerializers.SimpleSerializer<ColoredChestCartRecipe> COLORED_CHESTS_SERIALIZER = RecipeSerializers.register(new RecipeSerializers.SimpleSerializer<ColoredChestCartRecipe>(WoodenRails.MOD_ID + ":colored_chests", ColoredChestCartRecipe::new));

}
