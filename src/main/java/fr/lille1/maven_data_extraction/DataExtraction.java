package fr.lille1.maven_data_extraction;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

/**
 * @author Clement Dufour
 *
 */
public interface DataExtraction {

	/**
	 * @param folder
	 * @return list of files ".pom" contained in folder
	 */
	List<File> findPom(File folder);

	
	/**
	 * @return HashMap of all project contains in DataExtractionParam
	 */
	HashMap<String, Project> getAllProject();

}
