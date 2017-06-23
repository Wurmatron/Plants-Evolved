package wurmatron.spritesofthegalaxy.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.reference.Lineages;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.reference.NBT;

import java.util.List;

public class ItemSpriteColony extends Item {

	public ItemSpriteColony () {
		setCreativeTab (SpritesOfTheGalaxy.tabSprites);
		setUnlocalizedName ("spriteColony");
		setHasSubtypes (true);
		setMaxStackSize (1);
		setMaxDamage (0);
	}

	@Override
	public void onCreated (ItemStack stack,World world,EntityPlayer player) {
		NBTTagCompound nbt = new NBTTagCompound ();
		nbt.setString (NBT.LINEAGE,Lineages.COMMON);
		nbt.setDouble (NBT.POPULATION,Settings.startPopulation);
		stack.setTagCompound (nbt);
	}

	@Override
	public void addInformation (ItemStack stack,EntityPlayer player,List <String> tip,boolean adv) {
		if (stack.getTagCompound () != null) {
			tip.add (TextFormatting.GOLD + I18n.format (Local.POPULATION) + ": " + (int) stack.getTagCompound ().getDouble (NBT.POPULATION));
			tip.add (TextFormatting.GOLD + I18n.format (Local.LINEAGE) + ": " + stack.getTagCompound ().getString (NBT.LINEAGE));
		}
	}

	public ItemStack createColony (String lineage,double population) {
		ItemStack stack = new ItemStack (SpriteItems.spriteColony,1,0);
		NBTTagCompound nbt = new NBTTagCompound ();
		nbt.setDouble (NBT.POPULATION,population);
		nbt.setString (NBT.LINEAGE,lineage);
		stack.setTagCompound (nbt);
		return stack;
	}

	@Override
	public void getSubItems (Item item,CreativeTabs tab,NonNullList <ItemStack> sub) {
		sub.add (createColony (Lineages.COMMON,Settings.startPopulation));
	}
}
