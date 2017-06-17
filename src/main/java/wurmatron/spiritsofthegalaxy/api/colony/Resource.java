package wurmatron.spiritsofthegalaxy.api.colony;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Resource {

	private Item item;
	private int meta;
	private int tier;

	public Resource (Item item,int meta,int tier) {
		this.item = item;
		this.meta = meta;
		this.tier = tier;
	}

	public ItemStack getStack (int amount) {
		return new ItemStack (item,amount,meta);
	}

	public int getTier () {
		return tier;
	}
}
