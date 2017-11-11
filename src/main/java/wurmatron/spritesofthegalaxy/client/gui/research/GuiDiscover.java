package wurmatron.spritesofthegalaxy.client.gui.research;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.ResearchUpdateMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiDiscover extends GuiHabitatBase {

	private List <IResearch> researchType;

	public GuiDiscover (TileHabitatCore2 tile,ResearchType researchType) {
		super (tile);
		this.researchType = SpritesOfTheGalaxyAPI.getResearchForType (researchType);
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
			tile.setResearchPoints (research.getResearchTab (),tile.getResearchPoints (research.getResearchTab ()) - MutiBlockHelper.calcPointsForResearch (research,MutiBlockHelper.getResearchLevel (tile,research),nextTier));
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

	private void displayString (IResearch research,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal (research.getName ()) + " lvl " + DisplayHelper.formatNum (MutiBlockHelper.getResearchLevel (tile,research));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 13,startHeight + buttY + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add (I18n.translateToLocal (Local.COST_RESEARCH).replace ("'POINTS'",DisplayHelper.formatNum (MutiBlockHelper.calcPointsForResearch (research,MutiBlockHelper.getResearchLevel (tile,research),MutiBlockHelper.getResearchLevel (tile,research) + keyAmount ()))));
			drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
		}
		if (isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 13,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add (I18n.translateToLocal (Local.GIVE_RESEARCH).replace ("'POINTS'",DisplayHelper.formatNum (MutiBlockHelper.calcPointsForResearch (research,MutiBlockHelper.getResearchLevel (tile,research) - keyAmount (),MutiBlockHelper.getResearchLevel (tile,research)))));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}
}
