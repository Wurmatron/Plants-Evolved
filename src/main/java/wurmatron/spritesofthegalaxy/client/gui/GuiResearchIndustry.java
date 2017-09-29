package wurmatron.spritesofthegalaxy.client.gui;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

public class GuiResearchIndustry extends GuiResearch {

	public GuiResearchIndustry (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		agriculture.visible = false;
		industry.visible = false;
		energy.visible = false;
		researchResearch.visible = false;
		magic.visible = false;
		unique.visible = false;
	}
}
