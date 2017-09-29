package wurmatron.spritesofthegalaxy.common.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.client.proxy.ClientProxy;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.reference.Registry;

public class SpriteItems {

	public static Item spriteColony;

	public static void registerItems () {
		register (spriteColony = new ItemSpriteColony ());
	}

	public static void register (Item item) {
		Registry.registerItem (item,item.getUnlocalizedName ());
	}
}
