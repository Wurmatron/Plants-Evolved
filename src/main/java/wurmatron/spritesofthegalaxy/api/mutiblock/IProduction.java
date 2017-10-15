package wurmatron.spritesofthegalaxy.api.mutiblock;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;

public interface IProduction {

	void addProduction (TileHabitatCore2 core,int structureTier);

	void removeProduction (TileHabitatCore2 core,int structureTier);

	int getAmountPerTier (TileHabitatCore2 core,int tier);

	EnumProductionType getType ();
}
