package fr.lille1.maven_data_extraction.core.metrics;

import java.util.List;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

public interface MavenMetrics {

	/**
	 * List all dependencies of the {@link Project} <code>p</code> for a given
	 * {@link Version} <code>v</code>
	 */
	List<Project> computeDependencies(Project p, Version v);

	/**
	 * List all dependencies of the {@link Project} <code>p</code>
	 */
	List<Project> computeDependencies(Project p);

	/**
	 * List all {@link Project Project} in the given {@link Version}
	 * <code>v</code> which have {@link Project} <code>p</code> in their
	 * dependencies
	 */
	List<Project> computeUsages(Project p, Version v);

	/**
	 * List all {@link Project Project} which have {@link Project}
	 * <code>p</code> in their dependencies
	 */
	List<Project> computeAllUsages(Project p);

	/**
	 * Compute a metric of confidence for a {@link Project} <code>p</code>
	 * 
	 * @return A value higher or equal to zero
	 */
	double confidence(Project p);

}