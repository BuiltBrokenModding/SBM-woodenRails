package com.builtbroken.woodenrails.rail;

import com.builtbroken.woodenrails.WoodenRails;

import net.minecraft.block.BlockRail;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by Robert on 8/6/2015.
 */
public class BlockWoodenRail extends BlockRail
{
    public BlockWoodenRail()
    {
        super();
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
        this.setRegistryName("wooden_rail");
        this.setTranslationKey(WoodenRails.PREFIX + "wooden_rail");
        this.setSoundType(SoundType.WOOD);
    }
}
