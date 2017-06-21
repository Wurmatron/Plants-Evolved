package wurmatron.spritesofthegalaxy.api.mutiblock;

public interface IModuleStorage {

	/**
	 What this module stores
	 */
	StorageType getStorageType ();

	/**
	 Maximum amount of storage
	 */
	int getBaseStorage ();

	enum StorageType {
		POPULATION,ENERGY,FOOD,WATER,RESOURCE
	}
}
