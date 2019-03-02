package com.builtbroken.woodenrails;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.builtbroken.woodenrails.cart.recipe.EnableCartConditionFactory;
import com.builtbroken.woodenrails.entity.*;
import com.builtbroken.woodenrails.handler.*;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

/**
 * Created by Dark on 7/25/2015.
 */
@Mod(WoodenRails.MOD_ID)
public class WoodenRails {

	public static final String MOD_ID = "woodenrails";
	public static final String PREFIX = MOD_ID + ":";
	private static final String CONFIG_FILE = MOD_ID + ".toml";

	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public WoodenRails() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.getConfig(), CONFIG_FILE);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::getGui);
		ConfigHandler.loadConfig(ConfigHandler.getConfig(), FMLPaths.CONFIGDIR.get().resolve(CONFIG_FILE));
		CraftingHelper.register(new ResourceLocation(MOD_ID, "enable_cart"), new EnableCartConditionFactory());
	}

	/**
	 * Created by Cow Pi on 8/11/2015.
	 * Modified by TheRealp455w0rd on 3/1/2019
	 */
	public static enum EnumCartTypes {

			EMPTY(EntityEmptyCart.class, "wooden_cart", ItemHandler.MINECART, EntityHandler.MINECART_EMPTY),
			CHEST(EntityChestCart.class, "chest_cart", ItemHandler.MINECART_CHEST, EntityHandler.MINECART_CHEST),
			WORKBENCH(EntityWorkbenchCart.class, "workbench_cart", ItemHandler.MINECART_WORKBENCH, EntityHandler.MINECART_WORKBENCH),
			FURNACE(EntityFurnaceCart.class, "furnace_cart", ItemHandler.MINECART_FURNACE, EntityHandler.MINECART_FURNACE),
			HOPPER(EntityHopperCart.class, "hopper_cart", ItemHandler.MINECART_HOPPER, EntityHandler.MINECART_HOPPER),
			TNT(EntityTNTCart.class, "tnt_cart", ItemHandler.MINECART_TNT, EntityHandler.MINECART_TNT),
			TANK(EntityTankCart.class, "tank_cart", ItemHandler.MINECART_TANK, EntityHandler.MINECART_TANK),
			SPAWNER(EntitySpawnerCart.class, "spawner_cart", ItemHandler.MINECART_SPAWNER, EntityHandler.MINECART_SPAWNER);

		public final Class<? extends EntityWoodenCart> clazz;
		public final String name;
		private final ItemStack stack;
		private final EntityType<?> entity;

		EnumCartTypes(Class<? extends EntityWoodenCart> clazz, String name, Item item, EntityType<?> entity) {
			this.clazz = clazz;
			this.name = name;
			stack = new ItemStack(item);
			this.entity = entity;
		}

		public ItemStack getStack() {
			return stack;
		}

		public EntityType<?> getEntityType() {
			return entity;
		}

		public EntityWoodenCart newEntity(World world) {
			return (EntityWoodenCart) EntityType.create(world, getEntityType().getRegistryName());
		}
	}
}
