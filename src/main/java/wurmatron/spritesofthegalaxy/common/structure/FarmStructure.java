package wurmatron.spritesofthegalaxy.common.structure;

import net.minecraft.item.ItemStack;
import wurmatron.spritesofthegalaxy.api.mutiblock.EnumProductionType;
import wurmatron.spritesofthegalaxy.api.mutiblock.IProduction;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;

import java.util.HashMap;
import java.util.List;

public class FarmStructure implements IStructure, IProduction {

	@Override
	public String getName () {
		return "farm";
	}

	@Override
	public HashMap<IResearch, Integer> getRequiredResearch () {
		return null;
	}

	@Override
	public List<ItemStack> getCost (int researchLevel,int structureTier) {
		return null;
	}

	@Override
	public void tickStructure (TileHabitatCore core) { }

	@Override
	public void addProduction (TileHabitatCore core, int structureTier) {
		core.addFood (structureTier);
	}

	@Override
	public EnumProductionType getType () {
		return EnumProductionType.VALUE;
	}
}
