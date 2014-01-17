package fr.lille1.maven_data_extraction.console;

import java.util.List;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

/**
 * 
 * @author Alexandre Bonhomme
 * 
 */
public interface MavenMetricsConsole {

	void printAllStats();

	void printTopUsages();

	void printStatsOf(String groupId, String artifactId);

	List<Project> dependenciesOf(Project p);

	List<Project> dependenciesOf(String groupId, String artifactId);

	List<Project> dependenciesOf(Project p, Version v);

	List<Project> dependenciesOf(String groupId, String artifactId,
			String versionNumber);

	List<Project> usagesOf(Project p);

	List<Project> usagesOf(String groupId, String artifactId);

	List<Project> usagesOf(Project p, Version v);

	List<Project> usagesOf(String groupId, String artifactId,
			String versionNumber);

	double confidenceOf(Project p);

	double confidenceOf(String groupId, String artifactId);

}