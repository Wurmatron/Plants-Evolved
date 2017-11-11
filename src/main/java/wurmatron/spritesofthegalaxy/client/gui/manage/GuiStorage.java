package wurmatron.spritesofthegalaxy.client.gui.manage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.StorageTypeMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiStorage extends GuiHabitatBase {

	public GuiStorage (TileHabitatCore2 tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		for (int index = 0; index < StorageType.values ().length; index++)
			if (index <= 10)
				displayString (StorageType.values ()[index],mouseX,mouseY,62,31 + (16 * index),4,29 + (16 * index),106,29 + (16 * index));
	}

	@Override
	public void initGui () {
		super.initGui ();
		for (int index = 0; index < StorageType.values ().length; index++)
			if (index <= 10) {
				buttonList.add (new GuiTexturedButton (100 + index,startWidth + 106,(startHeight + 29) + (16 * index),12,12,1,"+"));
				buttonList.add (new GuiTexturedButton (101 + index,startWidth + 4,(startHeight + 29) + (16 * index),12,12,1,"-"));
			}
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		for (int index = 0; index < StorageType.values ().length; index++)
			if (index <= 10)
				if ((100 + index) == butt.id)
					proccessButton (StorageType.values ()[index]);
				else if ((101 + index) == butt.id)
					destroyButton (StorageType.values ()[index]);
	}

	private void proccessButton (StorageType type) {
		int currentTier = MutiBlockHelper.getStorageLevel (tile,type);
		int nextTier = currentTier + keyAmount ();
		if (MutiBlockHelper.canBuildStorageType (tile,type,currentTier,nextTier)) {
			tile.consumeColonyValue (NBT.MINERALS,MutiBlockHelper.calcMineralsForStorage (type,MutiBlockHelper.getStorageLevel (tile,type),nextTier,0));
			NetworkHandler.sendToServer (new StorageTypeMessage (type,nextTier,tile,false));
		} else {
			TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.NEED_MINERALS).replaceAll ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStorage (type,currentTier,nextTier,0) - tile.getColonyValue (NBT.MINERALS))));
			text.getStyle ().setColor (TextFormatting.RED);
			mc.ingameGUI.getChatGUI ().printChatMessage (text);
		}
	}

	private void destroyButton (StorageType type) {
		int nextTier = keyAmount ();
		if (MutiBlockHelper.getStorageLevel (tile,type) - keyAmount () >= 0) {
			tile.addColonyValue (NBT.MINERALS,MutiBlockHelper.calcMineralsForStorage (type,nextTier,MutiBlockHelper.getStorageLevel (tile,type),0));
			NetworkHandler.sendToServer (new StorageTypeMessage (type,nextTier,tile,true));
		}
	}

	private void displayString (StorageType type,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal (type.getDisplayKey ()) + " lvl " + DisplayHelper.formatNum (MutiBlockHelper.getStorageLevel (tile,type));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 12,startHeight + buttY + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add (I18n.translateToLocal (Local.COST_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calculateSellBack (MutiBlockHelper.calcMineralsForStorage (type,MutiBlockHelper.getStorageLevel (tile,type),MutiBlockHelper.getStorageLevel (tile,type) + keyAmount (),0)))));
			drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
		}
		if (isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 12,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add (I18n.translateToLocal (Local.GIVE_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calculateSellBack (MutiBlockHelper.calcMineralsForStorage (type,MutiBlockHelper.getStorageLevel (tile,type) - keyAmount (),MutiBlockHelper.getStorageLevel (tile,type),0)))));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}
}
