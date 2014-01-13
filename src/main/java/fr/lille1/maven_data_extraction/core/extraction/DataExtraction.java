package fr.lille1.maven_data_extraction.core.extraction;

import java.util.HashMap;

import fr.lille1.maven_data_extraction.core.Project;

/**
 * @author Clement Dufour
 *
 */
public interface DataExtraction {

	/**
	 * @return HashMap of all project contains in DataExtraction param
	 */
	HashMap<String, Project> getAllProject();

}
