package wurmatron.spritesofthegalaxy.common.module;

import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IModuleStorage;
import wurmatron.spritesofthegalaxy.api.mutiblock.ModuleType;
import wurmatron.spritesofthegalaxy.common.config.Settings;

public class ModuleHelper {

	// Storage
	public static final ModuleStorage population = new ModuleStorage ("population",ModuleType.STORAGE,Settings.maxTier,null,IModuleStorage.StorageType.POPULATION,10);
	public static final ModuleStorage garden = new ModuleStorage ("garden",ModuleType.STORAGE,Settings.maxTier,null,IModuleStorage.StorageType.FOOD,9);

	public static void registerModules () {
		SpritesOfTheGalaxyAPI.register (population);
		SpritesOfTheGalaxyAPI.register (garden);
	}

	public static int adjustStorageForTier (int baseValue,int tier) {
		if (tier > 1)
			return baseValue + (baseValue * tier);
		return baseValue;
	}
}
