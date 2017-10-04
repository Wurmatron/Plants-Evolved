package wurmatron.spritesofthegalaxy.common.utils;

import net.minecraft.item.ItemStack;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.reference.NBT;

import java.util.HashMap;

public class DisplayHelper {

	public static String formatNum (double count) {
		if (count < 1000)
			return "" + (int) count;
		int exp = (int) (Math.log ((int) count) / Math.log (1000));
		String format = String.format ("%.1f %c",(count / Math.pow (1000,exp)),"kMGT".charAt (exp - 1));
		return format.replaceAll (".0","");
	}

	public static String formatLineage (ItemStack colony) {
		if (colony != null && colony.getTagCompound ().hasKey (NBT.LINEAGE)) {
			String lineage = colony.getTagCompound ().getString (NBT.LINEAGE);
			return lineage.substring (0,1).toUpperCase () + lineage.substring (1);
		}
		return "none";
	}

	public static String formatNeededResearch (HashMap <IResearch, Integer> needed) {
		String temp = "";
		for (IResearch res : needed.keySet ())
			temp = temp + res.getName () + ": " + needed.get (res) + ", ";
		if (temp.endsWith (", ") && temp.length () > 2)
			return temp.substring (0,temp.length () - 2);
		return temp;
	}
}
