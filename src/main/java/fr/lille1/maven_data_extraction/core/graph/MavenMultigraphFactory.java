package fr.lille1.maven_data_extraction.core.graph;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.google.gag.annotation.disclaimer.AhaMoment;
import com.google.gag.enumeration.Where;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;
import fr.lille1.maven_data_extraction.core.exceptions.MavenGraphException;
import fr.lille1.maven_data_extraction.core.extraction.MavenDataExtraction;

/**
 * 
 * @author Alexandre Bonhomme
 * 
 * @param <G>
 */
public class MavenMultigraphFactory {

	private static final Logger log = Logger
			.getLogger(MavenMultigraphFactory.class);
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
	public MavenMultigraph<?> build(MavenDataExtraction extractor) {
		/*
		 * Get all projects from the specified extractor
		 */
		log.info("Starting .pom parsing...");
		Collection<Project> projects = extractor.computeAllProjects();

		/*
		 * Creating and filling of the dependencies graph
		 */
		log.info("Starting graph creation...");
		MavenMultigraph<?> graph = createGraph();

		// Adding all vertices
		for (Project project : projects) {
			graph.addVertex(project);
		}
		
		// GC
		projects = null;
		
		/*
		 * TODO TO MUCH COMPLEXITY
		 * 
		 * Adding all edges (dependencies per versions)
		 */
		Collection<Project> listOfProject = graph.getAllVertices();
		long total = listOfProject.size(), current = 1;
		for (Project project : listOfProject) {
			// Display the progression
			System.out.println("[" + current + "/" + total + "] : " + project);
			++current;

			Iterator<Version> it = project.getVersionsIterator();
			while (it.hasNext()) {
				Version version = it.next();
				log.debug(version);

				// We add a specific edge when the project is child of another.
				// (i.e. When the project has a parent)
				if (version.hasParent()) {
					Project parentProject = graph.getVertex(
							version.getParentGroupId(),
							version.getParentAritfactId());

					if (parentProject != null) {
						try {
							graph.addEdge(project, parentProject, "child",
									"parent");
						} catch (MavenGraphException e) {
							log.debug(e.getLocalizedMessage());
						} catch (IllegalArgumentException e) {
							log.debug(e.getLocalizedMessage());
						}
					}
				}

				// Add an edge for each dependence
				for (Project depProject : version.getDependencies()) {
					log.trace(depProject);

					// XXX This is pretty confused because here we're just
					// looking for the dep version, but we have to use a
					// temporary Project object
					Version depVersion = depProject.getVersionsIterator()
							.next();

					try {
						graph.addEdge(project, depProject,
								version.getVersionNumber(),
								depVersion.getVersionNumber());
					} catch (MavenGraphException e) {
						log.debug(e.getLocalizedMessage());
					} catch (IllegalArgumentException e) {
						log.debug(e.getLocalizedMessage());
					}
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
