package fr.lille1.maven_data_extraction.core;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;

/**
 * The {@link Graph graph} represent the dependencies of all Maven packages.
 * 
 * @author Alexandre Bonhomme
 * 
 * @param <E>
 *            Type of the edge to used in the graph. Could be
 *            {@link DefaultEdge} for example.
 */
public interface MavenGraph<E> {

	/**
	 * Add the {@link Project} <code>p</code> in the {@link Graph graph}
	 * 
	 * @return {@link true} if this {@link Graph graph} did not already contain
	 *         the specified {@link Project project}.
	 */
	boolean addVertex(Project p);

	/**
	 * Remove the {@link Project} <code>p</code> in the {@link Graph graph}
	 * 
	 * @return {@link true} if the {@link Graph graph} contained the specified
	 *         {@link Project project}; {@link false} otherwise.
	 */
	boolean removeVertex(Project p);

	/**
	 * Add an edge between the {@link Project} <code>source</code> and the
	 * {@link Project} <code>target</code>
	 * 
	 * @return The created {@link E edge}
	 */
	E addEdge(Project source, Project target);

	/**
	 * Add the edge between the {@link Project} <code>source</code> and the
	 * {@link Project} <code>target</code>
	 * 
	 * @return The remove {@link E edge} or {@link null}
	 */
	E removeEdge(Project source, Project target);

	/**
	 * List all dependencies of the {@link Project} <code>p</code>
	 */
	List<Project> getDependencies(Project p);

	/**
	 * List all {@link Project Project} which have {@link Project}
	 * <code>p</code> in their dependencies
	 */
	List<Project> getUsages(Project p);
}
