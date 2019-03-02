package com.builtbroken.woodenrails.handler;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.item.*;

import net.minecraft.item.*;

/**
 * @author p455w0rd
 *
 */
public class ItemHandler {

	public static final ItemCartChest MINECART_CHEST = new ItemCartChest();
	public static final ItemCartEmpty MINECART = new ItemCartEmpty();
	public static final ItemCartSpawner MINECART_SPAWNER = new ItemCartSpawner();
	public static final ItemCartWorkbench MINECART_WORKBENCH = new ItemCartWorkbench();
	public static final ItemCartTNT MINECART_TNT = new ItemCartTNT();
	public static final ItemCartFurnace MINECART_FURNACE = new ItemCartFurnace();
	public static final ItemCartHopper MINECART_HOPPER = new ItemCartHopper();
	public static Item MINECART_TANK;

	private static Item[] itemArray;
	private static ItemGroup group;
	private static Item.Properties itemProps;

	public static Item[] getArray() {
		if (itemArray == null) {
			itemArray = new Item[] {
					MINECART_CHEST, MINECART, MINECART_SPAWNER, MINECART_WORKBENCH, MINECART_TNT, MINECART_FURNACE, MINECART_HOPPER
			};
		}
		return itemArray;
	}

	public static Item.Properties getItemProps() {
		if (itemProps == null) {
			itemProps = new Item.Properties().group(getItemGroup());
		}
		return itemProps;
	}

	public static ItemGroup getItemGroup() {
		if (group == null) {
			group = new ItemGroup(WoodenRails.MOD_ID) {
				@Override
				public ItemStack createIcon() {
					return new ItemStack(BlockHandler.WOODEN_RAIL);
				}
			};
		}
		return group;
	}

}
