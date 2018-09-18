package com.builtbroken.woodenrails.cart.recipe;

import java.util.function.BooleanSupplier;

import com.builtbroken.woodenrails.ConfigHandler;
import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

/**
 * Created by bl4ckscor3 on 09/18/2018.
 */
public class EnableCartConditionFactory implements IConditionFactory
{
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json)
    {
        return () -> ConfigHandler.ENABLE_CART;
    }
}
