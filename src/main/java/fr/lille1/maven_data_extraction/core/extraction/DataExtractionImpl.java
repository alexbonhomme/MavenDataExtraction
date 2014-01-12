package fr.lille1.maven_data_extraction.core.extraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import fr.lille1.maven_data_extraction.core.Pom;
import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

public class DataExtractionImpl implements DataExtraction {

	private final File folder;
	private final HashMap<String, Project> projectMap;

	private static final Logger log = Logger
			.getLogger(DataExtractionImpl.class);

	public DataExtractionImpl(File folder) {
		this.folder = folder;
		this.projectMap = new HashMap<String, Project>();
	}

	@Override
	public List<File> findPom(File folder) {
		List<File> listFile = new ArrayList<File>();

		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				listFile.addAll(findPom(file));
			} else {
				listFile.add(file);
			}
		}
		return listFile;
	}

	@Override
	public HashMap<String, Project> getAllProject() {
		List<File> listPom = findPom(folder);

		for (File pom : listPom) {
			addProject(pom);
		}
		return projectMap;
	}

	private void addProject(File pomFile) throws NullPointerException {

		try {
			Pom pom = pomExtract(pomFile);
			Project project = pom.createProject();
			Version version = pom.createVersion();
			String keyProject = project.getGroupId() + "."
					+ project.getArtifactId();

			if (projectMap.containsKey(keyProject)) {
				project = projectMap.get(keyProject);
				project.addVersion(version);
			} else {
				project.addVersion(version);
				projectMap.put(keyProject, project);
			}
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}

	private Pom pomExtract(File pomFile) throws NullPointerException {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(pomFile);
			Element rootNode = document.getRootElement();
			Namespace ns = rootNode.getNamespace();
			Element dependenciesNode = rootNode.getChild("dependencies", ns);
			
			String groupId = rootNode.getChildText("groupId", ns);
			String artifactId = rootNode.getChildText("artifactId", ns);
			String versionNumber = rootNode.getChildText("version", ns);
			
			//If groupId is null, we take that of its parent
			if (groupId == null) {
				
			Element parent = rootNode.getChild("parent", ns);
				if (parent == null) {
					throw new NullPointerException("pom without GroupId : " + pomFile);
				}
				
				Element groupIdParent = parent.getChild("groupId", ns);
				if (groupIdParent == null) {
					throw new NullPointerException("pom without GroupId : " + pomFile);
				}
				
				groupId = groupIdParent.getText();
			}

			Pom pom = new Pom(pomFile, groupId, artifactId, versionNumber);
			
			if (dependenciesNode == null) {
				return pom;
			}
			
			try {
				pom.setDependents(extractDependencies(pomFile, ns, dependenciesNode));
			} catch (NullPointerException e) {
				System.err.println(e.getMessage());
			}
			
			return pom;

		} catch (JDOMException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	private List<Project> extractDependencies(File pomFile, Namespace ns,
			Element dependenciesNode) throws NullPointerException {
		List<Element> listDependency = dependenciesNode.getChildren(
				"dependency", ns);
		List<Project> dependencies = new ArrayList<Project>();
		String groupIdDep;
		String artifactIdDep;
		String versionNumberDep;

		for (Element dependencyNode : listDependency) {
			groupIdDep = dependencyNode.getChildText("groupId", ns);
			artifactIdDep = dependencyNode.getChildText("artifactId", ns);
			versionNumberDep = dependencyNode.getChildText("version", ns);

			if ((groupIdDep == null) || (artifactIdDep == null)) {
				throw new NullPointerException(
						"This pom have depedency without groupId or arifactId  : "
								+ pomFile.toString());
			}

			if (versionNumberDep == null) {
				versionNumberDep = "last";
				log.debug("dependency without version : " + pomFile);
			}

			Project project = new Project(groupIdDep, artifactIdDep);
			Version version = new Version(versionNumberDep, null);

			project.addVersion(version);
			dependencies.add(project);
		}
		return dependencies;
	}

}
