package fr.lille1.maven_data_extraction.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;

import fr.lille1.maven_data_extraction.Project;

public class DefaultMavenGraphTest {

	private MavenGraph<DefaultEdge> graph;

	@Before
	public void setUp() throws Exception {
		graph = new DefaultMavenGraph();
	}

	@Test
	public void testGetDependencies() {
		Project p1 = new Project("org.apache.abdera", "abdera", "1.0",
				new File(""));
		Project p2 = new Project("org.apache.accumulo", "accumulo-project",
				"1.5.0", new File(""));
		Project p3 = new Project("org.apache.ace", "ace-pom",
				"0.8.0-incubator", new File(""));

		// add the vertices
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addVertex(p3);

		// add edges to create linking structure
		graph.addEdge(p1, p2); // p1 -> p2
		graph.addEdge(p1, p3); // p1 -> p3
		graph.addEdge(p2, p3); // p2 -> p3

		/*
		 * Test
		 */
		List<Project> dependencies = graph.getDependencies(p1);
		assertEquals(2, dependencies.size());
		assertTrue(dependencies.contains(p2));
		assertTrue(dependencies.contains(p3));
	}

	@Test
	public void testGetUsages() {
		Project p1 = new Project("org.apache.abdera", "abdera", "1.0",
				new File(""));
		Project p2 = new Project("org.apache.accumulo", "accumulo-project",
				"1.5.0", new File(""));
		Project p3 = new Project("org.apache.ace", "ace-pom",
				"0.8.0-incubator", new File(""));

		// add the vertices
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addVertex(p3);

		// add edges to create linking structure
		graph.addEdge(p1, p2); // p1 -> p2
		graph.addEdge(p1, p3); // p1 -> p3
		graph.addEdge(p2, p3); // p2 -> p3

		/*
		 * Test
		 */
		List<Project> usages = graph.getUsages(p3);
		assertEquals(2, usages.size());
		assertTrue(usages.contains(p1));
		assertTrue(usages.contains(p2));
	}

}
