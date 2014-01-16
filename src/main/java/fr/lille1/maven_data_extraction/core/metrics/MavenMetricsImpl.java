package fr.lille1.maven_data_extraction.core.metrics;

import java.util.ArrayList;
import java.util.List;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;
import fr.lille1.maven_data_extraction.core.graph.MavenLabeledEdge;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraph;

public class MavenMetrics {

	private MavenMetrics() {
	}

	/**
	 * List all dependencies of the {@link Project} <code>p</code> for a given
	 * {@link Version} <code>v</code>
	 */
	public static List<Project> computeDependencies(
			MavenMultigraph<MavenLabeledEdge> g, Project p, Version v) {
		List<Project> dependencies = new ArrayList<Project>();

		for (MavenLabeledEdge edge : g.edgesOf(p)) {
			if (!edge.getSourceVersionNumber().equals(v.getVersionNumber())) {
				continue;
			}

			Project target = g.getEdgeTarget(edge);
			if (!target.equals(p)) {
				dependencies.add(target);
			}
		}

		return dependencies;
	}

	/**
	 * List all dependencies of the {@link Project} <code>p</code>
	 */
	public static List<Project> computeAllDependencies(
			MavenMultigraph<MavenLabeledEdge> g, Project p) {
		List<Project> dependencies = new ArrayList<Project>();

		for (MavenLabeledEdge edge : g.edgesOf(p)) {
			// We directly compare the target, because if we simply compare the
			// source, we have to do another call to get the target.
			Project target = g.getEdgeTarget(edge);
			if (!target.equals(p)) {
				dependencies.add(target);
			}
		}

		return dependencies;
	}

	/**
	 * List all {@link Project Project} in the given {@link Version}
	 * <code>v</code> which have {@link Project} <code>p</code> in their
	 * dependencies
	 */
	public static List<Project> computeUsages(
			MavenMultigraph<MavenLabeledEdge> g, Project p, Version v) {
		List<Project> usages = new ArrayList<Project>();

		for (MavenLabeledEdge edge : g.edgesOf(p)) {
			if (!edge.getTargetVersionNumber().equals(v.getVersionNumber())) {
				continue;
			}

			Project source = g.getEdgeSource(edge);
			if (!source.equals(p)) {
				usages.add(source);
			}
		}

		return usages;
	}

	/**
	 * List all {@link Project Project} which have {@link Project}
	 * <code>p</code> in their dependencies
	 */
	public static List<Project> computeAllUsages(
			MavenMultigraph<MavenLabeledEdge> g, Project p) {
		List<Project> usages = new ArrayList<Project>();

		for (MavenLabeledEdge edge : g.edgesOf(p)) {
			Project source = g.getEdgeSource(edge);
			if (!source.equals(p)) {
				usages.add(source);
			}
		}

		return usages;
	}
}
