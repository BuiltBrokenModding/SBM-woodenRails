package com.builtbroken.woodenrails.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author p455w0rd
 *
 */
public class ContainerCartWorkbench extends ContainerWorkbench {

	BlockPos accessiblePos;

	public ContainerCartWorkbench(InventoryPlayer playerInventory, World world, BlockPos pos) {
		super(playerInventory, world, pos);
		accessiblePos = pos;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(accessiblePos.getX() + 0.5D, accessiblePos.getY() + 0.5D, accessiblePos.getZ() + 0.5D) <= 64.0D;
	}

}
