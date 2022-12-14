package io.wurmatron.plants.client.gui.research;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;
import io.wurmatron.plants.api.research.ResearchType;
import io.wurmatron.plants.client.GuiHandler;
import io.wurmatron.plants.client.gui.GuiHabitatBase;
import io.wurmatron.plants.client.gui.utils.GuiTexturedButton;
import io.wurmatron.plants.common.network.NetworkHandler;
import io.wurmatron.plants.common.network.server.OpenGuiMessage;
import io.wurmatron.plants.common.reference.Local;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;
import io.wurmatron.plants.common.utils.DisplayHelper;

import java.io.IOException;

public class GuiResearch extends GuiHabitatBase {

	protected GuiButton agriculture;
	protected GuiButton energy;
	protected GuiButton industry;
	protected GuiButton magic;
	protected GuiButton research;
	protected GuiButton unique;

	public GuiResearch (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		buttonList.add (agriculture = new GuiTexturedButton (10,startWidth + 160,startHeight + 21,80,15,1,I18n.translateToLocal (Local.RESEARCH_AGRICLTURE) + " (" + DisplayHelper.formatNum (tile.getResearchPoints (ResearchType.ARGICULTURE)) + ")"));
		buttonList.add (energy = new GuiTexturedButton (11,startWidth + 160,startHeight + 37,80,15,1,I18n.translateToLocal (Local.RESEARCH_ENERGY) + " (" + DisplayHelper.formatNum (tile.getResearchPoints (ResearchType.ENERGY)) + ")"));
		buttonList.add (industry = new GuiTexturedButton (12,startWidth + 160,startHeight + 53,80,15,1,I18n.translateToLocal (Local.RESEARCH_INDUSTRY) + " (" + DisplayHelper.formatNum (tile.getResearchPoints (ResearchType.INDUSTRY)) + ")"));
		buttonList.add (magic = new GuiTexturedButton (13,startWidth + 160,startHeight + 69,80,15,1,I18n.translateToLocal (Local.RESEARCH_MAGIC) + " (" + DisplayHelper.formatNum (tile.getResearchPoints (ResearchType.MAGIC)) + ")"));
		buttonList.add (research = new GuiTexturedButton (14,startWidth + 160,startHeight + 85,80,15,1,I18n.translateToLocal (Local.RESEARCH_RESEARCH) + " (" + DisplayHelper.formatNum (tile.getResearchPoints (ResearchType.RESEARCH)) + ")"));
		buttonList.add (unique = new GuiTexturedButton (15,startWidth + 160,startHeight + 101,80,15,1,I18n.translateToLocal (Local.RESEARCH_UNIQUE) + " (" + DisplayHelper.formatNum (tile.getResearchPoints (ResearchType.UNIQUE)) + ")"));
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		switch (butt.id) {
			case (10):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_ARGICULTURE,tile.getPos ()));
				break;
			case (11):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_ENERGY,tile.getPos ()));
				break;
			case (12):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_INDUSTRY,tile.getPos ()));
				break;
			case (13):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_MAGIC,tile.getPos ()));
				break;
			case (14):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_RESEARCH,tile.getPos ()));
				break;
			case (15):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_UNIQUE,tile.getPos ()));
				break;
			default:
				break;
		}
	}
}
