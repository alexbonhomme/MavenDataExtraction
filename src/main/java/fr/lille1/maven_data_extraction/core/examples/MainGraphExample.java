package fr.lille1.maven_data_extraction.core.examples;

import java.io.File;

import fr.lille1.maven_data_extraction.Project;
import fr.lille1.maven_data_extraction.core.DefaultMavenGraph;

public class MainGraphExample {

	public static void main(String[] args) {
		DefaultMavenGraph g = new DefaultMavenGraph();

		Project p1 = new Project("org.apache.abdera", "abdera", "1.0", new File(""));
		Project p2 = new Project("org.apache.accumulo", "accumulo-project", "1.5.0", new File(""));
		Project p3 = new Project("org.apache.ace", "ace-pom", "0.8.0-incubator", new File(""));

		// add the vertices
		g.addVertex(p1);
		g.addVertex(p2);
		g.addVertex(p3);

		// add edges to create linking structure
		g.addEdge(p1, p2);
		g.addEdge(p1, p3);
		g.addEdge(p2, p3);

		System.err.println(g);
	}

}
