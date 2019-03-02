package com.builtbroken.woodenrails.entity;

import java.util.Random;

import javax.annotation.Nullable;

import com.builtbroken.woodenrails.WoodenRails;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.common.util.ITeleporter;

/**
 * Created by Dark on 8/11/2015.
 */
public abstract class EntityContainerCart extends EntityWoodenCart implements ILockableContainer, ILootContainer {

	private NonNullList<ItemStack> minecartContainerItems = NonNullList.<ItemStack>withSize(36, ItemStack.EMPTY);
	private boolean dropContentsWhenDead = true;
	private ResourceLocation lootTable;
	private long lootTableSeed;

	public EntityContainerCart(EntityType<?> type, World world) {
		super(type, world);
	}

	public EntityContainerCart(EntityType<?> type, World world, double x, double y, double z) {
		super(type, world, x, y, z);
	}

	@Override
	public void killMinecart(DamageSource damage) {
		super.killMinecart(damage);
		if (world.getGameRules().getBoolean("doEntityDrops")) {
			InventoryHelper.dropInventoryItems(world, this, this);
		}
		/*
		for (int i = 0; i < getSizeInventory(); ++i) {
			ItemStack itemstack = getStackInSlot(i);

			if (itemstack != null) {
				float f = rand.nextFloat() * 0.8F + 0.1F;
				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;

				while (itemstack.getCount() > 0) {
					int j = rand.nextInt(21) + 10;

					if (j > itemstack.getCount()) {
						j = itemstack.getCount();
					}

					itemstack.setCount(itemstack.getCount() - j);
					EntityItem entityitem = new EntityItem(world, posX + f, posY + f1, posZ + f2, new ItemStack(itemstack.getItem(), j));
					float f3 = 0.05F;
					entityitem.motionX = (float) rand.nextGaussian() * f3;
					entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
					entityitem.motionZ = (float) rand.nextGaussian() * f3;
					world.spawnEntity(entityitem);
				}
			}
		}
		*/
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : minecartContainerItems) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		addLoot((EntityPlayer) null);
		return minecartContainerItems.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		addLoot((EntityPlayer) null);
		return ItemStackHelper.getAndSplit(minecartContainerItems, slot, amount);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		addLoot((EntityPlayer) null);
		ItemStack itemstack = minecartContainerItems.get(index);
		if (itemstack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		else {
			minecartContainerItems.set(index, ItemStack.EMPTY);
			return itemstack;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		addLoot((EntityPlayer) null);
		minecartContainerItems.set(slot, stack);
		if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}
	}

	@Override
	public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
		if (inventorySlot >= 0 && inventorySlot < getSizeInventory()) {
			setInventorySlotContents(inventorySlot, itemStackIn);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return removed ? false : player.getDistanceSq(this) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public Entity changeDimension(DimensionType dim, ITeleporter teleporter) {
		dropContentsWhenDead = false;
		return super.changeDimension(dim, teleporter);
	}

	@Override
	public void remove() {
		if (dropContentsWhenDead) {
			InventoryHelper.dropInventoryItems(world, this, this);
		}
		super.remove();
		itemHandler.invalidate();
		/*
		if (dropContentsWhenDead) {
			for (int i = 0; i < getSizeInventory(); ++i) {
				ItemStack itemstack = getStackInSlot(i);

				if (itemstack != null) {
					float f = rand.nextFloat() * 0.8F + 0.1F;
					float f1 = rand.nextFloat() * 0.8F + 0.1F;
					float f2 = rand.nextFloat() * 0.8F + 0.1F;

					while (itemstack.getCount() > 0) {
						int j = rand.nextInt(21) + 10;

						if (j > itemstack.getCount()) {
							j = itemstack.getCount();
						}

						itemstack.setCount(itemstack.getCount() - j);
						ItemStack stack = getCartType().getStack();
						stack.setCount(j);
						EntityItem entityitem = new EntityItem(world, posX + f, posY + f1, posZ + f2, stack);

						if (itemstack.hasTag()) {
							entityitem.getItem().setTag(itemstack.getTag().copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (float) rand.nextGaussian() * f3;
						entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) rand.nextGaussian() * f3;
						world.spawnEntity(entityitem);
					}
				}
			}
		}

		super.remove();
		*/
	}

	@Override
	public void setDropItemsWhenDead(boolean dropWhenDead) {
		dropContentsWhenDead = dropWhenDead;
	}

	/*
	@Override
	protected void writeAdditional(NBTTagCompound nbt) {
		super.writeAdditional(nbt);
		NBTTagList nbttaglist = new NBTTagList();
	
		for (int i = 0; i < minecartContainerItems.size(); ++i) {
			if (!minecartContainerItems.get(i).isEmpty()) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.putByte("Slot", (byte) i);
				minecartContainerItems.get(i).write(nbttagcompound1);
				nbttaglist.add(nbttagcompound1);
			}
		}
	
		nbt.put("Items", nbttaglist);
	}
	*/
	@Override
	protected void writeAdditional(NBTTagCompound compound) {
		super.writeAdditional(compound);
		if (lootTable != null) {
			compound.putString("LootTable", lootTable.toString());
			if (lootTableSeed != 0L) {
				compound.putLong("LootTableSeed", lootTableSeed);
			}
		}
		else {
			ItemStackHelper.saveAllItems(compound, minecartContainerItems);
		}
	}

	/*
	@Override
	protected void readAdditional(NBTTagCompound nbt) {
		super.readAdditional(nbt);
		NBTTagList nbttaglist = nbt.getList("Items", 10);
		minecartContainerItems.clear();
	
		for (int i = 0; i < nbttaglist.size(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompound(i);
			int j = nbttagcompound1.getByte("Slot") & 255;
	
			if (j >= 0 && j < minecartContainerItems.size()) {
				minecartContainerItems.set(j, ItemStack.read(nbttagcompound1));
			}
		}
	}
	*/
	@Override
	protected void readAdditional(NBTTagCompound compound) {
		super.readAdditional(compound);
		minecartContainerItems = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		if (compound.contains("LootTable", 8)) {
			lootTable = new ResourceLocation(compound.getString("LootTable"));
			lootTableSeed = compound.getLong("LootTableSeed");
		}
		else {
			ItemStackHelper.loadAllItems(compound, minecartContainerItems);
		}

	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (super.processInitialInteract(player, hand)) {
			return true;
		}
		if (!world.isRemote) {
			player.displayGUIChest(this);
		}
		return true;
	}

	@Override
	protected void applyDrag() {
		int i = 15 - Container.calcRedstoneFromInventory(this);
		float f = 0.98F + i * 0.001F;
		motionX *= f;
		motionY *= 0.0D;
		motionZ *= f;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public void setLockCode(LockCode code) {
	}

	@Override
	public LockCode getLockCode() {
		return LockCode.EMPTY_CODE;
	}

	@Override
	public String getGuiID() {
		return WoodenRails.MOD_ID + ":" + getCartType().name;
	}

	public void addLoot(@Nullable EntityPlayer player) {
		if (lootTable != null && world.getServer() != null) {
			LootTable loottable = world.getServer().getLootTableManager().getLootTableFromLocation(lootTable);
			lootTable = null;
			Random random;
			if (lootTableSeed == 0L) {
				random = new Random();
			}
			else {
				random = new Random(lootTableSeed);
			}

			LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer) world)).withPosition(new BlockPos(this)).withLootedEntity(this); // Forge: add looted entity to LootContext
			if (player != null) {
				lootcontext$builder.withLuck(player.getLuck()).withPlayer(player); // Forge: add player to LootContext
			}

			loottable.fillInventory(this, random, lootcontext$builder.build());
		}

	}

	@Override
	public void clear() {
		addLoot((EntityPlayer) null);
		minecartContainerItems.clear();
	}

	public void setLootTable(ResourceLocation lootTableIn, long lootTableSeedIn) {
		lootTable = lootTableIn;
		lootTableSeed = lootTableSeedIn;
	}

	@Override
	public ResourceLocation getLootTable() {
		return lootTable;
	}

	private net.minecraftforge.common.util.LazyOptional<?> itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this));

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
		if (!removed && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandler.cast();
		}
		return super.getCapability(capability, facing);
	}
}