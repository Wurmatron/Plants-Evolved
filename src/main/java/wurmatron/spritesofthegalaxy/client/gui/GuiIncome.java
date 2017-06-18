package wurmatron.spritesofthegalaxy.client.gui;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatController;

public class GuiIncome extends GuiOverview {

	public GuiIncome (TileHabitatController tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		income.enabled = false;
	}
}
