package com.builtbroken.woodenrails.item;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;

/**
 * @author p455w0rd
 *
 */
public class ItemCartChest extends ItemWoodenCart {

	public ItemCartChest() {
		super();
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.CHEST;
	}

}
