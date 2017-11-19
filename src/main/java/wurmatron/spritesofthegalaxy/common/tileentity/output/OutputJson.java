package wurmatron.spritesofthegalaxy.common.tileentity.output;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import wurmatron.spritesofthegalaxy.api.mutiblock.IOutput;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.common.utils.StackHelper;

import java.util.HashMap;

public class OutputJson implements IOutput {

	public final String name;
	public final HashMap <StorageType, Integer> cost;
	private final String item;

	public OutputJson (String name,HashMap <StorageType, Integer> cost,String item) {
		this.name = name;
		this.cost = cost;
		this.item = item;
	}

	public OutputJson (String name,int cost,String item) {
		this.name = name;
		HashMap <StorageType, Integer> outputCost = new HashMap <> ();
		outputCost.put (StorageType.MINERAL,cost);
		this.cost = outputCost;
		this.item = item;
	}

	@Override
	public HashMap <StorageType, Integer> getCost () {
		return cost;
	}

	@Override
	public ItemStack getItem () {
		return new ItemStack (Items.DIAMOND);
//		return StackHelper.convertToStack (item);
	}

	@Override
	public String getName () {
		return name;
	}
}
