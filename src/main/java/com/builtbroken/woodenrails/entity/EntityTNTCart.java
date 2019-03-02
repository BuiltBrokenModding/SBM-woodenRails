package com.builtbroken.woodenrails.entity;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.handler.EntityHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityTNTCart extends EntityWoodenCart {

	private int minecartTNTFuse = -1;

	public EntityTNTCart(World world) {
		super(EntityHandler.MINECART_TNT, world);
	}

	public EntityTNTCart(World world, double xx, double yy, double zz) {
		super(EntityHandler.MINECART_TNT, world, xx, yy, zz);
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.TNT;
	}

	@Override
	public EntityMinecart.Type getMinecartType() {
		return EntityMinecart.Type.TNT;
	}

	@Override
	public IBlockState getDefaultDisplayTile() {
		return Blocks.TNT.getDefaultState();
	}

	@Override
	public void tick() {
		super.tick();

		if (minecartTNTFuse > 0) {
			--minecartTNTFuse;
			world.addParticle(Particles.SMOKE, posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
		}
		else if (minecartTNTFuse == 0) {
			explodeCart(motionX * motionX + motionZ * motionZ);
		}

		if (collidedHorizontally) {
			double d0 = motionX * motionX + motionZ * motionZ;
			if (d0 >= 0.009999999776482582D) {
				explodeCart(d0);
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		Entity entity = source.getImmediateSource();
		if (entity instanceof EntityArrow) {
			EntityArrow entityarrow = (EntityArrow) entity;
			if (entityarrow.isBurning()) {
				explodeCart(entityarrow.motionX * entityarrow.motionX + entityarrow.motionY * entityarrow.motionY + entityarrow.motionZ * entityarrow.motionZ);
			}
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public void killMinecart(DamageSource source) {
		double d0 = motionX * motionX + motionZ * motionZ;
		if (!source.isFireDamage() && !source.isExplosion() && !(d0 >= 0.01F)) {
			super.killMinecart(source);
			if (!source.isExplosion() && world.getGameRules().getBoolean("doEntityDrops")) {
				this.entityDropItem(Blocks.TNT);
			}

		}
		else {
			if (minecartTNTFuse < 0) {
				ignite();
				minecartTNTFuse = rand.nextInt(20) + rand.nextInt(20);
			}

		}
	}

	protected void explodeCart(double p_94103_1_) {
		if (!world.isRemote) {
			double d1 = Math.sqrt(p_94103_1_);
			if (d1 > 5.0D) {
				d1 = 5.0D;
			}
			world.createExplosion(this, posX, posY, posZ, (float) (4.0D + rand.nextDouble() * 1.5D * d1), true);
			remove();
		}
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		if (distance >= 3.0F) {
			float f1 = distance / 10.0F;
			explodeCart(f1 * f1);
		}
		super.fall(distance, damageMultiplier);
	}

	@Override
	public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
		if (receivingPower && minecartTNTFuse < 0) {
			ignite();
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 10) {
			ignite();
		}
		else {
			super.handleStatusUpdate(id);
		}
	}

	public void ignite() {
		minecartTNTFuse = 80;
		if (!world.isRemote) {
			world.setEntityState(this, (byte) 10);
			if (!isSilent()) {
				world.playSound((EntityPlayer) null, posX, posY, posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}

	}

	@OnlyIn(Dist.CLIENT)
	public int getFuseTicks() {
		return minecartTNTFuse;
	}

	public boolean isIgnited() {
		return minecartTNTFuse > -1;
	}

	@Override
	public float getExplosionResistance(Explosion explosion, IBlockReader world, BlockPos pos, IBlockState state, IFluidState fluidState, float p_180428_6_) {
		return !isIgnited() || !state.isIn(BlockTags.RAILS) && !world.getBlockState(pos.up()).isIn(BlockTags.RAILS) ? super.getExplosionResistance(explosion, world, pos, state, fluidState, p_180428_6_) : 0.0F;
	}

	@Override
	public boolean canExplosionDestroyBlock(Explosion explosion, IBlockReader world, BlockPos pos, IBlockState state, float p_174816_5_) {
		return !isIgnited() || !state.isIn(BlockTags.RAILS) && !world.getBlockState(pos.up()).isIn(BlockTags.RAILS) ? super.canExplosionDestroyBlock(explosion, world, pos, state, p_174816_5_) : false;
	}

	@Override
	protected void readAdditional(NBTTagCompound nbt) {
		super.readAdditional(nbt);
		if (nbt.contains("TNTFuse", 99)) {
			minecartTNTFuse = nbt.getInt("TNTFuse");
		}
	}

	@Override
	protected void writeAdditional(NBTTagCompound nbt) {
		super.writeAdditional(nbt);
		nbt.putInt("TNTFuse", minecartTNTFuse);
	}

	@Override
	public EntityType<?> getType() {
		return getCartType().getEntityType();
	}
}
