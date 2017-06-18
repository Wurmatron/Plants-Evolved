package wurmatron.spritesofthegalaxy.common.module;

import wurmatron.spritesofthegalaxy.api.mutiblock.IModule;
import wurmatron.spritesofthegalaxy.api.mutiblock.IModuleStorage;
import wurmatron.spritesofthegalaxy.api.mutiblock.ModuleType;
import wurmatron.spritesofthegalaxy.api.research.IResearch;

public class ModuleStorage implements IModule, IModuleStorage {

	private String name;
	private ModuleType type;
	private int maxTier;
	private IResearch research;
	private StorageType storageType;
	private int baseStorage;

	public ModuleStorage (String name,ModuleType type,int maxTier,IResearch research,StorageType storageType,int baseStorage) {
		this.name = name;
		this.type = type;
		this.maxTier = maxTier;
		this.research = research;
		this.storageType = storageType;
		this.baseStorage = baseStorage;
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

	@Override
	public StorageType getStorageType () {
		return storageType;
	}

	@Override
	public int getBaseStorage () {
		return baseStorage;
	}
}
