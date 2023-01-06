package io.wurmatron.plants.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import io.wurmatron.plants.api.PlantsEvolvedAPI;
import io.wurmatron.plants.api.mutiblock.IOutput;
import io.wurmatron.plants.common.config.ConfigHandler;
import io.wurmatron.plants.common.utils.output.OutputJson;

import java.io.*;
import java.util.ArrayList;

public class JsonLoader {

	public static final File dir = ConfigHandler.DIRECTORY;
	public static final File location = new File (JsonLoader.dir + File.separator + "Items" + File.separator);
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
		StringBuilder temp = new StringBuilder ();
		for (int s = 0; s <= lines.size () - 1; s++)
			temp.append (lines.get (s));
		return convertJsonToOutput (temp.toString ());
	}

	public static void loadJsonOutputs () {
		if (!location.exists ())
			location.mkdirs ();
		try {
			if (location.isDirectory () && location.listFiles ().length > 0) {
				for (File json : location.listFiles ())
					if (json != null && json.isFile ()) {
						IOutput temp = JsonLoader.loadOutputFromFile (json);
						if (!PlantsEvolvedAPI.getOutputTypes ().contains (temp))
							PlantsEvolvedAPI.register (temp);
					}
			} else
				LogHandler.info ("Unable to load Item Output");
		} catch (NullPointerException e) {
			LogHandler.debug (e.getLocalizedMessage ());
		}
	}

	public static void save(OutputJson output) {
		File file = new File (location + File.separator + output.getName () + ".json");
		if (!location.exists ())
			location.mkdirs ();
		if (!file.exists ()) {
			try {
				file.createNewFile ();
				FileUtils.writeStringToFile (file,gson.toJson (output));
			} catch (IOException e) {
				LogHandler.info ("Error saving, " + output.getName ());
			}
		}
	}
}
