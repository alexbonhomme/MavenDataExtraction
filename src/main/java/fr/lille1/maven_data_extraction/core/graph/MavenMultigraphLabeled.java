package fr.lille1.maven_data_extraction.core.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.NonNull;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;

import com.google.gag.annotation.disclaimer.AhaMoment;
import com.google.gag.annotation.disclaimer.CarbonFootprint;
import com.google.gag.annotation.remark.LOL;
import com.google.gag.enumeration.CO2Units;
import com.google.gag.enumeration.Where;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.exceptions.MavenGraphException;

/**
 * Implementation of a {@link MavenMultigraph} with {@link MavenLabeledEdge}.
 * 
 * @author Alexandre Bonhomme
 * 
 */
public class MavenMultigraphLabeled implements MavenMultigraph<MavenLabeledEdge> {

	private final Graph<Project, MavenLabeledEdge> graph;

	@LOL
	private final Map<String, Project> values;

	/**
	 * Initialize a {@link DirectedMultigraph directed multigraph} with vertex
	 * type {@link Project} and labeled edges type {@link MavenLabeledEdge}
	 */
	public MavenMultigraphLabeled() {
		this.graph = new DirectedMultigraph<Project, MavenLabeledEdge>(MavenLabeledEdge.class);
		this.values = new HashMap<String, Project>();
	}

	@Override
	@AhaMoment(Where.TRAFFIC_JAM)
	public ListenableGraph<Project, MavenLabeledEdge> getListenableGraph() {
		return new DefaultListenableGraph<Project, MavenLabeledEdge>(this.graph);
	}

	@Override
	public boolean addVertex(@NonNull Project p) {
		values.put(p.getFullName(), p);
		return graph.addVertex(p);
	}

	@Override
	public boolean removeVertex(@NonNull Project p) {
		values.remove(p.getFullName());
		return graph.removeVertex(p);
	}

	@Override
	public boolean containsVertex(@NonNull Project p) {
		return values.containsKey(p.toString());
	}

	@CarbonFootprint(value = 1, units = CO2Units.GRAMS_PER_MEGAJOULE)
	@Override
	public Project getVertex(String groupId, String artifactId) {
		return values.get(groupId + "." + artifactId);
	}

	@Override
	public Collection<Project> getAllVertices() {
		return graph.vertexSet();
	}

	@Override
	public boolean addEdge(@NonNull Project source,
			@NonNull Project target,
			String sourceVersion, String targetVersion) {
		MavenLabeledEdge newEdge = new MavenLabeledEdge(sourceVersion,
				targetVersion);

		Project realSource = values.get(source.getFullName());
		Project realTarget = values.get(target.getFullName());

		try {
			return graph.addEdge(realSource, realTarget, newEdge);
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new MavenGraphException(
					"Source or target project not found in the graph.", e);
		}
	}

	@Override
	public Set<MavenLabeledEdge> edgesOf(Project p) {
		try {
			return graph.edgesOf(values.get(p.getFullName()));
		} catch (NullPointerException | IllegalArgumentException e) {
			throw new MavenGraphException("Project not found in the graph.", e);
		}
	}

	@Override
	public Project getEdgeSource(MavenLabeledEdge e) {
		return graph.getEdgeSource(e);
	}

	@Override
	public Project getEdgeTarget(MavenLabeledEdge e) {
		return graph.getEdgeTarget(e);
	}

	@Override
	public boolean removeEdge(MavenLabeledEdge e) {
		return graph.removeEdge(e);
	}

	@Override
	public Set<MavenLabeledEdge> removeAllEdges(Project source, Project target) {
		Project realSource = values.get(source.getFullName());
		Project realTarget = values.get(target.getFullName());

		return graph.removeAllEdges(realSource, realTarget);
	}

	@Override
	public String toString() {
		return graph.toString();
	}

}
