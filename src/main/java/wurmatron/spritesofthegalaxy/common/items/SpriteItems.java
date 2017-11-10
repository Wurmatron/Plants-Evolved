package wurmatron.spritesofthegalaxy.common.items;

import net.minecraft.item.Item;
import wurmatron.spritesofthegalaxy.common.reference.Registry;

public class SpriteItems {

	public static Item spriteColony;
	public static Item mineral;

	public static void registerItems () {
		register (spriteColony = new ItemSpriteColony ());
		register (mineral = new ItemResource ());
	}

	public static void register (Item item) {
		Registry.registerItem (item,item.getUnlocalizedName ());
	}
}
