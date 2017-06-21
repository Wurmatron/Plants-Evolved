package wurmatron.spritesofthegalaxy.client.gui;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

public class GuiInfo extends GuiOverview {

	public GuiInfo (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		income.visible = false;
		info.visible = false;
		stats.visible = false;
	}
}
