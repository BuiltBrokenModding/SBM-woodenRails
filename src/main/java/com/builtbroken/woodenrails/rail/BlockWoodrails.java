package com.builtbroken.woodenrails.rail;

import com.builtbroken.woodenrails.WoodenRails;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Robert on 8/6/2015.
 */
public class BlockWoodrails extends BlockRailBase
{
    @SideOnly(Side.CLIENT)
    private IIcon turnedTrack;

    public BlockWoodrails()
    {
        super(false);
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
        this.setUnlocalizedName(WoodenRails.PREFIX + "woodenRail");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return meta >= 6 ? this.turnedTrack : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        this.blockIcon = reg.registerIcon(WoodenRails.PREFIX + "rail_normal");
        this.turnedTrack = reg.registerIcon(WoodenRails.PREFIX + "rail_normal_turned");
    }

	@Override
	public IProperty<EnumRailDirection> getShapeProperty() {
		return null;
	}
}
