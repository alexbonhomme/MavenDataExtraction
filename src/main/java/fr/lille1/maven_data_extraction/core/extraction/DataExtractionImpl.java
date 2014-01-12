package fr.lille1.maven_data_extraction.core.extraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
			Project project = pom.getProject();
			Version version = pom.getVersion();
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

			Pom pom = new Pom(pomFile, groupId, artifactId, versionNumber);
			
			if (dependenciesNode == null) {
				return pom;
			}
			
			pom.setDependents(extractDependencies(pomFile, ns, dependenciesNode));
			return pom;

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private List<Project> extractDependencies(File pomFile, Namespace ns, Element dependenciesNode)
			throws NullPointerException {
		List<Element> listDependency = dependenciesNode.getChildren(
				"dependency", ns);
		List<Project>dependencies = new ArrayList<Project>();
		String groupIdDep;
		String artifactIdDep;
		String versionNumberDep;

		for (Element dependencyNode : listDependency) {
			groupIdDep = dependencyNode.getChildText("groupId", ns);
			artifactIdDep = dependencyNode.getChildText("artifactId", ns);
			versionNumberDep = dependencyNode.getChildText("version", ns);

			if ((groupIdDep == null) || (artifactIdDep == null)
					|| (versionNumberDep == null)) {
				throw new NullPointerException(
						"This pom have depedency without groupId, arifactId or version : "
								+ pomFile.toString());
			}

			Project project = new Project(groupIdDep, artifactIdDep);
			Version version = new Version(versionNumberDep, null);

			project.addVersion(version);
			dependencies.add(project);
		}
		return dependencies;
	}


}
