package fr.lille1.maven_data_extraction.core;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import fr.lille1.maven_data_extraction.Project;

public class GraphExample {

	/**
	 * non-instantiable
	 */
	private GraphExample() {
	}

	/**
	 * Build an instance (singleton) of the dependencies graph
	 */
	public static DirectedGraph<Project, DefaultEdge> getInstance() {
		DirectedGraph<Project, DefaultEdge> graph = new DefaultDirectedGraph<Project, DefaultEdge>(
				DefaultEdge.class);

		Project p1 = new Project("org.apache.abdera", "abdera", "1.0", null);
		Project p2 = new Project("org.apache.accumulo", "accumulo-project",
				"1.5.0", null);
		Project p3 = new Project("org.apache.ace", "ace-pom",
				"0.8.0-incubator", null);

		// add the vertices
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addVertex(p3);

		// add edges to create linking structure
		graph.addEdge(p1, p2);
		graph.addEdge(p1, p3);
		graph.addEdge(p2, p3);

		return graph;
	}

}
