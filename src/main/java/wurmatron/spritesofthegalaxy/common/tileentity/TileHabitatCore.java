package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import javax.annotation.Nullable;

public class TileHabitatCore extends TileMutiBlock implements ITickable {

	private static final long UPDATE_TIME = 1000;

	private boolean update = false;
	private int maxPopulation = Settings.maxPopulation;
	private int food = Settings.populationFoodRequirement * Settings.maxPopulation;
	private long lastUpdate;
	// TEMP
	private double population = 1;

	@Override
	public void update () {
		if (!update && world.getWorldTime () % 20 == 0) {
			int isValid = MutiBlockHelper.isValid (world,pos);
			if (isValid > 0) {
				MutiBlockHelper.setTilesCore (world,pos,isValid);
				update = true;
			}
		}
		// TODO Add Support for over 1 update
		if (lastUpdate + UPDATE_TIME <= System.currentTimeMillis ()) {
			LogHandler.serverInfo ("Population: " + getPopulation () + " / " + getMaxPopulation ());
			LogHandler.serverInfo ("Food: " + getFood ());
			handleUpdate ();
			lastUpdate = System.currentTimeMillis ();
			world.markAndNotifyBlock (pos,world.getChunkFromBlockCoords (pos),world.getBlockState (pos), world.getBlockState (pos),3);
		}
	}

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		update = nbt.getBoolean (NBT.UPDATE);
		maxPopulation = nbt.getInteger (NBT.MAX_POPULATION);
		food = nbt.getInteger (NBT.FOOD);
		lastUpdate = nbt.getLong (NBT.LASTUPDATE);
		// Temp
		population = nbt.getDouble ("population");
		markDirty ();
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		nbt.setBoolean (NBT.UPDATE,update);
		nbt.setInteger (NBT.MAX_POPULATION,maxPopulation);
		nbt.setInteger (NBT.FOOD,food);
		nbt.setLong (NBT.LASTUPDATE,lastUpdate);
		// Temp
		nbt.setDouble ("population",population);
		super.writeToNBT (nbt);
		return nbt;
	}

	public void requestUpdate () {
		this.update = false;
	}

	public double getPopulation () {
		return population;
	}

	public void setPopulation (double pop) {
		this.population = pop;
	}

	public int getFood () {
		return (int) (food - (Settings.populationFoodRequirement * getPopulation ()));
	}

	private double canPopulationGrow () {
		return (food - (getPopulation () * Settings.populationFoodRequirement));
	}

	public int getMaxPopulation () {
		return maxPopulation;
	}

	public void growPopulation () {
		if (canPopulationGrow () > 0 && getPopulation () < getMaxPopulation ())
			setPopulation (getPopulation () * Settings.populationGrowth);
		else if (canPopulationGrow () < 0 && getFood () < 0)
			setPopulation (getPopulation () - Math.abs (getFood ()));
	}

	public void handleUpdate () {
		growPopulation ();
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT (tag);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		population = packet.getNbtCompound ().getDouble ("population");
		readFromNBT (packet.getNbtCompound());
	}
}
