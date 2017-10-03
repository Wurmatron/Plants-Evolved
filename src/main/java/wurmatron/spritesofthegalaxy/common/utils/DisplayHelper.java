package wurmatron.spritesofthegalaxy.common.utils;

import net.minecraft.item.ItemStack;
import wurmatron.spritesofthegalaxy.common.reference.NBT;

public class DisplayHelper {

	public static String formatNum (double num) {
		String r = Integer.toString ((int) Math.round (num));
		r = r.replaceAll ("E[0-9]",new String[] {"","k","m","b","t"}[Character.getNumericValue (r.charAt (r.length () - 1)) / 3]);
		while (r.length () > 4 || r.matches ("[0-9]+\\.[a-z]"))
			r = r.substring (0,r.length () - 2) + r.substring (r.length () - 1);
		return r;
	}

	public static String formatLineage (ItemStack colony) {
		if (colony != null && colony.getTagCompound ().hasKey (NBT.LINEAGE)) {
			String lineage = colony.getTagCompound ().getString (NBT.LINEAGE);
			return lineage.substring (0,1).toUpperCase () + lineage.substring (1);
		}
		return "none";
	}
}
