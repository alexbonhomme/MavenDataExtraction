package fr.lille1.maven_data_extraction.core.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lombok.NonNull;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;

import com.google.gag.annotation.disclaimer.AhaMoment;
import com.google.gag.annotation.remark.OhNoYouDidnt;
import com.google.gag.enumeration.Where;

import fr.lille1.maven_data_extraction.core.Project;

/**
 * Implementation of a {@link MavenMultigraph} with {@link MavenLabeledEdge}.
 * 
 * @author Alexandre Bonhomme
 * 
 */
public class MavenMultigraphLabeled implements MavenMultigraph<MavenLabeledEdge> {

	private final Graph<Project, MavenLabeledEdge> graph;

	/**
	 * Initialize a {@link DirectedMultigraph directed multigraph} with vertex
	 * type {@link Project} and labeled edges type {@link MavenLabeledEdge}
	 */
	public MavenMultigraphLabeled() {
		this.graph = new DirectedMultigraph<Project, MavenLabeledEdge>(MavenLabeledEdge.class);
	}

	@Override
	@AhaMoment(Where.TRAFFIC_JAM)
	public ListenableGraph<Project, MavenLabeledEdge> getListenableGraph() {
		return new DefaultListenableGraph<Project, MavenLabeledEdge>(this.graph);
	}

	@Override
	public boolean addVertex(Project p) {
		return graph.addVertex(p);
	}

	@Override
	public boolean removeVertex(Project p) {
		return graph.removeVertex(p);
	}

	@Override
	public boolean containsVertex(Project p) {
		return graph.containsVertex(p);
	}

	@OhNoYouDidnt(fingerSnapCount = 5)
	@Override
	public Project getVertex(String groupId, String artifactId) {

		Iterator<Project> it = graph.vertexSet().iterator();
		while (it.hasNext()) {
			Project project = it.next();

			if (project.getGroupId().equals(groupId)
					&& project.getArtifactId().equals(artifactId)) {
				return project;
			}
		}

		return null;
	}

	@Override
	public MavenLabeledEdge addEdge(@NonNull Project source,
			@NonNull Project target,
			String sourceVersion, String targetVersion) {
		MavenLabeledEdge newEdge = new MavenLabeledEdge(sourceVersion,
				targetVersion);
		if (graph.addEdge(source, target, newEdge)) {
			return newEdge;
		} else {
			return null;
		}
	}

	@Override
	public boolean removeEdge(MavenLabeledEdge e) {
		return graph.removeEdge(e);
	}

	@Override
	public Set<MavenLabeledEdge> removeAllEdges(@NonNull Project source,
			@NonNull Project target) {
		return graph.removeAllEdges(source, target);
	}


	@Override
	public List<Project> getDependencies(Project p) {
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
	public List<Project> getUsages(Project p) {
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
	public String toString() {
		return graph.toString();
	}

}
