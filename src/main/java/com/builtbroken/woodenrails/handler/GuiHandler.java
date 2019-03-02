package com.builtbroken.woodenrails.handler;

import javax.annotation.Nullable;

import com.builtbroken.woodenrails.client.gui.GuiWorkbench;
import com.builtbroken.woodenrails.entity.EntityWoodenCart;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.FMLPlayMessages;

/**
 * @author p455w0rd
 *
 */
public class GuiHandler {

	@Nullable
	public static GuiScreen getGui(FMLPlayMessages.OpenContainer c) {
		PacketBuffer buf = c.getAdditionalData();
		Entity entity = Minecraft.getInstance().world.getEntityByID(buf.readInt());
		if (entity != null && entity instanceof EntityWoodenCart) {
			switch (((EntityWoodenCart) entity).getCartType()) {
			case WORKBENCH:
				return new GuiWorkbench(Minecraft.getInstance().player.inventory, Minecraft.getInstance().world, entity.getPosition());
			default:
				break;

			}
		}
		return null;
	}

}
