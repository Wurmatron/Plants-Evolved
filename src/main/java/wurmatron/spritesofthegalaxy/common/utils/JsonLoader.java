package wurmatron.spritesofthegalaxy.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IOutput;
import wurmatron.spritesofthegalaxy.common.config.ConfigHandler;
import wurmatron.spritesofthegalaxy.common.tileentity.output.OutputJson;

import java.io.*;
import java.util.ArrayList;

public class JsonLoader {

	public static final File dir = ConfigHandler.DIRECTORY;
	private static final Gson gson = new GsonBuilder ().setPrettyPrinting ().create ();

	public static IOutput convertJsonToOutput (String json) {
		if (json != null && json.length () > 0)
			return gson.fromJson (json,OutputJson.class);
		return null;
	}

	public static IOutput loadOutputFromFile (File location) {
		ArrayList <String> lines = new ArrayList <> ();
		if (location.exists ()) {
			try {
				BufferedReader reader = new BufferedReader (new FileReader (location));
				String line;
				while ((line = reader.readLine ()) != null)
					lines.add (line);
				reader.close ();
			} catch (FileNotFoundException e) {
				e.printStackTrace ();
			} catch (IOException e) {
				e.printStackTrace ();
			}
		}
		String temp = "";
		for (int s = 0; s <= lines.size () - 1; s++)
			temp = temp + lines.get (s);
		return convertJsonToOutput (temp);
	}


	public static void loadJsonOutputs () {
		try {
			if (new File (JsonLoader.dir + File.separator + "Items" + File.separator).listFiles ().length > 0) {
				for (File json : new File (JsonLoader.dir + File.separator + "Items" + File.separator).listFiles ()) {
					if (json != null && json.isFile ()) {
						IOutput temp = JsonLoader.loadOutputFromFile (json);
						if (!SpritesOfTheGalaxyAPI.getOutputTypes ().contains (temp)) {
							SpritesOfTheGalaxyAPI.register (temp);
						}
					}
				}
			} else
				LogHandler.info ("Unable to load Item Output");
		} catch (NullPointerException e) {
			LogHandler.debug (e.getLocalizedMessage ());
		}
	}
}
