package wurmatron.spritesofthegalaxy.client.gui;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

public class GuiIncome extends GuiOverview {

	public GuiIncome (TileHabitatCore tile) {
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
