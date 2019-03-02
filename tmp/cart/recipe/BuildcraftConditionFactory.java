package com.builtbroken.woodenrails.cart.recipe;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;

/**
 * Created by bl4ckscor3 on 09/18/2018.
 */
public class BuildcraftConditionFactory implements IConditionFactory
{
    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json)
    {
        return () -> Loader.isModLoaded("buildcraft");
    }
}
