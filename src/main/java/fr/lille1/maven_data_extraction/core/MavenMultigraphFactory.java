package fr.lille1.maven_data_extraction.core;

import java.util.HashMap;
import java.util.Map;

import com.google.gag.annotation.disclaimer.AhaMoment;
import com.google.gag.enumeration.Where;

/**
 * 
 * @author Alexandre Bonhomme
 * 
 * @param <G>
 */
public class MavenMultigraphFactory<G> {

	private static final int INIT_MAP_SIZE = 500000;


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
	public G build(Class<? extends G> graphClass) {
		Map<String, Project> mapOfProjects = new HashMap<String, Project>(
				INIT_MAP_SIZE);

		// TODO Implement data extraction from pom files
		// Example :
		// mapOfProjects.put(groupId + artifactId, new Project(groupId,
		// artifactId, ...);

		G graph = createGraph(graphClass);

		// TODO Implement vertex adding and edge connecting

		return graph;
	}

	private G createGraph(Class<? extends G> graphClass) {
		try {
			return graphClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Graph factory faild", e);
		}
	}
}
