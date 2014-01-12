package fr.lille1.maven_data_extraction.core.graph;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

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

	private static final Logger log = Logger
			.getLogger(MavenMultigraphFactory.class);
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
		log.info("Starting .pom parsing...");
		Map<String, Project> mapOfProjects = extractor.getAllProject();

		/*
		 * Creating and filling of the dependencies graph
		 */
		log.info("Starting graph creation...");
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

				// Added an edge for each dependence
				for (Project depProject : version.getDependencies()) {
					// Here we get the correct reference about the project we're
					// looking for into the graph
					Project refDepProject = mapOfProjects.get(depProject
							.getGroupId() + "." + depProject.getArtifactId());

					// This case appeared when the dependence isn't in the set
					// of analyzed projects
					if (refDepProject == null) {
						continue;
					}

					// XXX This is pretty confused because here we're just
					// looking for the dep version, but we have to use a
					// temporary Project object
					Version depVersion = depProject.getVersionsIterator()
							.next();

					graph.addEdge(project, refDepProject,
							version.getVersionNumber(),
							depVersion.getVersionNumber());
				}
			}
		}

		return graph;
	}

	private MavenMultigraph<?> createGraph() {
		try {
			return graphClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Graph factory failed", e);
		}
	}
}
