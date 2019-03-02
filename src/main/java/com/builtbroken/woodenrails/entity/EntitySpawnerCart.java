package com.builtbroken.woodenrails.entity;

import javax.annotation.Nullable;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;
import com.builtbroken.woodenrails.handler.EntityHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author p455w0rd
 *
 */
public class EntitySpawnerCart extends EntityWoodenCart {

	private final MobSpawnerBaseLogic mobSpawnerLogic = new MobSpawnerBaseLogic() {

		@Override
		public void broadcastEvent(int id) {
			EntitySpawnerCart.this.world.setEntityState(EntitySpawnerCart.this, (byte) id);
		}

		@Override
		public World getWorld() {
			return EntitySpawnerCart.this.world;
		}

		@Override
		public BlockPos getSpawnerPosition() {
			return new BlockPos(EntitySpawnerCart.this);
		}

		@Override
		@Nullable
		public Entity getSpawnerEntity() {
			return EntitySpawnerCart.this;
		}
	};

	public EntitySpawnerCart(World world) {
		super(EntityHandler.MINECART_SPAWNER, world);
	}

	public EntitySpawnerCart(World world, double x, double y, double z) {
		super(EntityHandler.MINECART_SPAWNER, world, x, y, z);
	}

	public MobSpawnerBaseLogic getSpawnerLogic() {
		return mobSpawnerLogic;
	}

	@Override
	public EntityMinecart.Type getMinecartType() {
		return EntityMinecart.Type.SPAWNER;
	}

	@Override
	public IBlockState getDefaultDisplayTile() {
		return Blocks.SPAWNER.getDefaultState();
	}

	@Override
	public EntityType<?> getType() {
		return getCartType().getEntityType();
	}

	@Override
	protected void readAdditional(NBTTagCompound compound) {
		super.readAdditional(compound);
		mobSpawnerLogic.read(compound);
	}

	@Override
	protected void writeAdditional(NBTTagCompound compound) {
		super.writeAdditional(compound);
		mobSpawnerLogic.write(compound);
	}

	/**
	 * Handler for {@link World#setEntityState}
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleStatusUpdate(byte id) {
		mobSpawnerLogic.setDelayToMin(id);
	}

	@Override
	public void tick() {
		super.tick();
		mobSpawnerLogic.tick();
	}

	@Override
	public boolean ignoreItemEntityData() {
		return true;
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.SPAWNER;
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() instanceof ItemSpawnEgg) {
				EntityType<?> entity = ((ItemSpawnEgg) stack.getItem()).getType(stack.getTag());
				if (entity != null) {
					mobSpawnerLogic.setEntityType(entity);
					mobSpawnerLogic.getCachedEntity();
					NBTTagCompound nbt = mobSpawnerLogic.write(new NBTTagCompound());
					mobSpawnerLogic.read(nbt);
				}
			}
		}
		return true;
	}

}
