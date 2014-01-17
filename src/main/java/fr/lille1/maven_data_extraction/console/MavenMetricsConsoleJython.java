package fr.lille1.maven_data_extraction.console;

import java.util.ArrayList;
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
		int count = 1;
		Set<Entry<Integer, Project>> usagesList = usages.entrySet();
		for (Entry<Integer, Project> entry : usagesList) {
			if (count > 10) {
				return;
			}

			if (entry.getKey() == 0) {
				return;
			}

			System.out.println("\t" + entry.getKey() + " " + entry.getValue());
			++count;
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

	@Override
	public List<Integer> cumulativeHistUsages() {
		List<Integer> hist = new ArrayList<>();

		SortedMap<Integer, List<Project>> map = new TreeMap<>();
		for (Project project : graph.getAllVertices()) {
			int usageNumber = usagesOf(project).size();
			if (map.containsKey(usageNumber)) {
				map.get(usageNumber).add(project);
			} else {
				List<Project> list = new ArrayList<>();
				list.add(project);
				map.put(usageNumber, list);
			}
		}

		// Cumulative
		for (Entry<Integer, List<Project>> entry : map.entrySet()) {
			if (hist.size() == 0) {
				hist.add(entry.getValue().size());
			} else {
				hist.add(hist.get(hist.size() - 1) + entry.getValue().size());
			}
		}

		return hist;
	}

	@Override
	public List<Integer> cumulativeHistDependencies() {
		List<Integer> hist = new ArrayList<>();

		SortedMap<Integer, List<Project>> map = new TreeMap<>();
		for (Project project : graph.getAllVertices()) {
			int dependenciesNumber = dependenciesOf(project).size();
			if (map.containsKey(dependenciesNumber)) {
				map.get(dependenciesNumber).add(project);
			} else {
				List<Project> list = new ArrayList<>();
				list.add(project);
				map.put(dependenciesNumber, list);
			}
		}

		// Cumulative
		for (Entry<Integer, List<Project>> entry : map.entrySet()) {
			if (hist.size() == 0) {
				hist.add(entry.getValue().size());
			} else {
				hist.add(hist.get(hist.size() - 1) + entry.getValue().size());
			}
		}

		return hist;
	}
}
