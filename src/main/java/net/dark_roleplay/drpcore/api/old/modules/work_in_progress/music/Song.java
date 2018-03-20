package net.dark_roleplay.drpcore.api.old.modules.work_in_progress.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Song {

	protected Map<String,Note[][]> notes = new HashMap<String, Note[][]>();
	
    private static final Map<String, SoundEvent> INSTRUMENTS = new HashMap<String, SoundEvent>(){{
    	put("HARP", SoundEvents.BLOCK_NOTE_HARP);
    	put("BASEDRUM", SoundEvents.BLOCK_NOTE_BASEDRUM);
    	put("SNARE", SoundEvents.BLOCK_NOTE_SNARE);
    	put("HAT", SoundEvents.BLOCK_NOTE_HAT);
    	put("BASS", SoundEvents.BLOCK_NOTE_BASS);
    	put("FLUTE", SoundEvents.BLOCK_NOTE_FLUTE);
    	put("BELL", SoundEvents.BLOCK_NOTE_BELL);
    	put("GUITAR", SoundEvents.BLOCK_NOTE_GUITAR);
    	put("CHIME", SoundEvents.BLOCK_NOTE_CHIME);
    	put("XYLOPHONE", SoundEvents.BLOCK_NOTE_XYLOPHONE);
    }};
	
	public static String musicString = ""
			+ "{"
				+ "\"instruments\":["
					+ "\"HARP\","
					+ "\"FLUTE\""
				+ "],"
				+ "\"notes\":["
					+ "[0,15],"
					+ "[0,11],"
					+ "[0,6],"
					+ "[0,8],"
					+ "[0,11],"
					+ "[0,11],"
					+ "[0,8],"
					+ "[0,6],"
					+ "[0,11],"
					+ "[0,11],"
					+ "[0,18],"
					+ "[0,15],"
					+ "[0,13],"
					+ "[0,6],"
					+ "[0,15],"
					+ "[0,11],"
					+ "[0,6],"
					+ "[0,8],"
					+ "[0,11],"
					+ "[0,11],"
					+ "[0,8],"
					+ "[0,6],"
					+ "[0,11],"
					+ "[0,15],"
					+ "[0,13],"
					+ "[0,11]"
				+ "]"
			+ "}";
	
	public Song(String json){
		JsonObject jo = ((JsonObject)new JsonParser().parse(json));
		JsonArray jaInstruments = jo.get("instruments").getAsJsonArray();
		String[] instruments = new String[jaInstruments.size()];
		
		for(int i = 0; i < jaInstruments.size(); i++){
			instruments[i] = jaInstruments.get(i).getAsString();
		}
		
		JsonArray jaNotes = jo.get("notes").getAsJsonArray();
		for(int j = 0; j < instruments.length; j++){
			String instrument = instruments[j];
			Note[][] notes = new Note[jaNotes.size()][];
			for(int i = 0; i < jaNotes.size(); i++){
				JsonArray jaNotesIntern = jaNotes.get(i).getAsJsonArray();
				List<Note> internNotes = new ArrayList<Note>();
				for(int k = 0; k < jaNotesIntern.size(); k += 2){
					if(jaNotesIntern.size() > k + 1){
						if(jaNotesIntern.get(k).getAsInt() == j)
							internNotes.add(new Note(this.INSTRUMENTS.get(instrument), jaNotesIntern.get(k + 1).getAsInt()));
					}
				}
				notes[i] = internNotes.toArray(new Note[internNotes.size()]);	
			}
			this.notes.put(instrument, notes);
		}
	}
	
	public void play(String instrument, int note, World world, BlockPos pos){
		if(notes.containsKey(instrument)){
			Note[][] notes = this.notes.get(instrument);
			if(note < notes.length){
				Note[] notes2 = notes[note];
				for(int i = 0; i < notes2.length; i++){
					notes2[i].playSound(world, pos);
				}
			}
		}
	}
}
