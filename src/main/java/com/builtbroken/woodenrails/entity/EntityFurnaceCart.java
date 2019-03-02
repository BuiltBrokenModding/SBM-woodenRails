package com.builtbroken.woodenrails.entity;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.handler.EntityHandler;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityFurnaceCart extends EntityWoodenCart {

	private static final Ingredient ALLOWED_FUELS = Ingredient.fromItems(Items.COAL, Items.CHARCOAL);
	private int fuel;
	public double pushX;
	public double pushZ;
	private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(EntityFurnaceCart.class, DataSerializers.BOOLEAN);

	public EntityFurnaceCart(World world) {
		super(EntityHandler.MINECART_FURNACE, world);
	}

	public EntityFurnaceCart(World world, double xx, double yy, double zz) {
		super(EntityHandler.MINECART_FURNACE, world, xx, yy, zz);
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.FURNACE;
	}

	@Override
	public EntityType<?> getType() {
		return getCartType().getEntityType();
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(POWERED, false);
	}

	@Override
	public void tick() {
		super.tick();
		if (fuel > 0) {
			--fuel;
		}
		if (fuel <= 0) {
			pushX = pushZ = 0.0D;
		}
		setMinecartPowered(fuel > 0);
		if (isMinecartPowered() && rand.nextInt(4) == 0) {
			world.addParticle(Particles.LARGE_SMOKE, posX, posY + 0.8D, posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void killMinecart(DamageSource source) {
		super.killMinecart(source);
		if (!source.isExplosion() && world.getGameRules().getBoolean("doEntityDrops")) {
			this.entityDropItem(new ItemStack(Blocks.FURNACE, 1), 0.0F);
		}
	}

	@Override
	protected void moveAlongTrack(BlockPos pos, IBlockState state) {
		super.moveAlongTrack(pos, state);
		double d2 = pushX * pushX + pushZ * pushZ;

		if (d2 > 1.0E-4D && motionX * motionX + motionZ * motionZ > 0.001D) {
			d2 = MathHelper.sqrt(d2);
			pushX /= d2;
			pushZ /= d2;

			if (pushX * motionX + pushZ * motionZ < 0.0D) {
				pushX = 0.0D;
				pushZ = 0.0D;
			}
			else {
				double d1 = d2 / getMaximumSpeed();
				pushX *= d1;
				pushZ *= d1;
			}
		}
	}

	@Override
	protected void applyDrag() {
		double d0 = pushX * pushX + pushZ * pushZ;

		if (d0 > 1.0E-4D) {
			d0 = MathHelper.sqrt(d0);
			pushX /= d0;
			pushZ /= d0;
			double d1 = 0.05D;
			motionX *= 0.800000011920929D;
			motionY *= 0.0D;
			motionZ *= 0.800000011920929D;
			motionX += pushX * d1;
			motionZ += pushZ * d1;
		}
		else {
			motionX *= 0.9800000190734863D;
			motionY *= 0.0D;
			motionZ *= 0.9800000190734863D;
		}

		super.applyDrag();
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		//if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, player, hand))) return true;
		if (super.processInitialInteract(player, hand)) {
			return true;
		}
		ItemStack itemstack = player.getHeldItem(hand);
		if (ALLOWED_FUELS.test(itemstack) && fuel + 3600 <= 32000) {
			if (!player.abilities.isCreativeMode) {
				itemstack.shrink(1);
			}

			fuel += 3600;
		}

		pushX = posX - player.posX;
		pushZ = posZ - player.posZ;
		return true;
	}

	@Override
	protected void writeAdditional(NBTTagCompound nbt) {
		super.writeAdditional(nbt);
		nbt.putDouble("PushX", pushX);
		nbt.putDouble("PushZ", pushZ);
		nbt.putShort("Fuel", (short) fuel);
	}

	@Override
	protected void readAdditional(NBTTagCompound nbt) {
		super.readAdditional(nbt);
		pushX = nbt.getDouble("PushX");
		pushZ = nbt.getDouble("PushZ");
		fuel = nbt.getShort("Fuel");
	}

	protected boolean isMinecartPowered() {
		return dataManager.get(POWERED);
	}

	protected void setMinecartPowered(boolean isPowered) {
		dataManager.set(POWERED, isPowered);
	}

	@Override
	public IBlockState getDisplayTile() {
		return Blocks.FURNACE.getDefaultState().with(BlockFurnace.FACING, EnumFacing.NORTH).with(BlockFurnace.LIT, Boolean.valueOf(isMinecartPowered()));
	}

	@Override
	public Type getMinecartType() {
		return Type.FURNACE;
	}

}
