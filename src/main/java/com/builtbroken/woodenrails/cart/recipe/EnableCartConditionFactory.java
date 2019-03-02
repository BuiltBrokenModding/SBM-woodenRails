package com.builtbroken.woodenrails.cart.recipe;

import java.util.function.BooleanSupplier;

import com.builtbroken.woodenrails.handler.ConfigHandler.Options;
import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.IConditionSerializer;

/**
 * Created by bl4ckscor3 on 09/18/2018.
 */
public class EnableCartConditionFactory implements IConditionSerializer {

	@Override
	public BooleanSupplier parse(JsonObject json) {
		return () -> Options.isCartEnabled();
	}

}