package wurmatron.spritesofthegalaxy.client.gui.manage;

import net.minecraft.client.Minecraft;
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
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.io.IOException;

public class GuiStorage extends GuiHabitatBase {

	public GuiStorage (TileHabitatCore tile) {
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
				buttonList.add (new GuiTexturedButton (100 + index,startWidth + 4,(startHeight + 29) + (16 * index),12,12,1,"-"));
				buttonList.add (new GuiTexturedButton (101 + index,startWidth + 106,(startHeight + 29) + (16 * index),12,12,1,"+"));
			}
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		for (int index = 0; index < StorageType.values ().length; index++)
			if (index <= 10)
				if ((101 + index) == butt.id && butt.displayString.equalsIgnoreCase ("+"))
					proccessButton (StorageType.values ()[index]);
				else if ((100 + index) == butt.id && butt.displayString.equalsIgnoreCase ("-"))
					destroyButton (StorageType.values ()[index]);
	}

	private void proccessButton (StorageType type) {
		int currentTier = MutiBlockHelper.getStorageLevel (tile,type);
		int nextTier = currentTier + keyAmount ();
		if (MutiBlockHelper.canBuildStorageType (tile,type,currentTier,nextTier)) {
			tile.consumeColonyValue (NBT.MINERALS,MutiBlockHelper.calcMineralsForStorage (type,MutiBlockHelper.getStorageLevel (tile,type),nextTier,0));
			NetworkHandler.sendToServer (new StorageTypeMessage (type,nextTier,tile,false));
			Minecraft.getMinecraft ().player.sendStatusMessage (new TextComponentString (TextFormatting.GOLD + I18n.translateToLocal (Local.SEND_TO_BUILDQUEUE).replaceAll ("'STRUCTURE'",I18n.translateToLocal (type.getDisplayKey ()))),false);
		} else {
			TextComponentString text = new TextComponentString (I18n.translateToLocal (Local.NEED_MINERALS).replaceAll ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStorage (type,currentTier,nextTier,0))));
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
}
