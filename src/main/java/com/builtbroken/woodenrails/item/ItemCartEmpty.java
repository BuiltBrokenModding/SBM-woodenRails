package com.builtbroken.woodenrails.item;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;

/**
 * @author p455w0rd
 *
 */
public class ItemCartEmpty extends ItemWoodenCart {

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.EMPTY;
	}

}
