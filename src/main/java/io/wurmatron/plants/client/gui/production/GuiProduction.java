package io.wurmatron.plants.client.gui.production;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import io.wurmatron.plants.api.PlantsEvolvedAPI;
import io.wurmatron.plants.api.mutiblock.IOutput;
import io.wurmatron.plants.api.mutiblock.IStructure;
import io.wurmatron.plants.api.mutiblock.StorageType;
import io.wurmatron.plants.client.gui.GuiHabitatBase;
import io.wurmatron.plants.client.gui.utils.GuiTexturedButton;
import io.wurmatron.plants.common.network.NetworkHandler;
import io.wurmatron.plants.common.network.server.OutputMessage;
import io.wurmatron.plants.common.reference.Local;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;
import io.wurmatron.plants.common.utils.DisplayHelper;
import io.wurmatron.plants.common.utils.MutiBlockHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GuiProduction extends GuiHabitatBase {

	private List <IOutput> outputs = PlantsEvolvedAPI.getOutputTypes ();

	public GuiProduction (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		for (int index = 0; index < outputs.size (); index++)
			if (index <= 12)
				displayString (outputs.get (index),mouseX,mouseY,62,31 + (16 * index),106,29 + (16 * index),6,29 + (16 * index));
	}

	@Override
	public void initGui () {
		super.initGui ();
		for (int index = 0; index < outputs.size (); index++)
			if (index <= 12) {
				buttonList.add (new GuiTexturedButton (100 + index,startWidth + 106,(startHeight + 29) + (16 * index),12,12,1,"+"));
				buttonList.add (new GuiTexturedButton (101 + index,startWidth + 6,(startHeight + 29) + (16 * index),12,12,1,"-"));
			}
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		for (int index = 0; index < outputs.size (); index++)
			if (index <= 12)
				if ((100 + index) == butt.id && butt.displayString.equalsIgnoreCase ("+"))
					proccessButton (outputs.get (index));
				else if ((101 + index) == butt.id && butt.displayString.equalsIgnoreCase ("-"))
					destroyButton (outputs.get (index));
	}

	private void proccessButton (IOutput output) {
		int currentTier = MutiBlockHelper.getOutputLevel (tile,output);
		int nextTier = currentTier + keyAmount ();
		int valid = 0;
		for (StorageType st : output.getCost ().keySet ())
			if (tile.getColonyValue (MutiBlockHelper.getType (st)) >= (tile.getColonyValue (MutiBlockHelper.getType (st)) * MutiBlockHelper.getOutputLevel (tile,output))) {
				tile.consumeColonyValue (MutiBlockHelper.getType (st),MutiBlockHelper.getOutputLevel (tile,output) * tile.getColonyValue (MutiBlockHelper.getType (st)));
				valid++;
			} else {
				for (StorageType t : output.getCost ().keySet ()) {
					TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.NEED_MINERALS).replaceAll ("'Minerals'",TextFormatting.GOLD + DisplayHelper.formatNum ((MutiBlockHelper.getOutputLevel (tile,output) * tile.getColonyValue (MutiBlockHelper.getType (st))) - tile.getColonyValue (MutiBlockHelper.getType (t))) + TextFormatting.RED));
					text.getStyle ().setColor (TextFormatting.RED);
					mc.ingameGUI.getChatGUI ().printChatMessage (text);
				}
			}
		boolean hasRequiredStructures = MutiBlockHelper.isOutputRunning (output,tile);
		if (hasRequiredStructures && valid >= output.getCost ().keySet ().size ())
			NetworkHandler.sendToServer (new OutputMessage (output,nextTier,tile,false));
		if (!hasRequiredStructures) {
			StringBuilder requiredStructures = new StringBuilder ();
			HashMap <IStructure, Integer> missingStructures = MutiBlockHelper.outputRunningRequirments (output,tile);
			for (IStructure structure : missingStructures.keySet ())
				requiredStructures.append (structure.getDisplayName ()).append (" lvl ").append (missingStructures.get (structure)).append ("; ");
			mc.ingameGUI.getChatGUI ().printChatMessage (new TextComponentString (TextFormatting.RED + I18n.translateToLocal (Local.MISSING_STRUCTURE).replaceAll ("'STRUCTURE'",requiredStructures.toString ())));
		}
	}

	private void destroyButton (IOutput output) {
		int nextTier = keyAmount ();
		if (MutiBlockHelper.getOutputLevel (tile,output) - keyAmount () >= 0) {
			for (StorageType st : output.getCost ().keySet ())
				tile.addColonyValue (NBT.MINERALS,MutiBlockHelper.calculateSellBack (MutiBlockHelper.getOutputLevel (tile,output) * tile.getColonyValue (MutiBlockHelper.getType (st))));
			NetworkHandler.sendToServer (new OutputMessage (output,nextTier,tile,true));
		}
	}
}
