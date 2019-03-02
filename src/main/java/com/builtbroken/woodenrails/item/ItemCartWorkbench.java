package com.builtbroken.woodenrails.item;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;

/**
 * @author p455w0rd
 *
 */
public class ItemCartWorkbench extends ItemWoodenCart {

	public ItemCartWorkbench() {
		super();
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.WORKBENCH;
	}

}
