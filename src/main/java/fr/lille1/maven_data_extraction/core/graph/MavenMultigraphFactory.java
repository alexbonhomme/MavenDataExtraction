package fr.lille1.maven_data_extraction.core.graph;

import java.util.Iterator;
import java.util.Map;

import com.google.gag.annotation.disclaimer.AhaMoment;
import com.google.gag.enumeration.Where;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;
import fr.lille1.maven_data_extraction.core.extraction.DataExtraction;

/**
 * 
 * @author Alexandre Bonhomme
 * 
 * @param <G>
 */
public class MavenMultigraphFactory {

	private static final int INIT_MAP_SIZE = 500000;

	private final Class<? extends MavenMultigraph<?>> graphClass;

	/**
	 * @param graphClass
	 */
	public MavenMultigraphFactory(Class<? extends MavenMultigraph<?>> graphClass) {
		this.graphClass = graphClass;
	}

	@AhaMoment(Where.TOILET)
	/*-
	 * Return an instance of the given <code>graphClass</code> filled of {@link Project} 
	 * 
	 * Algorithm:
	 * 
	 * foreach Files do:
	 *   create Project from File.
	 *   add Project to Map.
	 * 
	 * foreach Project in Map do:
	 *   add Project to Graph.
	 *   get all Project dependencies from Map.
	 *   add Edge between Projects.
	 */
	public MavenMultigraph<?> build(DataExtraction extractor) {
		/*
		 * Get all projects from the specified extractor
		 */
		Map<String, Project> mapOfProjects = extractor.getAllProject();

		/*
		 * Creating and filling of the dependencies graph
		 */
		MavenMultigraph<?> graph = createGraph();

		// Adding all vertices
		for (Project project : mapOfProjects.values()) {
			graph.addVertex(project);
		}
		
		// Adding all edges (dependencies per versions)
		for (Project project : mapOfProjects.values()) {
			Iterator<Version> it = project.getVersionsIterator();
			while (it.hasNext()) {
				Version version = it.next();

				/*-
				 * Some problems here... How can I get dependencies per version? There is no methods. 
				 * Did we need a getDependencies() in Version class ?
				 * 
				 * IMHO we have to collect the dependencies list in the first pass on the POMs list.
				 */
				// TODO Adding edges for the dependencies
			}
		}

		return graph;
	}

	private MavenMultigraph<?> createGraph() {
		try {
			return graphClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Graph factory faild", e);
		}
	}
}
