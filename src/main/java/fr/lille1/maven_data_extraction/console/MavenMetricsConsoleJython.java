package fr.lille1.maven_data_extraction.console;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;
import fr.lille1.maven_data_extraction.core.graph.MavenLabeledEdge;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraph;
import fr.lille1.maven_data_extraction.core.metrics.MavenMetrics;
import fr.lille1.maven_data_extraction.core.metrics.MavenMetricsImpl;

public class MavenMetricsConsoleJython implements MavenMetricsConsole {

	private final MavenMultigraph<MavenLabeledEdge> graph;
	private final MavenMetrics metrics;

	public MavenMetricsConsoleJython(MavenMultigraph<MavenLabeledEdge> graph) {
		this.graph = graph;
		this.metrics = new MavenMetricsImpl(graph);
	}

	@Override
	public void printAllStats() {
		String out = new String();

		out += "\tNumber of artifacts: " + graph.getAllVertices().size();

		System.out.println(out);
	}

	@Override
	public void printStatsOf(String groupId, String artifactId) {
		String out = new String();

		out += "\tDependencies: " + dependenciesOf(groupId, artifactId).size();
		out += "\n\tUsages: " + usagesOf(groupId, artifactId).size();
		out += "\n\tConfidence: " + confidenceOf(groupId, artifactId);

		System.out.println(out);
	}

	@Override
	public void printTopUsages() {
		SortedMap<Integer, Project> usages = new TreeMap<>(
				Collections.reverseOrder());
		for (Project project : graph.getAllVertices()) {
			usages.put(metrics.computeAllUsages(project).size(), project);
		}

		System.out.println("Top 10 usages:");
		Set<Entry<Integer, Project>> usagesList = usages.entrySet();
		for (Entry<Integer, Project> entry : usagesList) {
			if (entry.getKey() == 0) {
				continue;
			}

			System.out.println("\t" + entry.getKey() + " " + entry.getValue());
		}
	}

	@Override
	public List<Project> dependenciesOf(Project p) {
		return metrics.computeDependencies(p);
	}

	@Override
	public List<Project> dependenciesOf(String groupId, String artifactId) {
		return dependenciesOf(new Project(groupId, artifactId));
	}

	@Override
	public List<Project> dependenciesOf(Project p, Version v) {
		return metrics.computeDependencies(p, v);
	}

	@Override
	public List<Project> dependenciesOf(String groupId, String artifactId,
			String versionNumber) {
		return dependenciesOf(new Project(groupId, artifactId), new Version(
				versionNumber));
	}

	@Override
	public List<Project> usagesOf(Project p) {
		return metrics.computeAllUsages(p);
	}

	@Override
	public List<Project> usagesOf(String groupId, String artifactId) {
		return usagesOf(new Project(groupId, artifactId));
	}

	@Override
	public List<Project> usagesOf(Project p, Version v) {
		return metrics.computeUsages(p, v);
	}

	@Override
	public List<Project> usagesOf(String groupId, String artifactId,
			String versionNumber) {
		return usagesOf(new Project(groupId, artifactId), new Version(
				versionNumber));
	}

	@Override
	public double confidenceOf(Project p) {
		return metrics.confidence(p);
	}

	@Override
	public double confidenceOf(String groupId, String artifactId) {
		return confidenceOf(new Project(groupId, artifactId));
	}
}
