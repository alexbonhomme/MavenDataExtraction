package fr.lille1.maven_data_extraction.core.extraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

public class PomExtraction {

	private File pom;
	private String groupId;
	private String artifactId;
	private String versionNumber;
	private List<Project> dependents;

	public PomExtraction(File pom) {
		this.pom = pom;
		this.dependents = new ArrayList<Project>();
		extractData();
	}

	private void extractData() {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = (Document) builder.build(pom);
			Element rootNode = document.getRootElement();
			Namespace ns = rootNode.getNamespace();
			Element dependenciesNode = rootNode.getChild("dependencies", ns);

			groupId = rootNode.getChildText("groupId", ns);
			artifactId = rootNode.getChildText("artifactId", ns);
			versionNumber = rootNode.getChildText("version", ns);

			if (dependenciesNode == null){
				return;
			}
			List<Element> listDependency = dependenciesNode.getChildren("dependency", ns);
			String groupIdDep;
			String artifactIdDep;
			String versionNumberDep;

			for (Element dependencyNode : listDependency) {
				groupIdDep = dependencyNode.getChildText("groupId", ns);
				artifactIdDep = dependencyNode.getChildText("artifactId", ns);
				versionNumberDep = dependencyNode.getChildText("version", ns);

				Project project = new Project(groupIdDep, artifactIdDep);
				Version version = new Version(versionNumberDep, null);

				if (version != null) {
					project.addVersion(version);
					dependents.add(project);
				}
			}

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Project getProject() {
		if (groupId != null && artifactId != null) {
			return new Project(groupId, artifactId);
		}
		return null;
	}

	public Version getVersion() {
		if (versionNumber != null) {
			return new Version(versionNumber, pom, dependents);
		}
		return null;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getNumberVersion() {
		return versionNumber;
	}

	public List<Project> getDependents() {
		return dependents;
	}

}
