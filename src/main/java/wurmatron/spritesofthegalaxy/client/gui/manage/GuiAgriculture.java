package wurmatron.spritesofthegalaxy.client.gui.manage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Keyboard;
import wurmatron.spritesofthegalaxy.api.mutiblock.IProduction;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.BuildStructureMessage;
import wurmatron.spritesofthegalaxy.common.network.server.DestroyStructureMessage;
import wurmatron.spritesofthegalaxy.common.network.server.ResearchUpdateMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.structure.FarmStructure;
import wurmatron.spritesofthegalaxy.common.structure.StructureHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiAgriculture extends GuiHabitatBase {

	protected GuiButton landAdd;
	protected GuiButton landSub;
	protected GuiButton greenAdd;
	protected GuiButton greenSub;
	protected GuiButton underAdd;
	protected GuiButton underSub;
	protected GuiButton hydroAdd;
	protected GuiButton hydroSub;
	protected GuiButton syncAdd;
	protected GuiButton syncSub;
	protected GuiButton augAdd;
	protected GuiButton augSub;

	public GuiAgriculture (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		super.drawScreen (mouseX,mouseY,partialTicks);

		displayString (StructureHelper.farmStructure,mouseX,mouseY,62,31,4,29,106,29);

		//		drawString (fontRenderer,I18n.translateToLocal (FarmStructure.NAME + " lvl " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.land)),startWidth + 57 - fontRenderer.getStringWidth (FarmStructure.NAME + " lvl" + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.land))) / 2,startHeight + 24,Color.white.getRGB ());
		//		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.greenHouse.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.greenHouse)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.greenHouse.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.greenHouse))) / 2,startHeight + 40,Color.white.getRGB ());
		//		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.underGround.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.underGround)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.underGround.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.underGround))) / 2,startHeight + 56,Color.white.getRGB ());
		//		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.hydroPonics.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.hydroPonics)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.hydroPonics.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.hydroPonics))) / 2,startHeight + 72,Color.white.getRGB ());
		//		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.synthesis.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.synthesis)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.synthesis.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.synthesis))) / 2,startHeight + 88,Color.white.getRGB ());
		//		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.augmentation.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.augmentation)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.augmentation.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.augmentation))) / 2,startHeight + 104,Color.white.getRGB ());
	}

	@Override
	public void initGui () {
		super.initGui ();
		buttonList.add (landAdd = new GuiButton (100,startWidth + 4,startHeight + 29,12,12,"+"));
		buttonList.add (landSub = new GuiButton (101,startWidth + 106,startHeight + 29,12,12,"-"));
		//		buttonList.add (greenAdd = new GuiButton (102,startWidth + 4,startHeight + 39,12,12,"+"));
		//		buttonList.add (greenSub = new GuiButton (103,startWidth + 96,startHeight + 39,12,12,"-"));
		//		buttonList.add (underAdd = new GuiButton (104,startWidth + 4,startHeight + 55,12,12,"+"));
		//		buttonList.add (underSub = new GuiButton (105,startWidth + 96,startHeight + 55,12,12,"-"));
		//		buttonList.add (hydroAdd = new GuiButton (106,startWidth + 4,startHeight + 71,12,12,"+"));
		//		buttonList.add (hydroSub = new GuiButton (107,startWidth + 96,startHeight + 71,12,12,"-"));
		//		buttonList.add (syncAdd = new GuiButton (108,startWidth + 4,startHeight + 87,12,12,"+"));
		//		buttonList.add (syncSub = new GuiButton (109,startWidth + 96,startHeight + 87,12,12,"-"));
		//		buttonList.add (augAdd = new GuiButton (110,startWidth + 4,startHeight + 103,12,12,"+"));
		//		buttonList.add (augSub = new GuiButton (111,startWidth + 96,startHeight + 103,12,12,"-"));
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		super.actionPerformed (button);
		switch (button.id) {
			case (100): {
				proccessButton (StructureHelper.farmStructure);
				break;
			}
			case (101): {
				destroyButton (StructureHelper.farmStructure);
				break;
			}
		}
	}

	private int getStructureLevel (IStructure structure) {
		if (structure != null && tile != null) {
			HashMap <IStructure, Integer> currentStructure = tile.getStructures ();
			if (currentStructure != null && currentStructure.size () > 0 && currentStructure.containsKey (structure))
				return currentStructure.get (structure);
		}
		return 0;
	}

	private int keyAmount () {
		int amt = 1;
		if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL))
			if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT))
				amt = 10;
			else
				amt = 5;
		return amt;
	}

	private void proccessButton (IStructure structure) {
		int currentTier = getStructureLevel (structure);
		int nextTier = currentTier + keyAmount ();
		if (MutiBlockHelper.canBuildStructure (tile,structure,currentTier,nextTier)) {
			tile.eatMinerals (MutiBlockHelper.calcMineralsForStructure (structure,getStructureLevel (structure),nextTier,0));
			NetworkHandler.sendToServer (new BuildStructureMessage (structure,nextTier,tile.getPos ()));
		} else
			mc.ingameGUI.getChatGUI ().printChatMessage (new TextComponentString (I18n.translateToLocal (Local.NEED_MINERALS).replaceAll ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStructure (structure,currentTier,nextTier,0) - tile.getMinerals ()))));
	}

	private void destroyButton (IStructure structure) {
		int nextTier = keyAmount ();
		if (getStructureLevel (structure) - keyAmount () >= 0) {
			tile.addMinerals (MutiBlockHelper.calcMineralsForStructure (structure,nextTier,getStructureLevel (structure),0));
			NetworkHandler.sendToServer (new DestroyStructureMessage (structure,nextTier,tile.getPos ()));
		}
	}


	private boolean isWithin (int mouseX,int mouseY,int x,int y,int x2,int y2) {
		return mouseX >= x && mouseX < x2 && mouseY >= y && mouseY < y2;
	}

	private void displayString (IStructure structure,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal (structure.getDisplayName ()) + " lvl " + DisplayHelper.formatNum (getStructureLevel (structure));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (structure instanceof IProduction)
			if (isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 12,startHeight + buttY + 12)) {
				List <String> displayInfo = new ArrayList ();
				IProduction production = (IProduction) structure;
				displayInfo.add ("Currently Provides: " + DisplayHelper.formatNum (getStructureLevel (structure)));
				displayInfo.add ("Next Level:            " + DisplayHelper.formatNum (production.getAmountPerTier (tile,getStructureLevel (structure) + keyAmount ())));
				displayInfo.add ("Minerals:                -" + DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStructure (structure,getStructureLevel (structure),getStructureLevel (structure) + keyAmount (),0)));
				drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
			}
		if (isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 12,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			IProduction production = (IProduction) structure;
			displayInfo.add ("Currently Provides: " + DisplayHelper.formatNum (getStructureLevel (structure)));
			displayInfo.add ("Prev Level:           " + DisplayHelper.formatNum (production.getAmountPerTier (tile,getStructureLevel (structure) - keyAmount ())));
			displayInfo.add ("Minerals:               +" + DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStructure (structure,getStructureLevel (structure) - keyAmount (),getStructureLevel (structure),0)));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}
}
