package fr.lille1.maven_data_extraction.console;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.google.gag.annotation.disclaimer.AhaMoment;
import com.google.gag.enumeration.Where;

import fr.lille1.maven_data_extraction.core.extraction.MavenDataExtraction;
import fr.lille1.maven_data_extraction.core.extraction.MavenDataExtractionImpl;
import fr.lille1.maven_data_extraction.core.graph.MavenLabeledEdge;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraph;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraphFactory;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraphLabeled;

public class MainConsole {

	private final static Logger log = Logger.getLogger(MainConsole.class);

	@AhaMoment(Where.TOILET)
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("Usage : Main /path/to/pom/file");
		}

		File root = new File(args[0]);
		log.trace(root.getCanonicalPath());

		/*
		 * Graph creation
		 */
		MavenMultigraphFactory factory = new MavenMultigraphFactory(
				MavenMultigraphLabeled.class);
		MavenDataExtraction extractor = new MavenDataExtractionImpl(root);

		log.info("Starting graph creation...");
		MavenMultigraph<MavenLabeledEdge> graph = (MavenMultigraph<MavenLabeledEdge>) factory
				.build(extractor);

		/*
		 * Metrics
		 */
		MavenMetricsConsole metrics = new MavenMetricsConsoleJython(graph);

		/*
		 * Python console
		 */
		PySystemState.initialize();
		PythonInterpreter pyi = new PythonInterpreter();
		pyi.exec("from fr.lille1.maven_data_extraction.core import Project, Version");
		// pyi.exec("from fr.lille1.maven_data_extraction.core.metrics import MavenMetrics");
		pyi.set("Metrics", metrics);

		System.out.println("\n\nMaven Data Extraction");
		System.out.println("\nJython " + PySystemState.version);
		System.out.println("\nUse exit() or Ctrl-D (i.e. EOF) to exit");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print(">>> ");
			String cmd = scanner.nextLine();

			if("exit()".equals(cmd)) {
				scanner.close();
				System.out.println("Bye!");

				return;
			}
			
			// Jython interpreting
			try {
				pyi.exec(cmd);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
