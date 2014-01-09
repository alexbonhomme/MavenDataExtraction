package fr.lille1.maven_data_extraction;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.jdom2.JDOMException;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

public class Main {

	public static void main(String[] args) throws JDOMException, IOException {
		
		Project p = new Project("com.android.support", "support-v4");
		Version v = new Version("18.0.+", new File(""));
		
		File pom = new File("/home/klem/Documents/maven_central/download/asia/ivity/android/drag-sort-listview/1.0/drag-sort-listview-1.0.pom");
		File pom2 = new File("/home/klem/Documents/maven_central/download/asia");
		
//		DataExtraction dex = new DataExtractionImpl(p,v, pom2);
		//dex.getDependent(pom);
//		dex.getAllDependent();
	}

}
