package com.builtbroken.woodenrails.item;

import com.builtbroken.woodenrails.WoodenRails.EnumCartTypes;

/**
 * @author p455w0rd
 *
 */
public class ItemCartSpawner extends ItemWoodenCart {

	public ItemCartSpawner() {
		super();
	}

	@Override
	public EnumCartTypes getCartType() {
		return EnumCartTypes.SPAWNER;
	}

}
