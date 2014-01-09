package fr.lille1.maven_data_extraction;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

public class Main {

	public static void main(String[] args) throws JDOMException, IOException {
		
		File pom = new File("/home/klem/Documents/maven_central/download/asia/ivity/android/drag-sort-listview/1.0/drag-sort-listview-1.0.pom");
		
		DataExtraction dex = new DataExtractionImpl(pom, new File("/"));
		
	}

}
