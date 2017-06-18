package wurmatron.spritesofthegalaxy.client.gui;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatController;

public class GuiStats extends GuiOverview {

	public GuiStats (TileHabitatController tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		stats.enabled = false;
	}
}
