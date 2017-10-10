package wurmatron.spritesofthegalaxy.client.gui.production;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IOutput;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OutputMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiProduction extends GuiHabitatBase {

	private List <IOutput> outputs = SpritesOfTheGalaxyAPI.getOutputTypes ();

	public GuiProduction (TileHabitatCore2 tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		for (int index = 0; index < outputs.size (); index++)
			if (index <= 10)
				displayString (outputs.get (index),mouseX,mouseY,62,31 + (16 * index),4,29 + (16 * index),106,29 + (16 * index));
	}

	@Override
	public void initGui () {
		super.initGui ();
		for (int index = 0; index < outputs.size (); index++)
			if (index <= 10) {
				buttonList.add (new GuiButton (100 + index,startWidth + 4,(startHeight + 29) + (16 * index),12,12,"+"));
				buttonList.add (new GuiButton (101 + index,startWidth + 106,(startHeight + 29) + (16 * index),12,12,"-"));
			}
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		for (int index = 0; index < outputs.size (); index++)
			if (index <= 10)
				if ((100 + index) == butt.id)
					proccessButton (outputs.get (index));
				else if ((101 + index) == butt.id)
					destroyButton (outputs.get (index));
	}

	private void proccessButton (IOutput output) {
		int currentTier = MutiBlockHelper.getOutputLevel (tile,output);
		int nextTier = currentTier + keyAmount ();
		for (StorageType st : output.getCost ().keySet ())
			if (tile.getColonyValue (MutiBlockHelper.getType (st)) >= (tile.getColonyValue (MutiBlockHelper.getType (st)) * MutiBlockHelper.getOutputLevel (tile,output))) {
				for (StorageType t : output.getCost ().keySet ())
					tile.consumeColonyValue (MutiBlockHelper.getType (st),MutiBlockHelper.getOutputLevel (tile,output) * tile.getColonyValue (MutiBlockHelper.getType (t)));
				NetworkHandler.sendToServer (new OutputMessage (output,nextTier,tile,false));
			} else {
				for (StorageType t : output.getCost ().keySet ()) {
					TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.NEED_MINERALS).replaceAll ("'Minerals'",TextFormatting.GOLD + DisplayHelper.formatNum ((MutiBlockHelper.getOutputLevel (tile,output) * tile.getColonyValue (MutiBlockHelper.getType (st))) - tile.getColonyValue (MutiBlockHelper.getType (t))) + TextFormatting.RED));
					text.getStyle ().setColor (TextFormatting.RED);
					mc.ingameGUI.getChatGUI ().printChatMessage (text);
				}
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

	private void displayString (IOutput output,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal (output.getName ()) + " lvl " + DisplayHelper.formatNum (MutiBlockHelper.getOutputLevel (tile,output));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 13,startHeight + buttY + 12)) {
			List <String> displayInfo = new ArrayList ();
			for (StorageType st : output.getCost ().keySet ())
				displayInfo.add (I18n.translateToLocal (Local.COST_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (((MutiBlockHelper.getOutputLevel (tile,output) + keyAmount ()) * tile.getColonyValue (MutiBlockHelper.getType (st))))));
			drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
		}
		if (isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 13,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			for (StorageType st : output.getCost ().keySet ())
				displayInfo.add (I18n.translateToLocal (Local.GIVE_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum ((MutiBlockHelper.getOutputLevel (tile,output) * tile.getColonyValue (MutiBlockHelper.getType (st))))));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}
}
