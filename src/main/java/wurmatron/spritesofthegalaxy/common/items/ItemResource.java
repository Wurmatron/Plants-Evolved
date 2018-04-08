package wurmatron.spritesofthegalaxy.common.items;


import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.common.reference.Local;

import javax.annotation.Nullable;
import java.util.List;

public class ItemResource extends Item {

	public static final String[] minerals = new String[] {"Mineral","Gem","Magic","Compound", "RichCompound"};

	public ItemResource () {
		setCreativeTab (SpritesOfTheGalaxy.tabSprites);
		setUnlocalizedName ("itemMineral");
		setHasSubtypes (true);
	}

	@Override
	public void getSubItems (CreativeTabs tab,NonNullList <ItemStack> items) {
		if (tab == SpritesOfTheGalaxy.tabSprites)
			for (int index = 0; index < minerals.length; index++)
				items.add (new ItemStack (this,1,index));
	}

	@Override
	public String getItemStackDisplayName (ItemStack stack) {
		return I18n.translateToLocal ("item." + minerals[stack.getItemDamage () < minerals.length ? stack.getItemDamage () : 0] + ".name");
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World world,List <String> tip,ITooltipFlag flag) {
		if (stack.getItemDamage () == 3)
			tip.add (I18n.translateToLocal (Local.TOOLTIP_WORTH).replaceAll ("%WORTH%",100 + ""));
		else if(stack.getItemDamage () == 4)
			tip.add (I18n.translateToLocal (Local.TOOLTIP_WORTH).replaceAll ("%WORTH%",1000 + ""));
		else
			tip.add (I18n.translateToLocal (Local.TOOLTIP_WORTH).replaceAll ("%WORTH%",10 + ""));
	}
}
