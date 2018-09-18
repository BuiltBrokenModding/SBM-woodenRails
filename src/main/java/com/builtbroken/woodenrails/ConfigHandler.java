package com.builtbroken.woodenrails;

import net.minecraftforge.common.config.Config;

/**
 * Created by bl4ckscor3 on 09/16/2018.
 */
@Config(modid = WoodenRails.DOMAIN)
public class ConfigHandler
{
    @Config.Name("enable_cart")
    @Config.Comment("Allows disabling the wooden cart item and entity")
    @Config.RequiresMcRestart
    public static boolean ENABLE_CART = true;

    @Config.Name("enable_rail")
    @Config.Comment("Allows disabling the wooden rail item and block")
    @Config.RequiresMcRestart
    public static boolean ENABLE_RAIL = true;
}
