package wurmatron.spritesofthegalaxy.api.mutiblock;

import net.minecraft.item.ItemStack;

import java.util.HashMap;

public interface IOutput {

	String getName ();

	HashMap<StorageType, Integer> getCost ();

	ItemStack getItem ();
}
