package fr.lille1.maven_data_extraction.core.graph;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import fr.lille1.maven_data_extraction.core.extraction.MavenDataExtraction;
import fr.lille1.maven_data_extraction.core.extraction.MavenDataExtractionImpl;

public class MavenMultigraphFactoryTest {

	private MavenMultigraphFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new MavenMultigraphFactory(MavenMultigraphLabeled.class);
	}

	@Test
	public void testBuild() {
		File root = new File("src/test/resources/asia");
		System.out.println(root.getAbsolutePath());
		MavenDataExtraction extractor = new MavenDataExtractionImpl(root);

		MavenMultigraph<?> graph = factory.build(extractor);
		// TODO testing
	}

}
