package wurmatron.spritesofthegalaxy.api.mutiblock;

import net.minecraft.item.ItemStack;

public interface IOutput {

	String getName ();

	int getCost ();

	ItemStack getItem ();
}
