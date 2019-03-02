package com.builtbroken.woodenrails.handler;

import static com.builtbroken.woodenrails.WoodenRails.MOD_ID;

import com.builtbroken.woodenrails.handler.ConfigHandler.Options;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * @author p455w0rd
 *
 */
@EventBusSubscriber(modid = MOD_ID, bus = Bus.MOD)
public class CommonEventHandler {

	@SubscribeEvent
	public static void onRegisterEntity(final RegistryEvent.Register<EntityType<?>> event) {
		if (Options.isCartEnabled()) {
			event.getRegistry().registerAll(EntityHandler.getArray());
		}
	}

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		if (Options.isCartEnabled()) {
			event.getRegistry().registerAll(ItemHandler.getArray());
		}
		if (Options.isRailEnabled()) {
			event.getRegistry().register(new ItemBlock(BlockHandler.WOODEN_RAIL, ItemHandler.getItemProps()).setRegistryName(BlockHandler.WOODEN_RAIL.getRegistryName()));
		}
	}

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
		if (Options.isRailEnabled()) {
			event.getRegistry().register(BlockHandler.WOODEN_RAIL);
		}
	}

}
