package fr.lille1.maven_data_extraction;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;
import fr.lille1.maven_data_extraction.core.extraction.DataExtraction;
import fr.lille1.maven_data_extraction.core.extraction.DataExtractionImpl;

public class Main {

	public static void main(String[] args) {
		
		File pom = new File("/home/klem/Documents/maven_central/download/asia/ivity/android/drag-sort-listview/1.0/drag-sort-listview-1.0.pom");
		File pom2 = new File("/home/klem/Documents/maven_central/download/asia");
		
		
		DataExtraction dex = new DataExtractionImpl(pom2);
		HashMap<String, Project> map = dex.getAllProject();
		
		for (Map.Entry<String, Project> res : map.entrySet()) {
			
			System.out.println("==Project== " + res.getKey());
			
			Iterator iterator = res.getValue().getVersionsIterator();
			
			while(iterator.hasNext()) {
				Version resVersion =  (Version) iterator.next();
				System.out.println("=Version= " + resVersion.getVersionNumber());
			}
			
		}
		
	}

}
