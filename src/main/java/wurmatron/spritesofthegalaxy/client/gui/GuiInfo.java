package wurmatron.spritesofthegalaxy.client.gui;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatController;

public class GuiInfo extends GuiOverview {

	public GuiInfo (TileHabitatController tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		info.enabled = false;
	}
}
