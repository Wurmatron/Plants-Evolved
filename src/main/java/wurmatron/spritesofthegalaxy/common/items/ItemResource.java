package wurmatron.spritesofthegalaxy.common.items;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;

public class ItemResource extends Item {

	public static final String[] minerals = new String[] {"Mineral","Gem","Magic"};

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
}
