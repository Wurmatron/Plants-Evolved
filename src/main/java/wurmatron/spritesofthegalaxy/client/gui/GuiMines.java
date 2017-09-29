package wurmatron.spritesofthegalaxy.client.gui;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

public class GuiMines extends GuiManage {

	public GuiMines (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		mines.visible = false;
		energyProduction.visible = false;
		magicProduction.visible = false;
		mobFarm.visible = false;
		liquidFarm.visible = false;
		nursery.visible = false;
	}
}
