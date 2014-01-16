package fr.lille1.maven_data_extraction.core.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.python.google.common.collect.Ordering;

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

	@Override
	public double confidence(Project p) {
		double confidence = 0;

		long mu = computeAllUsages(p).size();
		if (mu <= 0) {
			return confidence;
		}

		/*
		 * Computation of the main term : log(µ)
		 */
		confidence = Math.log(mu);

		List<Version> versionsList = p.getAllVersions();
		
		// we don't have enough points to compute the alpha coefficient
		if (versionsList.size() < 3) {
			return confidence;
		}
		
		// Sorting and reversing to get the last 3 versions an the beginning of the list
		Collections.sort(versionsList, Ordering.usingToString().reverse());
		
		/*
		 * Computation of the coefficient Alpha
		 */
		long mu_v2 = computeUsages(p, versionsList.get(2)).size();
		if (mu_v2 <= 0) {
			return confidence;
		}

		long mu_v  = computeUsages(p, versionsList.get(0)).size();
		long mu_v1 = computeUsages(p, versionsList.get(1)).size();
		
		long nu_v = mu_v + mu_v1 + mu_v2;
		long nu_v2 = mu_v2;

		double alpha = ((nu_v - nu_v2) / nu_v2);

		return confidence * alpha;
	}
}
