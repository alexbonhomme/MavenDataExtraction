package fr.lille1.maven_data_extraction;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.google.gag.annotation.disclaimer.AhaMoment;
import com.google.gag.enumeration.Where;

import fr.lille1.maven_data_extraction.core.extraction.DataExtraction;
import fr.lille1.maven_data_extraction.core.extraction.DataExtractionImpl;
import fr.lille1.maven_data_extraction.core.graph.MavenLabeledEdge;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraph;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraphFactory;
import fr.lille1.maven_data_extraction.core.graph.MavenMultigraphLabeled;

public class Main {

	private final static Logger log = Logger.getLogger(Main.class);

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
		DataExtraction extractor = new DataExtractionImpl(root);

		log.info("Starting graph creation...");
		MavenMultigraph<MavenLabeledEdge> graph = (MavenMultigraph<MavenLabeledEdge>) factory
				.build(extractor);

		/*
		 * Console running
		 */
		System.out.println("###############################");
		System.out.println("#");
		System.out.println("# Maven Data Extraction");
		System.out.println("#");
		System.out.println("###############################");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String cmd = scanner.nextLine();

			String[] cmdTab = cmd.split(" ");
			switch (cmdTab[0]) {
			case "stats":
				if (cmdTab.length < 2) {
					System.out.println("Usage : stats groupIp.artifactId");
					continue;
				}

				System.out.println("Statistics for " + cmdTab[1] + " :");
				break;

			case "exit":
				scanner.close();
				System.out.println("Bye!");
				return;

			default:
				System.out.println("Command not found.");
				break;
			}
		}
	}

}
