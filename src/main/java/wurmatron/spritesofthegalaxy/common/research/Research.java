package wurmatron.spritesofthegalaxy.common.research;

import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;

import java.util.HashMap;

public class Research implements IResearch {

	private String name;
	private int baseCost;
	private HashMap <IResearch, Integer> preReq;
	private ResearchType type;

	public Research (String name,int baseCost,HashMap <IResearch, Integer> preReq,ResearchType type) {
		this.name = name;
		this.baseCost = baseCost;
		this.preReq = preReq;
		this.type = type;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public int getBaseResearchCost () {
		return baseCost;
	}

	@Override
	public HashMap <IResearch, Integer> getPreReq () {
		return preReq;
	}

	@Override
	public ResearchType getResearchTab () {
		return type;
	}

	@Override
	public String toString () {
		return "[" + name + "]" + baseCost +"_" + type;
	}
}
