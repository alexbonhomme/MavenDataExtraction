package fr.lille1.maven_data_extraction.core.extraction;

import java.util.List;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

public interface PomExtraction {

	Project getProject();

	Version getVersion();

	String getGroupId();

	String getArtifactId();

	String getNumberVersion();

	/**
	 * @return the list of dependencies of pom file. Each dependency is
	 *         transform in Version in a project added to the list
	 */
	List<Project> getDependencies();

}