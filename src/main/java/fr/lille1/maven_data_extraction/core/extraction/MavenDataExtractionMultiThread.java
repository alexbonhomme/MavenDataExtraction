package fr.lille1.maven_data_extraction.core.extraction;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.IllegalNameException;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import fr.lille1.maven_data_extraction.core.Pom;
import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;
import fr.lille1.maven_data_extraction.core.exceptions.MavenDataExtractionException;

/**
 * 
 * @author Clément Dufour, Alexandre Bonhomme
 * 
 */
public class MavenDataExtractionMultiThread implements MavenDataExtraction {

	private final File root;
	private final ConcurrentHashMap<String, Project> projectMap;

	private static final Logger log = Logger
			.getLogger(MavenDataExtractionMultiThread.class);

	public MavenDataExtractionMultiThread(File root) {
		this.root = root;
		this.projectMap = new ConcurrentHashMap<String, Project>();
	}

	@Override
	public Collection<Project> computeAllProjects() {
		processPomFile(root);

		return projectMap.values();
	}

	private void processPomFile(File folder) {
		File[] files = folder.listFiles();
		if (files == null) {
			return;
		}

		for (final File f : files) {
			if (f.isDirectory()) {
				processPomFile(f);
			} else {
				log.trace("Processing " + f);
				new Thread(new Runnable() {

					@Override
					public void run() {
						addProject(f);
					}
				}).start();
			}
		}
	}

	private void addProject(File pomFile) {
		try {
			Pom pom = pomExtract(pomFile);
			Project project = pom.createProject();
			Version version = pom.createVersion();
			String keyProject = project.getFullName();

			if (projectMap.containsKey(keyProject)) {
				project = projectMap.get(keyProject);
				project.addVersion(version);
			} else {
				project.addVersion(version);
				projectMap.put(keyProject, project);
			}
		} catch (MavenDataExtractionException e) {
			log.debug(e.getLocalizedMessage());
		}
	}

	private Pom pomExtract(File pomFile) {
		SAXBuilder builder = new SAXBuilder();

		try {
			Document document = builder.build(pomFile);

			Element rootNode = document.getRootElement();
			Namespace ns = rootNode.getNamespace();
			Element dependenciesNode = rootNode.getChild("dependencies", ns);
			Element parent = rootNode.getChild("parent", ns);

			String groupId = rootNode.getChildText("groupId", ns);
			String artifactId = rootNode.getChildText("artifactId", ns);
			String versionNumber = rootNode.getChildText("version", ns);

			// If groupId is null, we take that of its parent
			if (groupId == null) {
				if (parent == null) {
					throw new MavenDataExtractionException(
							"pom without GroupId : "
							+ pomFile);
				}

				Element groupIdParent = parent.getChild("groupId", ns);
				if (groupIdParent == null) {
					throw new MavenDataExtractionException(
							"pom without GroupId : "
							+ pomFile);
				}

				groupId = groupIdParent.getText();
			}

			Pom pom = new Pom(pomFile, groupId, artifactId, versionNumber);

			if (parent != null) {
				pom.setParentArtifactId(parent.getChildText("artifactId", ns));
			}

			if (dependenciesNode == null) {
				return pom;
			}

			extractDependencies(pom, ns, dependenciesNode);
			return pom;

		} catch (JDOMException | IllegalNameException e) {
			throw new MavenDataExtractionException("JDOM Exception on file : "
					+ pomFile, e);
		} catch (IOException e) {
			throw new MavenDataExtractionException("I/O Exception on file : "
					+ pomFile, e);
		} catch (MavenDataExtractionException e) {
			throw new MavenDataExtractionException(e);
		}
	}

	private void extractDependencies(Pom pom, Namespace ns,
			Element dependenciesNode) {
		List<Element> listDependency = dependenciesNode.getChildren(
				"dependency", ns);
		String groupIdDep;
		String artifactIdDep;
		String versionNumberDep;

		for (Element dependencyNode : listDependency) {
			groupIdDep = dependencyNode.getChildText("groupId", ns);
			artifactIdDep = dependencyNode.getChildText("artifactId", ns);
			versionNumberDep = dependencyNode.getChildText("version", ns);

			if ((groupIdDep == null) || (artifactIdDep == null)) {
				throw new MavenDataExtractionException(
						"This pom have depedency without groupId or arifactId  : "
								+ pom.getPomFile().toString());
			}

			if (versionNumberDep == null) {
				versionNumberDep = "last";
				log.trace("Dependency without version : "
						+ pom.getPomFile().toString());
			}

			if (pom.getGroupId().equals(groupIdDep)
					&& pom.getArtifactId().equals(artifactIdDep)) {
				throw new MavenDataExtractionException(
						"Pom is dependent of himself : "
						+ pom.getPomFile().toString());
			}

			Project project = new Project(groupIdDep, artifactIdDep);
			Version version = new Version(versionNumberDep);

			project.addVersion(version);
			pom.AddDependency(project);
		}
	}

}
