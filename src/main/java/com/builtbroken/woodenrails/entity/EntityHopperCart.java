package com.builtbroken.woodenrails.entity;

import java.util.List;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.handler.EntityHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Dark on 8/11/2015.
 */
public class EntityHopperCart extends EntityContainerCart implements IHopper {

	private boolean isBlocked = true;
	private int transferTicker = -1;
	private final BlockPos lastPosition = BlockPos.ORIGIN;

	public EntityHopperCart(World world) {
		super(EntityHandler.MINECART_HOPPER, world);
	}

	public EntityHopperCart(World world, double x, double y, double z) {
		super(EntityHandler.MINECART_HOPPER, world, x, y, z);
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.HOPPER;
	}

	@Override
	public Type getMinecartType() {
		return Type.HOPPER;
	}

	@Override
	public IBlockState getDefaultDisplayTile() {
		return Blocks.HOPPER.getDefaultState();
	}

	@Override
	public int getDefaultDisplayTileOffset() {
		return 1;
	}

	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
		boolean flag1 = !receivingPower;
		if (flag1 != getBlocked()) {
			setBlocked(flag1);
		}
	}

	public boolean getBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean doBlock) {
		isBlocked = doBlock;
	}

	@Override
	public World getEntityWorld() {
		return world;
	}

	@Override
	public double getXPos() {
		return posX;
	}

	@Override
	public double getYPos() {
		return posY + 0.5D;
	}

	@Override
	public double getZPos() {
		return posZ;
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote && isAlive() && getBlocked()) {
			BlockPos blockpos = new BlockPos(this);
			if (blockpos.equals(lastPosition)) {
				--transferTicker;
			}
			else {
				setTransferTicker(0);
			}

			if (!canTransfer()) {
				setTransferTicker(0);

				if (captureDroppedItems()) {
					setTransferTicker(4);
					markDirty();
				}
			}
		}
	}

	public boolean captureDroppedItems() {
		if (TileEntityHopper.pullItems(this)) {
			return true;
		}
		else {
			List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, getBoundingBox().grow(0.25D, 0.0D, 0.25D), EntitySelectors.IS_ALIVE);
			if (!list.isEmpty()) {
				TileEntityHopper.captureItem(this, list.get(0));
			}

			return false;
		}
	}

	@Override
	protected void writeAdditional(NBTTagCompound nbt) {
		super.writeAdditional(nbt);
		nbt.putInt("TransferCooldown", transferTicker);
		nbt.putBoolean("Enabled", isBlocked);
	}

	@Override
	protected void readAdditional(NBTTagCompound nbt) {
		super.readAdditional(nbt);
		transferTicker = nbt.getInt("TransferCooldown");
		isBlocked = nbt.contains("Enabled") ? nbt.getBoolean("Enabled") : true;
	}

	public void setTransferTicker(int time) {
		transferTicker = time;
	}

	public boolean canTransfer() {
		return transferTicker > 0;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return getEntityWorld();
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < getSizeInventory(); i++) {
			if (!getStackInSlot(i).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void killMinecart(DamageSource source) {
		super.killMinecart(source);
		if (world.getGameRules().getBoolean("doEntityDrops")) {
			this.entityDropItem(Blocks.HOPPER);
		}

	}

	@Override
	public String getGuiID() {
		return "minecraft:hopper";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {
		return new ContainerHopper(playerInventory, this, player);
	}

	@Override
	public EntityType<?> getType() {
		return getCartType().getEntityType();
	}
}
