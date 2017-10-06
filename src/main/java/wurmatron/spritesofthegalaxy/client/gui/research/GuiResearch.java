package wurmatron.spritesofthegalaxy.client.gui.research;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;

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
		buttonList.add (agriculture = new GuiTexturedButton (10,startWidth + 171,startHeight + 21,60,15,I18n.translateToLocal (Local.RESEARCH_AGRICLTURE) + " (" + DisplayHelper.formatNum (tile.getResearshPoints (ResearchType.ARGICULTURE)) + ")"));
		buttonList.add (energy = new GuiTexturedButton (11,startWidth + 171,startHeight + 37,60,15,I18n.translateToLocal (Local.RESEARCH_ENERGY) + " (" + DisplayHelper.formatNum (tile.getResearshPoints (ResearchType.ENERGY)) + ")"));
		buttonList.add (industry = new GuiTexturedButton (12,startWidth + 171,startHeight + 53,60,15,I18n.translateToLocal (Local.RESEARCH_INDUSTRY) + " (" + DisplayHelper.formatNum (tile.getResearshPoints (ResearchType.INDUSTRY)) + ")"));
		buttonList.add (magic = new GuiTexturedButton (13,startWidth + 171,startHeight + 69,60,15,I18n.translateToLocal (Local.RESEARCH_MAGIC) + " (" + DisplayHelper.formatNum (tile.getResearshPoints (ResearchType.MAGIC)) + ")"));
		buttonList.add (research = new GuiTexturedButton (14,startWidth + 171,startHeight + 85,60,15,I18n.translateToLocal (Local.RESEARCH_RESEARCH) + " (" + DisplayHelper.formatNum (tile.getResearshPoints (ResearchType.RESEARCH)) + ")"));
		buttonList.add (unique = new GuiTexturedButton (15,startWidth + 171,startHeight + 101,60,15,I18n.translateToLocal (Local.RESEARCH_UNIQUE) + " (" + DisplayHelper.formatNum (tile.getResearshPoints (ResearchType.UNIQUE)) + ")"));
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
		}
	}
}
