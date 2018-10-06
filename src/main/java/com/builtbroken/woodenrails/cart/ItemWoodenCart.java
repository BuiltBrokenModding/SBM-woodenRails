package com.builtbroken.woodenrails.cart;

import java.awt.Color;
import java.util.List;

import com.builtbroken.woodenrails.WoodenRails;
import com.builtbroken.woodenrails.cart.types.EntityChestCart;
import com.builtbroken.woodenrails.cart.types.EntityEmptyCart;
import com.builtbroken.woodenrails.cart.types.EntityFurnaceCart;
import com.builtbroken.woodenrails.cart.types.EntityHopperCart;
import com.builtbroken.woodenrails.cart.types.EntityTNTCart;
import com.builtbroken.woodenrails.cart.types.EntityTankCart;
import com.builtbroken.woodenrails.cart.types.EntityWorkbenchCart;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Dark on 7/25/2015.
 */
public class ItemWoodenCart extends Item
{
    public boolean enableColoredChestSupport;

    public ItemWoodenCart()
    {
        this.setMaxStackSize(3);
        this.setHasSubtypes(true);
        this.setRegistryName("wooden_cart");
        this.setTranslationKey(WoodenRails.PREFIX + "wooden_cart");
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new DispenserMinecartBehavior());
        enableColoredChestSupport = Loader.isModLoaded("coloredchests");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (BlockRailBase.isRailBlock(world.getBlockState(pos)))
        {
            ItemStack itemStack = player.getHeldItem(hand);

            EntityWoodenCart cart = createNewCart(world, itemStack);
            if (cart != null)
            {
                if (!world.isRemote)
                {
                    cart.setPosition(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);

                    if(itemStack.hasDisplayName())
                        cart.setCustomNameTag(itemStack.getDisplayName());

                    world.spawnEntity(cart);

                    if (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("rgb"))
                    {
                        cart.setBlockRenderColor(itemStack.getTagCompound().getInteger("rgb"));
                    }
                }
                if (!player.capabilities.isCreativeMode)
                    itemStack.setCount(itemStack.getCount() - 1);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flag)
    {
        if (stack.getItemDamage() >= 0 && stack.getItemDamage() < EnumCartTypes.values().length)
        {
            EnumCartTypes type = EnumCartTypes.values()[stack.getItemDamage()];
            if (type == EnumCartTypes.HOPPER || type == EnumCartTypes.WORKBENCH || type == EnumCartTypes.TANK)
                tooltip.add("Not implemented");
        }
        if (stack != null && stack.getTagCompound() != null)
        {
            if (stack.getTagCompound().hasKey("rgb"))
            {
                Color color = WoodenRails.getColor(stack.getTagCompound().getInteger("rgb"));
                tooltip.add("R: " + color.getRed() + " G: " + color.getGreen() + " B: " + color.getBlue());
            }

            if (stack.getTagCompound().hasKey("colorName"))
            {
                tooltip.add("N: " + stack.getTagCompound().getString("colorName"));
            }
        }
    }

    //    @Override TODO: Somehow reimplement if colored chests gets updated to 1.12.2
    //    @SideOnly(Side.CLIENT)
    //    public int getColorFromItemStack(ItemStack stack, int pass)
    //    {
    //        if (pass == 1 && stack.getTagCompound() != null && stack.getTagCompound().hasKey("rgb"))
    //        {
    //            return stack.getTagCompound().getInteger("rgb");
    //        }
    //        return 16777215;
    //    }

    @Override
    public String getTranslationKey(ItemStack itemStack)
    {
        EnumCartTypes type = EnumCartTypes.EMPTY;
        if (itemStack.getItemDamage() >= 0 && itemStack.getItemDamage() < EnumCartTypes.values().length)
        {
            type = EnumCartTypes.values()[itemStack.getItemDamage()];
        }
        return getTranslationKey() + "." + type.name().toLowerCase();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        for (EnumCartTypes type : EnumCartTypes.values())
        {
            if(isInCreativeTab(tab))
                items.add(new ItemStack(this, 1, type.ordinal()));
            //            if (type == EnumCartTypes.CHEST && enableColoredChestSupport) TODO: readd if colored chest gets updated to 1.12.2
            //            {
            //                for (int i = 0; i < ItemDye.field_150922_c.length; i++)
            //                {
            //                    ItemStack stack = new ItemStack(item, 1, type.ordinal());
            //                    stack.setTagCompound(new NBTTagCompound());
            //                    stack.getTagCompound().setInteger("rgb", ItemDye.field_150922_c[i]);
            //                    stack.getTagCompound().setString("colorName", ItemDye.field_150921_b[i]);
            //                    items.add(stack);
            //                }
            //            }
        }
    }

    public static EntityWoodenCart createNewCart(World world, ItemStack itemStack)
    {
        if (itemStack.getItemDamage() >= 0 && itemStack.getItemDamage() < EnumCartTypes.values().length)
        {
            switch (EnumCartTypes.values()[itemStack.getItemDamage()])
            {
                case CHEST:
                    return new EntityChestCart(world);
                case TNT:
                    return new EntityTNTCart(world);
                case FURNACE:
                    return new EntityFurnaceCart(world);
                case HOPPER:
                    return new EntityHopperCart(world);
                case WORKBENCH:
                    return new EntityWorkbenchCart(world);
                case TANK:
                    return new EntityTankCart(world);
                case EMPTY:
                    break;
            }
        }
        return new EntityEmptyCart(world);
    }
}
