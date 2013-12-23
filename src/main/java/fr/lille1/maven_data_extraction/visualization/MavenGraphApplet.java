package fr.lille1.maven_data_extraction.visualization;

import java.awt.Dimension;

import javax.swing.JApplet;

import org.jgrapht.ext.JGraphXAdapter;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;

import fr.lille1.maven_data_extraction.core.MavenLabeledEdge;
import fr.lille1.maven_data_extraction.core.MavenMultigraph;
import fr.lille1.maven_data_extraction.core.Project;

/**
 * 
 * @author Alexandre Bonhomme
 * 
 */
public class MavenGraphApplet extends JApplet {

	private static final long serialVersionUID = -5809860607499098885L;
	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	private JGraphXAdapter<Project, MavenLabeledEdge> adapter;
	private final MavenMultigraph<MavenLabeledEdge> graph;

	/**
	 * @param graph
	 */
	public MavenGraphApplet(MavenMultigraph<MavenLabeledEdge> graph) {
		this.graph = graph;
	}

	@Override
	public void init() {
		// create a visualization using JGraph, via an adapter
		adapter = new JGraphXAdapter<Project, MavenLabeledEdge>(
				graph.getListenableGraph());

		getContentPane().add(new mxGraphComponent(adapter));
		resize(DEFAULT_SIZE);

		// positioning via jgraphx layouts
		mxCircleLayout layout = new mxCircleLayout(adapter);
		layout.execute(adapter.getDefaultParent());

	}

}
