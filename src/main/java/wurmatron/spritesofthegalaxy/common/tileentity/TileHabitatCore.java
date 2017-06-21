package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

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
			handleUpdate ();
			lastUpdate = System.currentTimeMillis ();
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
		return food;
	}

	private double canPopulationGrow () {
		return (getFood () - (getPopulation () * Settings.populationFoodRequirement));
	}

	private int getMaxPopulation () {
		return maxPopulation;
	}

	public void growPopulation () {
		if (canPopulationGrow () > 0 && getPopulation () < getMaxPopulation ())
			setPopulation (getPopulation () * Settings.populationGrowth);
	}

	public void handleUpdate () {
		growPopulation ();
	}
}
