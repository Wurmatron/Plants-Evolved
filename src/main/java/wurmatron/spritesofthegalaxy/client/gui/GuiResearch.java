package wurmatron.spritesofthegalaxy.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.io.IOException;

public class GuiResearch extends GuiHabitatBase {

	protected GuiButton agriculture;
	protected GuiButton industry;
	protected GuiButton energy;
	protected GuiButton researchResearch;
	protected GuiButton magic;
	protected GuiButton unique;

	public GuiResearch (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		int startWidth = width / 20 + width / 60;
		int buttonHeight = height / 20;
		buttonList.add (agriculture = new GuiTexturedButton (10,startWidth + ((width / 6) * 4),(buttonHeight * 2) + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.AGRICULTURE)));
		buttonList.add (industry = new GuiTexturedButton (11,startWidth + ((width / 6) * 4),(buttonHeight * 3) + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.RESEARCH_INDUSTRY)));
		buttonList.add (energy = new GuiTexturedButton (12,startWidth + ((width / 6) * 4),buttonHeight * 4 + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.RESEARCH_ENERGY)));
		buttonList.add (researchResearch = new GuiTexturedButton (13,startWidth + ((width / 6) * 4),buttonHeight * 5 + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.RESEARCH_RESEARCH)));
		buttonList.add (magic = new GuiTexturedButton (14,startWidth + ((width / 6) * 4),buttonHeight * 6 + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.RESEARCH_MAGIC)));
		buttonList.add (unique = new GuiTexturedButton (12,startWidth + ((width / 6) * 4),buttonHeight * 4 + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.RESEARCH_UNIQUE)));
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		super.actionPerformed (button);
		switch (button.id) {
			case (10):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_ARGICULTURE,tile.getPos ()));
				break;
			case (11):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_INDUSTRY,tile.getPos ()));
				break;
			case (12):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_ENERGY,tile.getPos ()));
				break;
			case (13):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_RESEARCH,tile.getPos ()));
				break;
			case (14):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_MAGIC,tile.getPos ()));
				break;
			case (15):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_UNIQUE,tile.getPos ()));
		}
	}
}
