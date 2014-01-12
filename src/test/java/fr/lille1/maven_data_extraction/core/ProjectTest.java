package fr.lille1.maven_data_extraction.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ProjectTest {

	private Project project;

	@Before
	public void setUp() throws Exception {
		project = new Project("com.googlecode.jmockit", "guava");
		project.addVersion(new Version("0.0.1"));
		project.addVersion(new Version("0.1.1"));
	}

	@Test
	public void testAddVersion() {
		Version newVersion = new Version("0.2");
		project.addVersion(newVersion);
		assertEquals(3, project.getVersionsSize());
		assertEquals(newVersion, project.getVersion("0.2"));
	}

	@Test
	public void testRemoveVersion() {
		project.removeVersion("0.0.1");
		assertEquals(1, project.getVersionsSize());
		assertEquals(null, project.getVersion("0.0.1"));
	}

	@Test
	public void testEqualsObject() {
		Project newProject = new Project("com.googlecode.jmockit", "guava");
		newProject.equals(project);
	}

}
