package wurmatron.spritesofthegalaxy.common.tileentity.output;

import net.minecraft.item.ItemStack;
import wurmatron.spritesofthegalaxy.api.mutiblock.IOutput;
import wurmatron.spritesofthegalaxy.common.utils.StackHelper;

public class OutputJson implements IOutput {

	public final String name;
	public final int cost;
	private final String item;

	public OutputJson (String name,int cost,String item) {
		this.name = name;
		this.cost = cost;
		this.item = item;
	}

	@Override
	public int getCost () {
		return cost;
	}

	@Override
	public ItemStack getItem () {
		return StackHelper.convertToStack (item);
	}

	@Override
	public String getName () {
		return name;
	}
}
