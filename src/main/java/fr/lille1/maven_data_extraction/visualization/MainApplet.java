package fr.lille1.maven_data_extraction.visualization;

import java.io.File;

import javax.swing.JFrame;

import fr.lille1.maven_data_extraction.core.MavenLabeledEdge;
import fr.lille1.maven_data_extraction.core.MavenMultigraph;
import fr.lille1.maven_data_extraction.core.MavenMultigraphLabeled;
import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

/**
 * @author Alexandre Bonhomme
 *
 */
public class MainApplet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Graph creation
		 */
		MavenMultigraph<MavenLabeledEdge> graph = new MavenMultigraphLabeled();
		
		Project p1 = new Project("org.apache.abdera", "abdera");
		p1.addVersion(new Version("1.0", new File("")));
		p1.addVersion(new Version("1.1.1", new File("")));

		Project p2 = new Project("org.apache.accumulo", "accumulo-core");
		p2.addVersion(new Version("1.3.6", new File("")));

		Project p3 = new Project("org.apache.ace", "ace-pom");
		p3.addVersion(new Version("0.8.0-incubator", new File("")));
		p3.addVersion(new Version("0.8.1-incubator", new File("")));

		// add the vertices
		graph.addVertex(p1);
		graph.addVertex(p2);
		graph.addVertex(p3);

		// p1.v1 -> p2.v1
		graph.addEdge(p1, p2, p1.getVersion("1.0").getVersionNumber(), p2
				.getVersion("1.3.6").getVersionNumber());
		// p1.v1 -> p2.v1
		graph.addEdge(p1, p2, p1.getVersion("1.1.1").getVersionNumber(), p2
				.getVersion("1.3.6").getVersionNumber());
		// p1.v1 -> p3.v1
		graph.addEdge(p1, p3, p1.getVersion("1.0").getVersionNumber(), p3
				.getVersion("0.8.0-incubator").getVersionNumber());
		// p2.v1 -> p3.v2
		graph.addEdge(p2, p3, p2.getVersion("1.3.6").getVersionNumber(), p3
				.getVersion("0.8.1-incubator").getVersionNumber());

		/*
		 * Applet creation
		 */
		MavenGraphApplet applet = new MavenGraphApplet(graph);
		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
