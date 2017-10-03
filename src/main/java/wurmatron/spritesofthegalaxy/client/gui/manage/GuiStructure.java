package wurmatron.spritesofthegalaxy.client.gui.manage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IProduction;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StructureType;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.StructureMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiStructure extends GuiHabitatBase {

	private List <IStructure> structures;

	public GuiStructure (TileHabitatCore tile,StructureType structureType) {
		super (tile);
		structures = SpritesOfTheGalaxyAPI.getStructuresForType (structureType);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		for (int index = 0; index < structures.size (); index++)
			if (index <= 10)
				displayString (structures.get (index),mouseX,mouseY,62,31 + (16 * index),4,29 + (16 * index),106,29 + (16 * index));
	}

	@Override
	public void initGui () {
		super.initGui ();
		for (int index = 0; index < structures.size (); index++)
			if (index <= 10) {
				buttonList.add (new GuiButton (100 + index,startWidth + 4,(startHeight + 29) + (16 * index),12,12,"+"));
				buttonList.add (new GuiButton (101 + index,startWidth + 106,(startHeight + 29) + (16 * index),12,12,"-"));
			}
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		for (int index = 0; index < structures.size (); index++)
			if (index <= 10)
				if (butt.id % 2 == 0)
					proccessButton (structures.get (index));
				else
					destroyButton (structures.get (index));
	}

	private void proccessButton (IStructure structure) {
		int currentTier = MutiBlockHelper.getStructureLevel (tile,structure);
		int nextTier = currentTier + keyAmount ();
		if (MutiBlockHelper.canBuildStructure (tile,structure,currentTier,nextTier)) {
			tile.consumeMinerals (MutiBlockHelper.calcMineralsForStructure (structure,MutiBlockHelper.getStructureLevel (tile,structure),nextTier,0));
			NetworkHandler.sendToServer (new StructureMessage (structure,nextTier,tile,false));
		} else {
			TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.NEED_MINERALS).replaceAll ("'Minerals'",TextFormatting.GOLD + DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStructure (structure,currentTier,nextTier,0) - tile.getMinerals ())));
			text.getStyle ().setColor (TextFormatting.RED);
			mc.ingameGUI.getChatGUI ().printChatMessage (text);
		}
	}

	private void destroyButton (IStructure structure) {
		int nextTier = keyAmount ();
		if (MutiBlockHelper.getStructureLevel (tile,structure) - keyAmount () >= 0) {
			tile.addMinerals (MutiBlockHelper.calculateSellBack (MutiBlockHelper.calcMineralsForStructure (structure,nextTier,MutiBlockHelper.getStructureLevel (tile,structure),0)));
			NetworkHandler.sendToServer (new StructureMessage (structure,nextTier,tile,true));
		}
	}

	private void displayString (IStructure structure,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal (structure.getDisplayName ()) + " lvl " + DisplayHelper.formatNum (MutiBlockHelper.getStructureLevel (tile,structure));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (structure instanceof IProduction)
			if (isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 13,startHeight + buttY + 12)) {
				List <String> displayInfo = new ArrayList ();
				IProduction production = (IProduction) structure;
				displayInfo.add (I18n.translateToLocal (Local.CURRENT_PROVIDE).replace ("'PRODUCE'",DisplayHelper.formatNum (((IProduction) structure).getAmountPerTier (tile,MutiBlockHelper.getStructureLevel (tile,structure)))));
				displayInfo.add (I18n.translateToLocal (Local.CURRENT_PROVIDE).replace ("'PRODUCE'",DisplayHelper.formatNum (production.getAmountPerTier (tile,MutiBlockHelper.getStructureLevel (tile,structure) + keyAmount ()))));
				displayInfo.add (I18n.translateToLocal (Local.COST_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStructure (structure,MutiBlockHelper.getStructureLevel (tile,structure),MutiBlockHelper.getStructureLevel (tile,structure) + keyAmount (),0))));
				drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
			}
		if (isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 13,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			IProduction production = (IProduction) structure;
			displayInfo.add (I18n.translateToLocal (Local.CURRENT_PROVIDE).replace ("'PRODUCE'",DisplayHelper.formatNum (((IProduction) structure).getAmountPerTier (tile,MutiBlockHelper.getStructureLevel (tile,structure)))));
			displayInfo.add (I18n.translateToLocal (Local.PREV_LEVEL).replace ("'PRODUCE'",DisplayHelper.formatNum (production.getAmountPerTier (tile,MutiBlockHelper.getStructureLevel (tile,structure) - keyAmount ()))));
			displayInfo.add (I18n.translateToLocal (Local.GIVE_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStructure (structure,MutiBlockHelper.getStructureLevel (tile,structure) - keyAmount (),MutiBlockHelper.getStructureLevel (tile,structure),0))));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}
}
