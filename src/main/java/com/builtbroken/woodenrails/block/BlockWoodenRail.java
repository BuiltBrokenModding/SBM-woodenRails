package com.builtbroken.woodenrails.block;

import com.builtbroken.woodenrails.WoodenRails;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;

/**
 * Created by Robert on 8/6/2015.
 */
public class BlockWoodenRail extends BlockRail {

	public BlockWoodenRail() {
		super(Block.Properties.create(Material.CIRCUITS).doesNotBlockMovement().hardnessAndResistance(0.7F).sound(SoundType.WOOD));
		this.setRegistryName(WoodenRails.MOD_ID, "wooden_rail");
	}

}
