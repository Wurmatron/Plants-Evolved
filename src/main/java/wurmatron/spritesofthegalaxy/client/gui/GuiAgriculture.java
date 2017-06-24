package wurmatron.spritesofthegalaxy.client.gui;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

public class GuiAgriculture extends GuiManage {

	public GuiAgriculture (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		agriculture.visible = false;
		mines.visible = false;
		energyProduction.visible = false;
		magicProduction.visible= false;
		mobFarm.visible = false;
		liquidFarm.visible = false;
		nursery.visible = false;
	}
}
