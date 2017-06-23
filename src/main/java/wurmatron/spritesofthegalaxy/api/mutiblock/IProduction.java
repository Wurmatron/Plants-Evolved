package wurmatron.spritesofthegalaxy.api.mutiblock;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

public interface IProduction {

	void addProduction(TileHabitatCore core, int structureTier);

	EnumProductionType getType();
}
