package com.builtbroken.woodenrails.item;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;

/**
 * @author p455w0rd
 *
 */
public class ItemCartHopper extends ItemWoodenCart {

	public ItemCartHopper() {
		super();
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.HOPPER;
	}

}
