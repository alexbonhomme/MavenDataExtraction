package fr.lille1.maven_data_extraction;

import java.io.File;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		Project projet = new Project("com.googlecode.jmockit", "guava", "", new File("~/workspace/IDL/MavenDataExtraction/testGeneral/source"));
		File folder = new File("");
		
		DataExtraction dex = new DataExtractionImpl(folder, projet);
		
		List<File> listFile = dex.findPom(folder);
		for (File file : listFile) {
			System.out.println(file.toString());
		}
	}

}
