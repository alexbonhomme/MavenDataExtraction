package fr.lille1.maven_data_extraction.visualization;

import java.io.File;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import fr.lille1.maven_data_extraction.core.extraction.MavenDataExtraction;
import fr.lille1.maven_data_extraction.core.extraction.MavenDataExtractionImpl;
import fr.lille1.maven_data_extraction.core.graph.MavenLabeledEdge;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraph;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraphFactory;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraphLabeled;

/**
 * @author Alexandre Bonhomme
 *
 */
public class MainApplet {
	private final static Logger log = Logger.getLogger(MainApplet.class);

	private final static File root = new File("src/test/resources/asia");
	private final static File root_big = new File("../download/org/apache");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * Graph creation
		 */
		MavenMultigraphFactory factory = new MavenMultigraphFactory(
				MavenMultigraphLabeled.class);
		MavenDataExtraction extractor = new MavenDataExtractionImpl(root_big);

		log.info("Starting graph creation...");
		MavenMultigraph<MavenLabeledEdge> graph = (MavenMultigraph<MavenLabeledEdge>) factory
				.build(extractor);

		/*
		 * Applet creation
		 */
		log.info("Starting graph visualization...");
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
