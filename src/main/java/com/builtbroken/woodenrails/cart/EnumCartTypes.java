package com.builtbroken.woodenrails.cart;

import com.builtbroken.woodenrails.cart.types.EntityChestCart;
import com.builtbroken.woodenrails.cart.types.EntityEmptyCart;
import com.builtbroken.woodenrails.cart.types.EntityHopperCart;
import com.builtbroken.woodenrails.cart.types.EntityFurnaceCart;
import com.builtbroken.woodenrails.cart.types.EntityTNTCart;
import com.builtbroken.woodenrails.cart.types.EntityTankCart;
import com.builtbroken.woodenrails.cart.types.EntityWorkbenchCart;

/**
 * Created by Cow Pi on 8/11/2015.
 */
public enum EnumCartTypes
{
    EMPTY(EntityEmptyCart.class, "wooden_cart"),
    CHEST(EntityChestCart.class, "chest_cart"),
    WORKBENCH(EntityWorkbenchCart.class, "workbench_cart"),
    FURNACE(EntityFurnaceCart.class, "furnace_cart"),
    HOPPER(EntityHopperCart.class, "hopper_cart"),
    TNT(EntityTNTCart.class, "tnt_cart"),
    TANK(EntityTankCart.class, "tank_cart");

    public final Class<? extends EntityWoodenCart> clazz;
    public final String name;

    EnumCartTypes(Class<? extends EntityWoodenCart> clazz, String name)
    {
        this.clazz = clazz;
        this.name = name;
    }
}
