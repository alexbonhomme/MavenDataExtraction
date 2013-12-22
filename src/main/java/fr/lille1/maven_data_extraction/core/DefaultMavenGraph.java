package fr.lille1.maven_data_extraction.core;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * 
 * @author Alexandre Bonhomme
 * 
 */
public class DefaultMavenGraph implements MavenGraph<DefaultEdge> {

	private final Graph<Project, DefaultEdge> graph;

	/**
	 * Initialize a {@link DefaultDirectedGraph directed graph} with vertex type {@link Project} and edges type {@link DefaultEdge}
	 */
	public DefaultMavenGraph() {
		this.graph = new DefaultDirectedGraph<Project, DefaultEdge>(
				DefaultEdge.class);
	}

	@Override
	public boolean addVertex(Project p) {
		return graph.addVertex(p);
	}

	@Override
	public boolean removeVertex(Project p) {
		return graph.removeVertex(p);
	}

	@Override
	public DefaultEdge addEdge(Project source, Project target) {
		return graph.addEdge(source, target);
	}

	@Override
	public DefaultEdge removeEdge(Project source, Project target) {
		return graph.removeEdge(source, target);
	}

	@Override
	public List<Project> getDependencies(Project p) {
		List<Project> dependencies = new ArrayList<Project>();

		for (DefaultEdge edge : graph.edgesOf(p)) {
			Project target = graph.getEdgeTarget(edge);
			if (!target.equals(p)) {
				dependencies.add(target);
			}
		}

		return dependencies;
	}

	@Override
	public List<Project> getUsages(Project p) {
		List<Project> usages = new ArrayList<Project>();

		for (DefaultEdge edge : graph.edgesOf(p)) {
			Project source = graph.getEdgeSource(edge);
			if (!source.equals(p)) {
				usages.add(source);
			}
		}

		return usages;
	}

	@Override
	public String toString() {
		return graph.toString();
	}

}
