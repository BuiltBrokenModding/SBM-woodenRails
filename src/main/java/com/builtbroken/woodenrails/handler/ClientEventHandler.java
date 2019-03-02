package com.builtbroken.woodenrails.handler;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.client.render.RenderWoodenCart;
import com.builtbroken.woodenrails.entity.*;
import com.builtbroken.woodenrails.handler.ConfigHandler.Options;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * @author p455w0rd
 *
 */
@EventBusSubscriber(modid = WoodenRails.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {
		if (Options.isCartEnabled()) {
			RenderingRegistry.registerEntityRenderingHandler(EntityEmptyCart.class, manager -> new RenderWoodenCart<EntityEmptyCart>(manager));
			RenderingRegistry.registerEntityRenderingHandler(EntityChestCart.class, manager -> new RenderWoodenCart<EntityChestCart>(manager));
			RenderingRegistry.registerEntityRenderingHandler(EntityWorkbenchCart.class, manager -> new RenderWoodenCart<EntityWorkbenchCart>(manager));
			RenderingRegistry.registerEntityRenderingHandler(EntityTNTCart.class, manager -> new RenderWoodenCart<EntityTNTCart>(manager));
			RenderingRegistry.registerEntityRenderingHandler(EntitySpawnerCart.class, manager -> new RenderWoodenCart<EntitySpawnerCart>(manager));
			RenderingRegistry.registerEntityRenderingHandler(EntityHopperCart.class, manager -> new RenderWoodenCart<EntityHopperCart>(manager));
			RenderingRegistry.registerEntityRenderingHandler(EntityFurnaceCart.class, manager -> new RenderWoodenCart<EntityFurnaceCart>(manager));
		}
	}

}
