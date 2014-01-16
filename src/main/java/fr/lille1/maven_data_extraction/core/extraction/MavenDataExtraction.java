package fr.lille1.maven_data_extraction.core.extraction;

import java.util.Collection;

import fr.lille1.maven_data_extraction.core.Project;

/**
 * @author Clement Dufour
 *
 */
public interface MavenDataExtraction {

	/**
	 * Return a {@link Collection} of {@link Project} objects extract from
	 * <code>.pom</code> files set.
	 */
	Collection<Project> computeAllProjects();

}
