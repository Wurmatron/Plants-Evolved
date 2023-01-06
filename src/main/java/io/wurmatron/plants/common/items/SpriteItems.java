package io.wurmatron.plants.common.items;

import net.minecraft.item.Item;
import io.wurmatron.plants.common.reference.Registry;

public class SpriteItems {

	public static Item spriteColony;
	public static Item mineral;

	public static void registerItems () {
		register (spriteColony = new ItemSpriteColony ());
		register (mineral = new ItemResource ());
	}

	public static void register (Item item) {
		Registry.registerItem (item,item.getUnlocalizedName ().substring (5));
	}
}
