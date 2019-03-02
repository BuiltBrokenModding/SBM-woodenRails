package com.builtbroken.woodenrails.cart.recipe;

import java.util.function.BooleanSupplier;

import com.builtbroken.woodenrails.handler.ConfigHandler.Options;
import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.IConditionSerializer;

/**
 * @author p455w0rd
 *
 */
public class EnableRailConditionFactory implements IConditionSerializer {

	@Override
	public BooleanSupplier parse(JsonObject json) {
		return () -> Options.isRailEnabled();
	}

}
