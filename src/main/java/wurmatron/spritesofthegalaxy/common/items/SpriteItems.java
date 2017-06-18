package wurmatron.spritesofthegalaxy.common.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wurmatron.spritesofthegalaxy.common.reference.Global;

public class SpriteItems {

	public static void registerItems () {

	}

	public static void register (Item item) {
		GameRegistry.register (item,new ResourceLocation (Global.MODID,item.getUnlocalizedName ()));
	}

}
