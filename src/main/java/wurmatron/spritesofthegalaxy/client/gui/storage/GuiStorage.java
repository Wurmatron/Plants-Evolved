package wurmatron.spritesofthegalaxy.client.gui.storage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Keyboard;
import sun.rmi.runtime.Log;
import wurmatron.spritesofthegalaxy.api.mutiblock.IProduction;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.BuildStorageTypeMessage;
import wurmatron.spritesofthegalaxy.common.network.server.BuildStructureMessage;
import wurmatron.spritesofthegalaxy.common.network.server.DestroyStorageTypeMessage;
import wurmatron.spritesofthegalaxy.common.network.server.DestroyStructureMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.structure.StructureHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiStorage extends GuiHabitatBase {

	protected GuiButton popAdd;
	protected GuiButton popSub;

	public GuiStorage (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		super.drawScreen (mouseX,mouseY,partialTicks);
		displayString (StorageType.POPULATION,mouseX,mouseY,62,31,4,29,106,29);
	}

	@Override
	public void initGui () {
		super.initGui ();
		buttonList.add (popAdd = new GuiButton (100,startWidth + 4,startHeight + 29,12,12,"+"));
		buttonList.add (popSub = new GuiButton (101,startWidth + 106,startHeight + 29,12,12,"-"));
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		super.actionPerformed (button);
		switch (button.id) {
			case (100): {
				proccessButton (StorageType.POPULATION);
				break;
			}
			case (101): {
				destroyButton (StorageType.POPULATION);
				break;
			}
		}
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

	private void proccessButton (StorageType type) {
		int currentTier = getStorageLevel (tile,type);
		int nextTier = currentTier + keyAmount ();
		if (MutiBlockHelper.canBuildStorageType (tile,type,currentTier,nextTier)) {
			tile.eatMinerals (MutiBlockHelper.calcMineralsForStorage (type,getStorageLevel (tile,type),nextTier,0));
			NetworkHandler.sendToServer (new BuildStorageTypeMessage (type,nextTier,tile.getPos ()));
		} else
			mc.ingameGUI.getChatGUI ().printChatMessage (new TextComponentString (I18n.translateToLocal (Local.NEED_MINERALS).replaceAll ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStorage (type,currentTier,nextTier,0) - tile.getMinerals ()))));
	}

	private void destroyButton (StorageType type) {
		int nextTier = keyAmount ();
		if (getStorageLevel (tile,type) - keyAmount () >= 0) {
			tile.addMinerals (MutiBlockHelper.calcMineralsForStorage (type,nextTier,getStorageLevel (tile,type),0));
			NetworkHandler.sendToServer (new DestroyStorageTypeMessage (type,nextTier,tile.getPos ()));
		}
	}

	private boolean isWithin (int mouseX,int mouseY,int x,int y,int x2,int y2) {
		return mouseX >= x && mouseX < x2 && mouseY >= y && mouseY < y2;
	}

	private void displayString (StorageType type,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal (type.getDisplayKey ()) + " lvl " + DisplayHelper.formatNum (getStorageLevel (tile,type));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 12,startHeight + buttY + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add ("Minerals:   -" + DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStorage (type,getStorageLevel (tile,type),getStorageLevel (tile,type)+keyAmount (),0)));
			drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
		}
		if (isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 12,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add ("Minerals:   +" + DisplayHelper.formatNum (MutiBlockHelper.calcMineralsForStorage (type,getStorageLevel (tile,type)-keyAmount (),getStorageLevel (tile,type),0)));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}

	private int getStorageLevel (TileHabitatCore tile,StorageType type) {
		if (type != null && tile != null) {
			HashMap <StorageType, Integer> currentStorage = tile.getStorageData ();
			if (currentStorage != null && currentStorage.size () > 0 && currentStorage.containsKey (type))
				return currentStorage.get (type);
		}
		return 0;
	}
}
