package io.wurmatron.plants.client.gui.manage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import io.wurmatron.plants.api.PlantsEvolvedAPI;
import io.wurmatron.plants.api.mutiblock.IProduction;
import io.wurmatron.plants.api.mutiblock.IStructure;
import io.wurmatron.plants.api.mutiblock.StructureType;
import io.wurmatron.plants.client.gui.GuiHabitatBase;
import io.wurmatron.plants.client.gui.utils.GuiTexturedButton;
import io.wurmatron.plants.common.config.Settings;
import io.wurmatron.plants.common.network.NetworkHandler;
import io.wurmatron.plants.common.network.server.StructureMessage;
import io.wurmatron.plants.common.reference.Local;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.research.ResearchHelper;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;
import io.wurmatron.plants.common.utils.DisplayHelper;
import io.wurmatron.plants.common.utils.MutiBlockHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiStructure extends GuiHabitatBase {

	private List <IStructure> structures;

	public GuiStructure (TileHabitatCore tile,StructureType structureType) {
		super (tile);
		structures = PlantsEvolvedAPI.getStructuresForType (structureType);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		for (int index = 0; index < structures.size (); index++)
			if (index <= 10)
				displayString (structures.get (index),mouseX,mouseY,62,31 + (16 * index),106,29 + (16 * index),6,29 + (16 * index));
	}

	@Override
	public void initGui () {
		super.initGui ();
		for (int index = 0; index < structures.size (); index++) {
			GuiTexturedButton addButt = new GuiTexturedButton (100 + index,startWidth + 108,(startHeight + 29) + (16 * index),12,12,1,"+");
			GuiTexturedButton negButt = new GuiTexturedButton (101 + index,startWidth + 6,(startHeight + 29) + (16 * index),12,12,1,"-");
			if (index <= 10) {
				buttonList.add (addButt);
				buttonList.add (negButt);
			}
			if (tile.getBuildQueue ().size () == 0) {
				negButt.enabled = true;
				addButt.enabled = true;
			}
			if (MutiBlockHelper.getStructureLevel (tile,structures.get (index)) <= 0)
				negButt.enabled = false;
		}
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		for (int index = 0; index < structures.size (); index++)
			if (index <= 10)
				if ((100 + index) == butt.id && "+".equals (butt.displayString))
					proccessButton (structures.get (index));
				else if ((101 + index) == butt.id && "-".equals (butt.displayString))
					destroyButton (structures.get (index));
	}

	private void proccessButton (IStructure structure) {
		int currentTier = MutiBlockHelper.getStructureLevel (tile,structure);
		int nextTier = currentTier + keyAmount ();
		if (tile.getColonyValue (NBT.BUILD_QUEUE) > tile.getBuildQueue ().size () && MutiBlockHelper.canBuildStructure (tile,structure,currentTier,nextTier)) {
			tile.consumeColonyValue (NBT.MINERALS,MutiBlockHelper.calcMineralsForStructure (structure,MutiBlockHelper.getStructureLevel (tile,structure),nextTier,0));
			if (tile.getColonyValue (NBT.ENERGY) < (tile.getPowerUsage () + structure.getEnergyUsage (nextTier)))
				Minecraft.getMinecraft ().player.sendStatusMessage (new TextComponentString (TextFormatting.RED + I18n.translateToLocal (Local.ENERGY_OVERLOAD).replaceAll ("%STRUCTURE%",structure.getDisplayName ())),false);
			else {
				if (keyAmount () > 0) {
					if (tile.getBuildQueue ().size () + keyAmount () <= tile.getColonyValue (NBT.BUILD_QUEUE)) {
						NetworkHandler.sendToServer (new StructureMessage (structure,nextTier,tile,false));
						Minecraft.getMinecraft ().player.sendStatusMessage (new TextComponentString (TextFormatting.GOLD + I18n.translateToLocal (Local.SEND_TO_BUILDQUEUE).replaceAll ("'STRUCTURE'",structure.getDisplayName ())),false);
					} else {
						TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.BUILDQUEUE_FULL));
						text.getStyle ().setColor (TextFormatting.RED);
						mc.ingameGUI.getChatGUI ().printChatMessage (text);
					}
				} else {
					NetworkHandler.sendToServer (new StructureMessage (structure,nextTier,tile,false));
					Minecraft.getMinecraft ().player.sendStatusMessage (new TextComponentString (TextFormatting.GOLD + I18n.translateToLocal (Local.SEND_TO_BUILDQUEUE).replaceAll ("'STRUCTURE'",structure.getDisplayName ())),false);
				}
			}
		} else if (!MutiBlockHelper.hasRequiredResearch (tile,structure)) {
			TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.MISSING_RESEARCH).replaceAll ("'Research'",TextFormatting.GOLD + DisplayHelper.formatNeededResearch (ResearchHelper.getNeededResearch (tile.getResearch (),structure))));
			text.getStyle ().setColor (TextFormatting.RED);
			mc.ingameGUI.getChatGUI ().printChatMessage (text);
		} else if (tile.getColonyValue (NBT.BUILD_QUEUE) <= tile.getBuildQueue ().size ()) {
			TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.BUILDQUEUE_FULL));
			text.getStyle ().setColor (TextFormatting.RED);
			mc.ingameGUI.getChatGUI ().printChatMessage (text);
		} else {
			TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.NEED_MINERALS).replaceAll ("'Minerals'",TextFormatting.GOLD + DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStructure (structure,currentTier,nextTier,0) - tile.getColonyValue (NBT.MINERALS)) + TextFormatting.RED));
			text.getStyle ().setColor (TextFormatting.RED);
			mc.ingameGUI.getChatGUI ().printChatMessage (text);
		}
	}

	private void destroyButton (IStructure structure) {
		int nextTier = keyAmount ();
		if (!Settings.defaultStructures.containsKey (structure) || Settings.defaultStructures.containsKey (structure) && Settings.defaultStructures.get (structure) > 0) {
			if (MutiBlockHelper.getStructureLevel (tile,structure) - nextTier >= 0 && MutiBlockHelper.getMinimumLevel (structure) <= MutiBlockHelper.getStructureLevel (tile,structure) - keyAmount ()) {
				NetworkHandler.sendToServer (new StructureMessage (structure,MutiBlockHelper.getStructureLevel (tile,structure) - nextTier,tile,true));
			} else if (MutiBlockHelper.getMinimumLevel (structure) > MutiBlockHelper.getStructureLevel (tile,structure) - keyAmount ()) {
				TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.MIN_LEVEL_REQ));
				text.getStyle ().setColor (TextFormatting.RED);
				mc.ingameGUI.getChatGUI ().printChatMessage (text);
			}
		} else {
			TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.MIN_LEVEL_REQ));
			text.getStyle ().setColor (TextFormatting.RED);
			mc.ingameGUI.getChatGUI ().printChatMessage (text);
		}
	}

	private void displayString (IStructure structure,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal (structure.getDisplayName ()) + " lvl " + DisplayHelper.formatNum (MutiBlockHelper.getStructureLevel (tile,structure));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (structure instanceof IProduction && isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 13,startHeight + buttY + 12)) {
			List <String> displayInfo = new ArrayList ();
			IProduction production = (IProduction) structure;
			displayInfo.add (I18n.translateToLocal (Local.CURRENT_PROVIDE).replace ("'PRODUCE'",DisplayHelper.formatNum (((IProduction) structure).getAmountPerTier (tile,MutiBlockHelper.getStructureLevel (tile,structure)))));
			displayInfo.add (I18n.translateToLocal (Local.NEXT_LEVEL).replace ("'PRODUCE'",DisplayHelper.formatNum (production.getAmountPerTier (tile,MutiBlockHelper.getStructureLevel (tile,structure) + keyAmount ()))));
			int cost = MutiBlockHelper.calcMineralsForStructure (structure,MutiBlockHelper.getStructureLevel (tile,structure),MutiBlockHelper.getStructureLevel (tile,structure) + keyAmount (),0);
			displayInfo.add (I18n.translateToLocal (Local.COST_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStructure (structure,MutiBlockHelper.getStructureLevel (tile,structure),MutiBlockHelper.getStructureLevel (tile,structure) + keyAmount (),0))));
			displayInfo.add (I18n.translateToLocal (Local.POPULATION_COST).replace ("'POPULATION'",DisplayHelper.formatNum (MutiBlockHelper.getRequiredPopulation (structure,MutiBlockHelper.getStructureLevel (tile,structure)))));
			displayInfo.add (I18n.translateToLocal (Local.ENERGY_COST).replace ("'ENERGY'","" + structure.getEnergyUsage (MutiBlockHelper.getStructureLevel (tile,structure))));
			drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
		}
		if (structure instanceof IProduction && isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 13,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			IProduction production = (IProduction) structure;
			displayInfo.add (I18n.translateToLocal (Local.CURRENT_PROVIDE).replace ("'PRODUCE'",DisplayHelper.formatNum (((IProduction) structure).getAmountPerTier (tile,MutiBlockHelper.getStructureLevel (tile,structure)))));
			displayInfo.add (I18n.translateToLocal (Local.PREV_LEVEL).replace ("'PRODUCE'",DisplayHelper.formatNum (production.getAmountPerTier (tile,MutiBlockHelper.getStructureLevel (tile,structure) - keyAmount ()))));
			displayInfo.add (I18n.translateToLocal (Local.GIVE_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStructure (structure,MutiBlockHelper.getStructureLevel (tile,structure) - keyAmount (),MutiBlockHelper.getStructureLevel (tile,structure),0))));
			displayInfo.add (I18n.translateToLocal (Local.POPULATION_COST).replace ("'POPULATION'",DisplayHelper.formatNum (MutiBlockHelper.getRequiredPopulation (structure,MutiBlockHelper.getStructureLevel (tile,structure)))));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}
}
