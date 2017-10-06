package wurmatron.spritesofthegalaxy.common.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IOutput;
import wurmatron.spritesofthegalaxy.common.network.CustomMessage;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.io.IOException;

public class OutputMessage extends CustomMessage.CustomtServerMessage <OutputMessage> {

	private NBTTagCompound data;

	public OutputMessage () {
	}

	public OutputMessage (IOutput type,int level,TileHabitatCore core,boolean remove) {
		data = new NBTTagCompound ();
		data.setString (NBT.OUTPUT,type.getName ());
		data.setInteger (NBT.LEVEL,level);
		data.setIntArray (NBT.POSITION,new int[] {core.getPos ().getX (),core.getPos ().getY (),core.getPos ().getZ ()});
		data.setBoolean (NBT.TYPE,remove);
	}

	public OutputMessage (IOutput type,int level,BlockPos coreLoc,boolean remove) {
		data = new NBTTagCompound ();
		data.setString (NBT.OUTPUT,type.getName ());
		data.setInteger (NBT.LEVEL,level);
		data.setIntArray (NBT.POSITION,new int[] {coreLoc.getX (),coreLoc.getY (),coreLoc.getZ ()});
		data.setBoolean (NBT.TYPE,remove);
	}

	@Override
	protected void read (PacketBuffer buff) throws IOException {
		data = buff.readCompoundTag ();
	}

	@Override
	protected void write (PacketBuffer buff) throws IOException {
		buff.writeCompoundTag (data);
	}

	@Override
	public void process (EntityPlayer player,Side side) {
		int[] coreLoc = data.getIntArray (NBT.POSITION);
		IOutput type = SpritesOfTheGalaxyAPI.getOutputFromName (data.getString (NBT.OUTPUT));
		int tier = data.getInteger (NBT.LEVEL);
		boolean remove = data.getBoolean (NBT.TYPE);
		BlockPos coreLocation = new BlockPos (coreLoc[0],coreLoc[1],coreLoc[2]);
		if (player.world.getTileEntity (coreLocation) != null && player.world.getTileEntity (coreLocation) instanceof TileHabitatCore) {
			TileHabitatCore core = (TileHabitatCore) player.world.getTileEntity (coreLocation);
			if (core != null) {
				if (remove) {
					core.addMinerals (type.getCost () * tier);
					core.removeOutputSetting (type);
				} else {
					core.consumeMinerals (type.getCost () * tier);
					core.addOutputSetting (type,tier);
				}
			}
		}
	}
}
