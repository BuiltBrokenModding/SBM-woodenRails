package com.builtbroken.woodenrails.item;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;

/**
 * @author p455w0rd
 *
 */
public class ItemCartFurnace extends ItemWoodenCart {

	public ItemCartFurnace() {
		super();
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.FURNACE;
	}

}
