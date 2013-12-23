package fr.lille1.maven_data_extraction.core;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * This {@link Graph graph} represent the projects and their dependencies from
 * the Maven Central repository.
 * 
 * Should be construct with the {@link MavenMultigraphFactory} for example :
 * 
 * MavenMultigraph<MavenLabeledEdge> g = new
 * MavenMultigraphFactory<MavenMultigraph
 * >(MavenMultigraphLabeled.class).build();
 * 
 * @author Alexandre Bonhomme
 * 
 * @param <E>
 *            Type of the edge to used in the graph. Could be
 *            {@link DefaultEdge} for example.
 */
public interface MavenMultigraph<E> {

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
	 * Returns {@link true} if and only if this graph contains a {@link Project}
	 * u such that <code>u.equals(p)</code>. If the specified {@link Project
	 * project} is {@link null} returns {@link false}.
	 */
	boolean containsVertex(Project p);

	/**
	 * Return a {@link Project} object which have the same <code>groupId</code>
	 * and <code>artifactId</code>, or {@link null} otherwise.
	 */
	Project getVertex(String groupId, String artifactId);

	/**
	 * Add an edge between the {@link Project} <code>source</code> and the
	 * {@link Project} <code>target</code>
	 * 
	 * @return The created {@link E edge}, {@link null} otherwise.
	 */
	E addEdge(Project source, Project target, String sourceVersion,
			String targetVersion);

	/**
	 * Remove the specified edge <code>e</code> from the {@link Graph graph}
	 * 
	 * @return {@link true} if and only if the graph contained the specified
	 *         edge.
	 */
	boolean removeEdge(E e);

	/**
	 * Remove all the edges between the {@link Project} <code>source</code> and
	 * the {@link Project} <code>target</code>
	 * 
	 * @return The remove {@link E edge} or {@link null}
	 */
	Set<E> removeAllEdges(Project source, Project target);

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
