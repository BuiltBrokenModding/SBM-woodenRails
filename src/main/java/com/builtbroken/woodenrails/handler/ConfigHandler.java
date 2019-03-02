package com.builtbroken.woodenrails.handler;

import java.nio.file.Path;

import com.builtbroken.woodenrails.WoodenRails;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;

/**
 * Created by bl4ckscor3 on 09/16/2018.
 */
//@Config(modid = WoodenRails.DOMAIN)
public class ConfigHandler {

	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	private static ForgeConfigSpec CONFIG;

	public static ForgeConfigSpec getConfig() {
		if (CONFIG == null) {
			Options.init(BUILDER);
			CONFIG = BUILDER.build();
		}
		return CONFIG;
	}

	public static void loadConfig(ForgeConfigSpec spec, Path path) {
		WoodenRails.LOGGER.debug("Loading config file {}", path);

		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

		WoodenRails.LOGGER.debug("Built TOML config for {}", path.toString());
		configData.load();
		WoodenRails.LOGGER.debug("Loaded TOML config file {}", path.toString());
		spec.setConfig(configData);
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
		WoodenRails.LOGGER.debug("Loaded {} config file {}", WoodenRails.MOD_ID, configEvent.getConfig().getFileName());

	}

	@SubscribeEvent
	public static void onFileChange(final ModConfig.ConfigReloading configEvent) {
		WoodenRails.LOGGER.fatal(net.minecraftforge.fml.Logging.CORE, "{} config just got changed on the file system!", WoodenRails.MOD_ID);
	}

	public static class Options {

		private static ForgeConfigSpec.BooleanValue enableCart;
		private static ForgeConfigSpec.BooleanValue enableRail;
		//public static boolean ENABLE_CART = true;
		//public static boolean ENABLE_RAIL = true;

		public static void init(ForgeConfigSpec.Builder s) {
			s.comment("Options");
			enableCart = s.comment("Allows disabling the wooden cart item and entity").define("enable_cart", true);
			enableRail = s.comment("Allows disabling the wooden rail item and block").define("enable_rail", true);
			//ENABLE_CART = enableCart.get();
			//ENABLE_RAIL = enableRail.get();
		}

		public static boolean isCartEnabled() {
			if (enableCart != null) {
				return enableCart.get();
			}
			return true;
		}

		public static boolean isRailEnabled() {
			if (enableRail != null) {
				return enableRail.get();
			}
			return true;
		}

	}

}
