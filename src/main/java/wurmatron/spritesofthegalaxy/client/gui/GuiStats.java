package wurmatron.spritesofthegalaxy.client.gui;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

public class GuiStats extends GuiOverview {

	public GuiStats (TileHabitatCore tile) {
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
