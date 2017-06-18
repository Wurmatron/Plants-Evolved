package wurmatron.spritesofthegalaxy.common.module;

import wurmatron.spritesofthegalaxy.api.mutiblock.IModule;
import wurmatron.spritesofthegalaxy.api.mutiblock.ModuleType;
import wurmatron.spritesofthegalaxy.api.research.IResearch;

public class Module implements IModule {

	private String name;
	private ModuleType type;
	private int maxTier;
	private IResearch research;

	public Module (String name,ModuleType type,int maxTier,IResearch research) {
		this.name = name;
		this.type = type;
		this.maxTier = maxTier;
		this.research = research;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public ModuleType getType () {
		return type;
	}

	@Override
	public int getMaxTier () {
		return maxTier;
	}

	@Override
	public IResearch getResearch () {
		return research;
	}
}
