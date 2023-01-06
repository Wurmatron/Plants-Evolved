package io.wurmatron.plants.client.gui.research;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import io.wurmatron.plants.api.PlantsEvolvedAPI;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.api.research.ResearchType;
import io.wurmatron.plants.client.gui.GuiHabitatBase;
import io.wurmatron.plants.client.gui.utils.GuiTexturedButton;
import io.wurmatron.plants.common.network.NetworkHandler;
import io.wurmatron.plants.common.network.server.ResearchUpdateMessage;
import io.wurmatron.plants.common.reference.Local;
import io.wurmatron.plants.common.research.ResearchHelper;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;
import io.wurmatron.plants.common.utils.DisplayHelper;
import io.wurmatron.plants.common.utils.MutiBlockHelper;

import java.io.IOException;
import java.util.List;

public class GuiDiscover extends GuiHabitatBase {

	private List <IResearch> researchType;

	public GuiDiscover (TileHabitatCore tile,ResearchType researchType) {
		super (tile);
		this.researchType = PlantsEvolvedAPI.getResearchForType (researchType);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		for (int index = 0; index < researchType.size (); index++)
			if (index <= 10)
				displayString (researchType.get (index),mouseX,mouseY,62,31 + (16 * index),106,29 + (16 * index),4,29 + (16 * index));
	}

	@Override
	public void initGui () {
		super.initGui ();
		for (int index = 0; index < researchType.size (); index++)
			if (index <= 10) {
				buttonList.add (new GuiTexturedButton (100 + index,startWidth + 106,(startHeight + 29) + (16 * index),12,12,1,"+"));
				buttonList.add (new GuiTexturedButton (101 + index,startWidth + 4,(startHeight + 29) + (16 * index),12,12,1,"-"));
			}
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		for (int index = 0; index < researchType.size (); index++)
			if (index <= 10)
				if ((100 + index) == butt.id && butt.displayString.equalsIgnoreCase ("+"))
					proccessButton (researchType.get (index));
				else if ((101 + index) == butt.id && butt.displayString.equalsIgnoreCase ("-"))
					destroyButton (researchType.get (index));
	}

	private void proccessButton (IResearch research) {
		int currentTier = MutiBlockHelper.getResearchLevel (tile,research);
		int nextTier = currentTier + keyAmount ();
		if (MutiBlockHelper.canBuildResearchType (tile,research,currentTier,nextTier) && ResearchHelper.isValidMove (tile.getResearch (),research)) {
			tile.consumeResearchPoints (research.getResearchTab (),MutiBlockHelper.calcPointsForResearch (research,MutiBlockHelper.getResearchLevel (tile,research),nextTier));
			NetworkHandler.sendToServer (new ResearchUpdateMessage (research,nextTier,tile,false));
		} else {
			if (!ResearchHelper.isValidMove (tile.getResearch (),research)) {
				TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.MISSING_RESEARCH).replaceAll ("'Research'",TextFormatting.GOLD + DisplayHelper.formatNeededResearch (ResearchHelper.getNeededResearch (tile.getResearch (),research))));
				text.getStyle ().setColor (TextFormatting.RED);
				mc.ingameGUI.getChatGUI ().printChatMessage (text);
			} else {
				TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.NEED_POINTS).replaceAll ("'POINTS'",TextFormatting.GOLD + DisplayHelper.formatNum (MutiBlockHelper.calcPointsForResearch (research,currentTier,nextTier) - tile.getResearchPoints (research.getResearchTab ())) + TextFormatting.RED));
				text.getStyle ().setColor (TextFormatting.RED);
				mc.ingameGUI.getChatGUI ().printChatMessage (text);
			}
		}
	}

	private void destroyButton (IResearch research) {
		int nextTier = keyAmount ();
		if (MutiBlockHelper.getResearchLevel (tile,research) - keyAmount () >= 0) {
			tile.setResearchPoints (research.getResearchTab (),tile.getResearchPoints (research.getResearchTab ()) + MutiBlockHelper.calcPointsForResearch (research,nextTier,MutiBlockHelper.getResearchLevel (tile,research)));
			NetworkHandler.sendToServer (new ResearchUpdateMessage (research,MutiBlockHelper.getResearchLevel (tile,research) - keyAmount (),tile,true));
		}
	}
}
