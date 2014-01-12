package fr.lille1.maven_data_extraction.core.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

/**
 * 
 * @author Alexandre Bonhomme
 * 
 */
public class MavenMultigraphLabeledTest {

	private MavenMultigraph<MavenLabeledEdge> graph;

	private Project p1;
	private Project p2;
	private Project p3;

	@Before
	public void setUp() throws Exception {
		graph = new MavenMultigraphLabeled();

		p1 = new Project("org.apache.abdera", "abdera");
		p1.addVersion(new Version("1.0"));
		p1.addVersion(new Version("1.1.1"));

		p2 = new Project("org.apache.accumulo", "accumulo-core");
		p2.addVersion(new Version("1.3.6"));

		p3 = new Project("org.apache.ace", "ace-pom");
		p3.addVersion(new Version("0.8.0-incubator"));
		p3.addVersion(new Version("0.8.1-incubator"));

		// add the vertices
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addVertex(p3);

		// p1.v1 -> p2.v1
		graph.addEdge(p1, p2, p1.getVersion("1.0").getVersionNumber(), p2
				.getVersion("1.3.6").getVersionNumber());
		// p1.v1 -> p3.v1
		graph.addEdge(p1, p3, p1.getVersion("1.0").getVersionNumber(), p3
				.getVersion("0.8.0-incubator").getVersionNumber());
		// p2.v1 -> p3.v2
		graph.addEdge(p2, p3, p2.getVersion("1.3.6").getVersionNumber(), p3
				.getVersion("0.8.1-incubator").getVersionNumber());
	}

	@Test
	public void testGetVertex() {
		Project project = graph.getVertex("org.apache.abdera", "abdera");
		assertEquals(p1, project);
	}

	@Test
	public void testGetDependencies() {
		List<Project> dependencies = graph.getDependencies(p1);
		assertEquals(2, dependencies.size());
		assertTrue(dependencies.contains(p2));
		assertTrue(dependencies.contains(p3));
	}

	@Test
	public void testGetUsages() {
		List<Project> usages = graph.getUsages(p3);
		assertEquals(2, usages.size());
		assertTrue(usages.contains(p1));
		assertTrue(usages.contains(p2));
	}

}
