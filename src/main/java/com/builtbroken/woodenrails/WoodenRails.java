package com.builtbroken.woodenrails;

import java.awt.Color;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.builtbroken.woodenrails.cart.EnumCartTypes;
import com.builtbroken.woodenrails.cart.ItemWoodenCart;
import com.builtbroken.woodenrails.rail.BlockWoodenRail;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by Dark on 7/25/2015.
 */
@Mod(modid = WoodenRails.DOMAIN, name = "Wooden Rails", version = "@MAJOR@.@MINOR@.@REVIS@.@BUILD@")
@Mod.EventBusSubscriber
public class WoodenRails
{
    public int ENTITY_ID_PREFIX = 56;
    public static final String DOMAIN = "woodenrails";
    public static final String PREFIX = DOMAIN + ":";

    @SidedProxy(clientSide = "com.builtbroken.woodenrails.ClientProxy", serverSide = "com.builtbroken.woodenrails.CommonProxy")
    public static CommonProxy proxy;

    public static Logger LOGGER;

    public static Item itemWoodCart;
    public static Block blockRail;

    @Mod.Instance(DOMAIN)
    public static WoodenRails INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = LogManager.getLogger(DOMAIN);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

    @SubscribeEvent
    public static void onRegisterBlock(RegistryEvent.Register<Block> event)
    {
        if(ConfigHandler.ENABLE_RAIL)
            event.getRegistry().register(blockRail = new BlockWoodenRail());
    }

    @SubscribeEvent
    public static void onRegisterItem(RegistryEvent.Register<Item> event)
    {
        if(ConfigHandler.ENABLE_CART)
            event.getRegistry().register(itemWoodCart = new ItemWoodenCart());

        if(ConfigHandler.ENABLE_RAIL)
            event.getRegistry().register(new ItemBlock(blockRail).setRegistryName(blockRail.getRegistryName()));
    }

    @SubscribeEvent
    public static void onRegisterModel(ModelRegistryEvent event)
    {
        if(ConfigHandler.ENABLE_RAIL)
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockRail), 0, new ModelResourceLocation(PREFIX + "wooden_rail", "inventory"));

        if(ConfigHandler.ENABLE_CART)
        {
            for(EnumCartTypes type : EnumCartTypes.values())
            {
                ModelLoader.setCustomModelResourceLocation(itemWoodCart, type.ordinal(), new ModelResourceLocation(PREFIX + type.name, "inventory"));
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterRecipe(RegistryEvent.Register<IRecipe> event)
    {
        //TODO add colored chests support if the mod is updated to 1.12.2
        ResourceLocation location = new ResourceLocation(DOMAIN, DOMAIN);

        if(ConfigHandler.ENABLE_CART)
        {
            event.getRegistry().register(new ShapedOreRecipe(location, new ItemStack(itemWoodCart),
                    "psp",
                    " b ",
                    "psp",
                    'b', Items.BOAT,
                    's', "stickWood",
                    'p', "plankWood").setRegistryName(new ResourceLocation(DOMAIN, "empty_cart")));
        }

        if(ConfigHandler.ENABLE_RAIL)
        {
            event.getRegistry().register(new ShapedOreRecipe(location, new ItemStack(blockRail, 16),
                    "ptp",
                    "psp",
                    "ptp",
                    'p', "plankWood",
                    't', "treeSapling",
                    's', "stickWood").setRegistryName(new ResourceLocation(DOMAIN, "wooden_rails")));
        }
    }

    @SubscribeEvent
    public static void onRegisterEntityEntry(RegistryEvent.Register<EntityEntry> event)
    {
        if(ConfigHandler.ENABLE_CART)
        {
            int nextEntityID = 0;

            for(EnumCartTypes type : EnumCartTypes.values())
            {
                event.getRegistry().register(EntityEntryBuilder.create()
                        .name(PREFIX + type.name)
                        .id(new ResourceLocation(DOMAIN, type.name), nextEntityID++)
                        .tracker(64, 1, true)
                        .entity(type.clazz).build());
            }
        }
    }

    public static Color getColor(int rgb)
    {
        return new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
    }
}
