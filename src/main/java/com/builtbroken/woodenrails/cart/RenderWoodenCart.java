package com.builtbroken.woodenrails.cart;

import java.awt.Color;

import com.builtbroken.woodenrails.WoodenRails;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;

/**
 * Created by Dark on 7/25/2015.
 */
public class RenderWoodenCart extends Render<EntityWoodenCart>
{
    private static final ResourceLocation minecartTextures = new ResourceLocation(WoodenRails.DOMAIN, "textures/entity/minecart.png");
    public static final ResourceLocation textureChest = new ResourceLocation("coloredchests", "textures/entity/chest/grey_scale.png");

    public static ModelChest modelChest = new ModelChest();

    /** instance of ModelMinecart for rendering */
    protected ModelBase modelMinecart = new ModelMinecart();

    public RenderWoodenCart()
    {
        super(Minecraft.getMinecraft().getRenderManager());
        this.shadowSize = 0.5F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWoodenCart p_110775_1_)
    {
        return minecartTextures;
    }

    @Override
    public void doRender(EntityWoodenCart entityMinecart, double xx, double yy, double zz, float p_76986_8_, float p_76986_9_)
    {
        GlStateManager.pushMatrix();
        //this.bindEntityTexture(entityMinecart);
        long i = entityMinecart.getEntityId() * 493286711L;
        i = i * i * 4392167121L + i * 98761L;
        float f2 = (((i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f3 = (((i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f4 = (((i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        GlStateManager.translate(f2, f3, f4);

        double d3 = entityMinecart.lastTickPosX + (entityMinecart.posX - entityMinecart.lastTickPosX) * p_76986_9_;
        double d4 = entityMinecart.lastTickPosY + (entityMinecart.posY - entityMinecart.lastTickPosY) * p_76986_9_;
        double d5 = entityMinecart.lastTickPosZ + (entityMinecart.posZ - entityMinecart.lastTickPosZ) * p_76986_9_;
        double d6 = 0.30000001192092896D;

        Vec3d vec3 = entityMinecart.getPos(d3, d4, d5);
        float f5 = entityMinecart.prevRotationPitch + (entityMinecart.rotationPitch - entityMinecart.prevRotationPitch) * p_76986_9_;

        if (vec3 != null)
        {
            Vec3d vec31 = entityMinecart.getPosOffset(d3, d4, d5, d6);
            Vec3d vec32 = entityMinecart.getPosOffset(d3, d4, d5, -d6);

            if (vec31 == null)
            {
                vec31 = vec3;
            }

            if (vec32 == null)
            {
                vec32 = vec3;
            }

            xx += vec3.x - d3;
            yy += (vec31.y + vec32.y) / 2.0D - d4;
            zz += vec3.z - d5;
            Vec3d vec33 = vec32.add(-vec31.x, -vec31.y, -vec31.z);

            if (vec33.length() != 0.0D)
            {
                vec33 = vec33.normalize();
                p_76986_8_ = (float) (Math.atan2(vec33.z, vec33.x) * 180.0D / Math.PI);
                f5 = (float) (Math.atan(vec33.y) * 73.0D);
            }
        }

        GlStateManager.translate((float) xx, (float) yy, (float) zz);
        GlStateManager.rotate(180.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-f5, 0.0F, 0.0F, 1.0F);
        float f7 = entityMinecart.getRollingAmplitude() - p_76986_9_;
        float f8 = entityMinecart.getDamage() - p_76986_9_;

        if (f8 < 0.0F)
        {
            f8 = 0.0F;
        }

        if (f7 > 0.0F)
        {
            GlStateManager.rotate(MathHelper.sin(f7) * f7 * f8 / 10.0F * entityMinecart.getRollingDirection(), 1.0F, 0.0F, 0.0F);
        }

        //Render block that is inside the minecart
        IBlockState state = entityMinecart.getDisplayTile();
        if (state != null && state.getBlock() != Blocks.AIR)
        {
            this.renderContainedBlock(entityMinecart, p_76986_9_, state.getBlock());
        }

        //Render Minecart
        this.bindEntityTexture(entityMinecart);
        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.modelMinecart.render(entityMinecart, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }

    protected void renderContainedBlock(EntityWoodenCart entityMinecart, float par, Block block)
    {
        IBlockState state = entityMinecart.getDisplayTile();

        if (state.getRenderType() != EnumBlockRenderType.INVISIBLE)
        {
            GlStateManager.pushMatrix();
            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(-0.5F, (entityMinecart.getDisplayTileOffset() - 8) / 16.0F, 0.5F);
            GlStateManager.pushMatrix();
            Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(state, entityMinecart.getBrightness());
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    protected void renderColoredChest(int c)
    {
        GlStateManager.translate(0.5f, 0.5f, 0.5f);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        modelChest.chestLid.rotateAngleX = 0;

        //set color
        if (c != -1)
        {
            Color color = WoodenRails.getColor(c);
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;
            GlStateManager.color(r, g, b);
        }

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureChest);
        modelChest.renderAll();
        GlStateManager.enableRescaleNormal();
    }
}
