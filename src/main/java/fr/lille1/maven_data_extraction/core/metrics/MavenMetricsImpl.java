package fr.lille1.maven_data_extraction.core.metrics;

import java.util.ArrayList;
import java.util.List;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;
import fr.lille1.maven_data_extraction.core.graph.MavenLabeledEdge;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraph;

public class MavenMetricsImpl implements MavenMetrics {

	private final MavenMultigraph<MavenLabeledEdge> graph;

	public MavenMetricsImpl(MavenMultigraph<MavenLabeledEdge> graph) {
		this.graph = graph;
	}

	@Override
	public List<Project> computeDependencies(Project p, Version v) {
		List<Project> dependencies = new ArrayList<Project>();

		for (MavenLabeledEdge edge : graph.edgesOf(p)) {
			if (!edge.getSourceVersionNumber().equals(v.getVersionNumber())) {
				continue;
			}

			// We directly compare the target, because if we simply compare the
			// source, we have to do another call to get the target.
			Project target = graph.getEdgeTarget(edge);
			if (!target.equals(p)) {
				dependencies.add(target);
			}
		}

		return dependencies;
	}

	@Override
	public List<Project> computeDependencies(Project p) {
		List<Project> dependencies = new ArrayList<Project>();

		for (MavenLabeledEdge edge : graph.edgesOf(p)) {
			Project target = graph.getEdgeTarget(edge);
			if (!target.equals(p)) {
				dependencies.add(target);
			}
		}

		return dependencies;
	}

	@Override
	public List<Project> computeUsages(Project p, Version v) {
		List<Project> usages = new ArrayList<Project>();

		for (MavenLabeledEdge edge : graph.edgesOf(p)) {
			if (!edge.getTargetVersionNumber().equals(v.getVersionNumber())) {
				continue;
			}

			Project source = graph.getEdgeSource(edge);
			if (!source.equals(p)) {
				usages.add(source);
			}
		}

		return usages;
	}

	@Override
	public List<Project> computeAllUsages(Project p) {
		List<Project> usages = new ArrayList<Project>();

		for (MavenLabeledEdge edge : graph.edgesOf(p)) {
			Project source = graph.getEdgeSource(edge);
			if (!source.equals(p)) {
				usages.add(source);
			}
		}

		return usages;
	}
}
