package com.builtbroken.woodenrails.item;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;

/**
 * @author p455w0rd
 *
 */
public class ItemCartTNT extends ItemWoodenCart {

	public ItemCartTNT() {
		super();
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.TNT;
	}

}
