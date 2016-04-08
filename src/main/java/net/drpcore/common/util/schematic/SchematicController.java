package net.drpcore.common.util.schematic;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

import net.drpcore.common.DarkRoleplayCore;

public class SchematicController {
	
	public static ArrayList<String> Schematics = new ArrayList<String>();
	
	public static void findSchematics(){
		File[] SubDirectorys = DarkRoleplayCore.schematicPath.listFiles();
		
		ArrayList<File[]> schematics = new ArrayList<File[]>();
		
		
		if(SubDirectorys != null){
			for(File potentialFile: SubDirectorys){
				if(potentialFile.isDirectory()){
				
					File[] tempSchematics = potentialFile.listFiles(new FilenameFilter(){
						@Override
						public boolean accept(File dir, String name) {
							if(name.endsWith(".schematic"))
								return true;
							return false;
						}
					});
									
					schematics.add(tempSchematics);
					
				}else{
					Schematics.add(potentialFile.getName());
				}
			}
			
			for(File[] SchematicList: schematics){
				for(File Schematic : SchematicList){
					Schematics.add(Schematic.getName());
				}
			}
		}
	}
	
	public static void debug(){
		for(String name : Schematics){
			System.out.println(name);
		}
	}
	
}
