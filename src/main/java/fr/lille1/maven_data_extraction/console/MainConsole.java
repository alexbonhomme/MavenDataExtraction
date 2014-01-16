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
import fr.lille1.maven_data_extraction.core.extraction.MavenDataExtractionMultiThread;
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

		long startCounter = System.currentTimeMillis();

		/*
		 * Graph creation
		 */
		MavenMultigraphFactory factory = new MavenMultigraphFactory(
				MavenMultigraphLabeled.class);
		MavenDataExtraction extractor = new MavenDataExtractionMultiThread(root);

		log.info("Starting graph creation...");
		MavenMultigraph<MavenLabeledEdge> graph = (MavenMultigraph<MavenLabeledEdge>) factory
				.build(extractor);
		
		long processingTimeMillis = System.currentTimeMillis() - startCounter;

		/*
		 * Python console
		 */
		PySystemState.initialize();
		PythonInterpreter pyi = new PythonInterpreter();
		pyi.exec("from fr.lille1.maven_data_extraction.core import Project, Version");

		pyi.set("MavenGraph", graph);
		pyi.set("Metrics", new MavenMetricsConsoleJython(graph));

		System.out.println("\n\nMaven Data Extraction");
		System.out.println("Processing time: " + processingTimeMillis / 1000
				+ " second(s)");
		long memoryUsedMb = (Runtime.getRuntime().totalMemory() - Runtime
				.getRuntime().freeMemory()) / (1024 * 1024);
		System.out.println("Used Memory: " + memoryUsedMb + " MB");

		System.out.println("\nJython " + PySystemState.version);
		System.out.println("Use exit() or Ctrl-D (i.e. EOF) to exit");
		System.out.println("\nObjects available:");
		System.out.println("\n\tMavenGraph");
		System.out
				.println("\n\tMetrics"
						+ "\n\t\tprintAllStats()"
						+ "\n\t\tprintStatsOf(String groupId, String artifactId)"
						+ "\n\t\tdependenciesOf(Project p)"
						+ "\n\t\tdependenciesOf(String groupId, String artifactId)"
						+ "\n\t\tdependenciesOf(Project p, Version v)"
						+ "\n\t\tdependenciesOf(String groupId, String artifactId, String versionNumber)"
						+ "\n\t\tusagesOf(Project p)"
						+ "\n\t\tusagesOf(String groupId, String artifactId)"
						+ "\n\t\tusagesOf(Project p, Version v)"
						+ "\n\t\tusagesOf(String groupId, String artifactId, String versionNumber)"
						+ "\n\t\tconfidenceOf(Project p)"
						+ "\n\t\tconfidenceOf(String groupId, String artifactId)");

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
