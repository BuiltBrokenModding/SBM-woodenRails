package com.builtbroken.woodenrails.client.render;

import java.awt.Color;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.entity.EntitySpawnerCart;
import com.builtbroken.woodenrails.entity.EntityWoodenCart;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Created by Dark on 7/25/2015.
 */
public class RenderWoodenCart<T extends EntityWoodenCart> extends Render<T> {

	private static final ResourceLocation minecartTextures = new ResourceLocation(WoodenRails.MOD_ID, "textures/entity/minecart.png");
	public static final ResourceLocation textureChest = new ResourceLocation("coloredchests", "textures/entity/chest/grey_scale.png");

	public static ModelChest modelChest = new ModelChest();

	/** instance of ModelMinecart for rendering */
	protected ModelBase modelMinecart = new ModelMinecart();

	public RenderWoodenCart(RenderManager manager) {
		super(manager);
		shadowSize = 0.5F;
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return minecartTextures;
	}

	@Override
	public void doRender(T entityMinecart, double xx, double yy, double zz, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		//this.bindEntityTexture(entityMinecart);
		long i = entityMinecart.getEntityId() * 493286711L;
		i = i * i * 4392167121L + i * 98761L;
		float f2 = (((i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f3 = (((i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		float f4 = (((i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
		GlStateManager.translatef(f2, f3, f4);

		double d3 = entityMinecart.lastTickPosX + (entityMinecart.posX - entityMinecart.lastTickPosX) * partialTicks;
		double d4 = entityMinecart.lastTickPosY + (entityMinecart.posY - entityMinecart.lastTickPosY) * partialTicks;
		double d5 = entityMinecart.lastTickPosZ + (entityMinecart.posZ - entityMinecart.lastTickPosZ) * partialTicks;
		double d6 = 0.30000001192092896D;

		Vec3d vec3 = entityMinecart.getPos(d3, d4, d5);
		float f5 = entityMinecart.prevRotationPitch + (entityMinecart.rotationPitch - entityMinecart.prevRotationPitch) * partialTicks;

		if (vec3 != null) {
			Vec3d vec31 = entityMinecart.getPosOffset(d3, d4, d5, d6);
			Vec3d vec32 = entityMinecart.getPosOffset(d3, d4, d5, -d6);

			if (vec31 == null) {
				vec31 = vec3;
			}

			if (vec32 == null) {
				vec32 = vec3;
			}

			xx += vec3.x - d3;
			yy += (vec31.y + vec32.y) / 2.0D - d4;
			zz += vec3.z - d5;
			Vec3d vec33 = vec32.add(-vec31.x, -vec31.y, -vec31.z);

			if (vec33.length() != 0.0D) {
				vec33 = vec33.normalize();
				entityYaw = (float) (Math.atan2(vec33.z, vec33.x) * 180.0D / Math.PI);
				f5 = (float) (Math.atan(vec33.y) * 73.0D);
			}
		}

		GlStateManager.translatef((float) xx, (float) yy + 0.3765F, (float) zz); //translating by 0.3765 up makes the cart render at the same height as the vanilla ones. value by trial and error
		GlStateManager.rotatef(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(-f5, 0.0F, 0.0F, 1.0F);
		float f7 = entityMinecart.getRollingAmplitude() - partialTicks;
		float f8 = entityMinecart.getDamage() - partialTicks;

		if (f8 < 0.0F) {
			f8 = 0.0F;
		}

		if (f7 > 0.0F) {
			GlStateManager.rotatef(MathHelper.sin(f7) * f7 * f8 / 10.0F * entityMinecart.getRollingDirection(), 1.0F, 0.0F, 0.0F);
		}

		//Render block that is inside the minecart
		IBlockState state = entityMinecart.getDisplayTile();
		if (state != null && state.getBlock() != Blocks.AIR) {
			if (entityMinecart instanceof EntitySpawnerCart && state.getBlock() == Blocks.SPAWNER) {
				renderMob(((EntitySpawnerCart) entityMinecart).getSpawnerLogic(), xx, yy, zz, partialTicks);
			}
			renderContainedBlock(entityMinecart);

		}

		//Render Minecart
		bindEntityTexture(entityMinecart);
		GlStateManager.color4f(1f, 1f, 1f, 1f);
		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		modelMinecart.render(entityMinecart, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
	}

	public static void renderMob(MobSpawnerBaseLogic mobSpawnerLogic, double posX, double posY, double posZ, float partialTicks) {
		GlStateManager.pushMatrix();
		Entity entity = mobSpawnerLogic.getCachedEntity();
		if (entity != null) {
			float f = 0.53125F;
			float f1 = Math.max(entity.width, entity.height);
			if (f1 > 1.0D) {
				f /= f1;
			}
			//GlStateManager.color4f(1f, 1f, 1f, 1f);
			//GlStateManager.enableLighting();
			//RenderHelper.disableStandardItemLighting();
			GlStateManager.translatef(0.0F, 0.4F, 0.0F);
			GlStateManager.rotatef((float) (mobSpawnerLogic.getPrevMobRotation() + (mobSpawnerLogic.getMobRotation() - mobSpawnerLogic.getPrevMobRotation()) * partialTicks) * 10.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.translatef(0.0F, -0.2F, 0.0F);
			GlStateManager.rotatef(-30.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.scalef(f, f, f);
			entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
			Minecraft.getInstance().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
			//RenderHelper.enableStandardItemLighting();
		}
		GlStateManager.popMatrix();
	}

	protected void renderContainedBlock(T entityMinecart) {
		IBlockState state = entityMinecart.getDisplayTile();

		if (state.getRenderType() != EnumBlockRenderType.INVISIBLE) {
			GlStateManager.pushMatrix();
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GlStateManager.scalef(0.75F, 0.75F, 0.75F);
			GlStateManager.translatef(-0.5F, (entityMinecart.getDisplayTileOffset() - 8) / 16.0F, 0.5F);
			GlStateManager.pushMatrix();
			Minecraft.getInstance().getBlockRendererDispatcher().renderBlockBrightness(state, entityMinecart.getBrightness());
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	protected void renderColoredChest(int c) {
		GlStateManager.translatef(0.5f, 0.5f, 0.5f);
		GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.scalef(1.0F, -1.0F, -1.0F);
		modelChest.getLid().rotateAngleX = 0;

		//set color
		if (c != -1) {
			Color color = getColor(c);
			float r = color.getRed() / 255f;
			float g = color.getGreen() / 255f;
			float b = color.getBlue() / 255f;
			GlStateManager.color3f(r, g, b);
		}

		renderManager.textureManager.bindTexture(textureChest);
		modelChest.renderAll();
		GlStateManager.enableRescaleNormal();
	}

	public static Color getColor(int rgb) {
		return new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
	}

}
