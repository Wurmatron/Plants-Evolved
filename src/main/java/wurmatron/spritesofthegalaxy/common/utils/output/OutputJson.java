package wurmatron.spritesofthegalaxy.common.utils.output;

import net.minecraft.item.ItemStack;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IOutput;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.common.utils.StackHelper;

import java.util.HashMap;

public class OutputJson implements IOutput {

	public final String name;
	private final HashMap <StorageType, Integer> cost;
	private final String item;
	private final HashMap <String, Integer> requiredStructures;

	public OutputJson (String name,HashMap <StorageType, Integer> cost,String item,HashMap <String, Integer> requiredStructures) {
		this.name = name;
		this.cost = cost;
		this.item = item;
		this.requiredStructures = requiredStructures;
	}

	public OutputJson (String name,int cost,String item) {
		this.name = name;
		HashMap <StorageType, Integer> outputCost = new HashMap <> ();
		outputCost.put (StorageType.MINERAL,cost);
		this.cost = outputCost;
		this.item = item;
		this.requiredStructures = new HashMap <> ();
	}

	public OutputJson (String name,int cost,String item,HashMap <String, Integer> requiredStructures) {
		this.name = name;
		HashMap <StorageType, Integer> outputCost = new HashMap <> ();
		outputCost.put (StorageType.MINERAL,cost);
		this.cost = outputCost;
		this.item = item;
		this.requiredStructures = requiredStructures;
	}


	@Override
	public HashMap <StorageType, Integer> getCost () {
		return cost;
	}

	@Override
	public ItemStack getItem () {
		return StackHelper.convert (item);
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public HashMap <IStructure, Integer> getRequiredStructures () {
		HashMap <IStructure, Integer> structures = new HashMap <> ();
		if (requiredStructures != null && requiredStructures.size () > 0)
			for (String structure : requiredStructures.keySet ())
				structures.put (SpritesOfTheGalaxyAPI.getStructureFromName (structure),requiredStructures.get (structure));
		else
			return new HashMap <> ();
		return structures;
	}
}
