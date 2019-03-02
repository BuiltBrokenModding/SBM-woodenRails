package com.builtbroken.woodenrails.handler;

import static com.builtbroken.woodenrails.WoodenRails.MOD_ID;

import com.builtbroken.woodenrails.entity.*;

import net.minecraft.entity.EntityType;

/**
 * @author p455w0rd
 *
 */
public class EntityHandler {

	public static final EntityType<EntityChestCart> MINECART_CHEST = EntityType.register(MOD_ID + ":minecart_chest", EntityType.Builder.create(EntityChestCart.class, EntityChestCart::new).tracker(128, 1, true));
	public static final EntityType<EntityEmptyCart> MINECART_EMPTY = EntityType.register(MOD_ID + ":minecart_empty", EntityType.Builder.create(EntityEmptyCart.class, EntityEmptyCart::new).tracker(128, 1, true));
	public static final EntityType<EntityFurnaceCart> MINECART_FURNACE = EntityType.register(MOD_ID + ":minecart_furnace", EntityType.Builder.create(EntityFurnaceCart.class, EntityFurnaceCart::new).tracker(128, 1, true));
	public static final EntityType<EntityHopperCart> MINECART_HOPPER = EntityType.register(MOD_ID + ":minecart_hopper", EntityType.Builder.create(EntityHopperCart.class, EntityHopperCart::new).tracker(128, 1, true));
	public static final EntityType<EntityTankCart> MINECART_TANK = EntityType.register(MOD_ID + ":minecart_tank", EntityType.Builder.create(EntityTankCart.class, EntityTankCart::new).tracker(128, 1, true));
	public static final EntityType<EntityTNTCart> MINECART_TNT = EntityType.register(MOD_ID + ":minecart_tnt", EntityType.Builder.create(EntityTNTCart.class, EntityTNTCart::new).tracker(128, 1, true));
	public static final EntityType<EntityWorkbenchCart> MINECART_WORKBENCH = EntityType.register(MOD_ID + ":minecart_workbench", EntityType.Builder.create(EntityWorkbenchCart.class, EntityWorkbenchCart::new).tracker(128, 1, true));
	public static final EntityType<EntitySpawnerCart> MINECART_SPAWNER = EntityType.register(MOD_ID + ":minecart_spawner", EntityType.Builder.create(EntitySpawnerCart.class, EntitySpawnerCart::new).tracker(128, 1, true));
	private static EntityType<?>[] cartArray;

	public static EntityType<?>[] getArray() {
		if (cartArray == null) {
			cartArray = new EntityType<?>[] {
					MINECART_CHEST, MINECART_EMPTY, MINECART_FURNACE, MINECART_HOPPER, MINECART_SPAWNER, MINECART_TANK, MINECART_TNT, MINECART_WORKBENCH
			};
		}
		return cartArray;
	}

}
