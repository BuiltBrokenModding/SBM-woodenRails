package com.builtbroken.woodenrails.entity;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.handler.EntityHandler;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.*;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.*;

/**
 * Cart that contains  a fluid tank
 * Created by Dark on 8/12/2015.
 */
public class EntityTankCart extends EntityWoodenCart implements IFluidHandler {
	protected FluidTank internal_tank;
	private static final DataParameter<String> FLUID_ID = EntityDataManager.<String>createKey(EntityWoodenCart.class, DataSerializers.STRING);
	private static final DataParameter<Integer> FLUID_AMOUNT = EntityDataManager.<Integer>createKey(EntityWoodenCart.class, DataSerializers.VARINT);
	private static final DataParameter<Byte> TANK_TYPE = EntityDataManager.<Byte>createKey(EntityWoodenCart.class, DataSerializers.BYTE);

	public EntityTankCart(World world) {
		super(EntityHandler.MINECART_TANK, world);
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(FLUID_ID, "");
		dataManager.register(FLUID_AMOUNT, new Integer(0));
		dataManager.register(TANK_TYPE, Byte.valueOf((byte) 0));
	}

	protected void setTankType(TankCartType type) {
		if (!world.isRemote) {
			dataManager.set(TANK_TYPE, Byte.valueOf((byte) type.ordinal()));
		}
	}

	protected TankCartType getTankType() {
		byte b = dataManager.get(TANK_TYPE);
		if (b >= 0 && b < TankCartType.values().length) {
			return TankCartType.values()[b];
		}
		return TankCartType.BUILDCRAFT;
	}

	protected void setFluidID(Fluid fluid) {
		if (!world.isRemote) {
			dataManager.set(FLUID_ID, fluid.getName()); //according to the javadoc, a fluid name is unique
		}
	}

	protected String getFluidID() {
		return dataManager.get(FLUID_ID);
	}

	protected void setFluidAmount(int a) {
		if (!world.isRemote) {
			dataManager.set(FLUID_AMOUNT, Integer.valueOf(a));
		}
	}

	protected int getFluidAmount() {
		return dataManager.get(FLUID_AMOUNT);
	}

	@Override
	protected void readAdditional(NBTTagCompound nbt) {
		super.readAdditional(nbt);
		if (nbt.contains("tank")) {
			getTank().readFromNBT(nbt.getCompound("tank"));
		}
	}

	@Override
	protected void writeAdditional(NBTTagCompound nbt) {
		super.writeAdditional(nbt);
		if (getTank() != null && getTank().getFluid() != null) {
			nbt.put("tank", getTank().writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.TANK;
	}

	@Override
	public EntityMinecart.Type getMinecartType() {
		return Type.COMMAND_BLOCK;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return getTank().fill(resource, doFill);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource != null && (getTank().getFluid() == null || resource.getFluid() == getTank().getFluid().getFluid())) {
			return getTank().drain(resource.amount, doDrain);
		}
		return null;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		if (getTank() == null) {
			return new FluidTankPropertiesWrapper[0];
		}
		return getTank().getTankProperties();
	}

	public FluidTank getTank() {
		if (internal_tank == null) {
			//Custom internal class to auto update the datawatchers used by the renderers
			internal_tank = new FluidTank(getTankType().buckets * Fluid.BUCKET_VOLUME) {
				@Override
				public int fill(FluidStack resource, boolean doFill) {
					Fluid fluid = getFluid() != null ? getFluid().getFluid() : null;
					if (resource != null && (fluid == null || resource.getFluid() == fluid)) {
						int fill = super.fill(resource, doFill);
						if (fluid != getFluid().getFluid()) {
							EntityTankCart.this.setFluidID(getFluid().getFluid());
						}
						if (doFill && fill > 0) {
							EntityTankCart.this.setFluidAmount(getFluidAmount());
						}
						return fill;
					}
					return 0;
				}

				@Override
				public FluidStack drain(int maxDrain, boolean doDrain) {
					if (maxDrain > 0 && doDrain) {
						Fluid fluid = getFluid() != null ? getFluid().getFluid() : null;
						FluidStack drain = super.drain(maxDrain, doDrain);
						if (drain != null) {
							//Should never happen but just in case a freak ASM :P
							if (fluid != getFluid().getFluid()) {
								EntityTankCart.this.setFluidID(getFluid().getFluid());
							}
							if (drain.amount > 0) {
								EntityTankCart.this.setFluidAmount(getFluidAmount());
							}
						}
						return drain;
					}
					return super.drain(maxDrain, doDrain);
				}
			};
		}
		return internal_tank;
	}

	public enum TankCartType {
			BUILDCRAFT(16),
			OPENBLOCKS(16);
		//TODO add more tanks types for the lolz

		public final int buckets;

		TankCartType(int buckets) {
			this.buckets = buckets;
		}
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}
}
