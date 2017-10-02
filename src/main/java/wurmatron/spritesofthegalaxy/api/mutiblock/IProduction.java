package wurmatron.spritesofthegalaxy.api.mutiblock;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

public interface IProduction {

	void addProduction (TileHabitatCore core,int structureTier);

	void removeProduction (TileHabitatCore core,int structureTier);

	int getAmountPerTier(TileHabitatCore core, int tier) ;

	EnumProductionType getType ();
}
