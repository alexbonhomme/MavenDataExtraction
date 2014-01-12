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

public class PomExtractionImpl implements PomExtraction {

	private File pom;
	private String groupId;
	private String artifactId;
	private String versionNumber;
	private List<Project> dependents;

	public PomExtractionImpl(File pom) {
		this.pom = pom;
		this.dependents = new ArrayList<Project>();
		try {
			extractData();
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}

	private void extractData() throws NullPointerException {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = (Document) builder.build(pom);
			Element rootNode = document.getRootElement();
			Namespace ns = rootNode.getNamespace();
			Element dependenciesNode = rootNode.getChild("dependencies", ns);
			
			groupId = rootNode.getChildText("groupId", ns);
			artifactId = rootNode.getChildText("artifactId", ns);
			versionNumber = rootNode.getChildText("version", ns);

			if (dependenciesNode == null) {
				return;
			}
			
			extractDependencies(ns, dependenciesNode);

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void extractDependencies(Namespace ns, Element dependenciesNode)
			throws NullPointerException {
		List<Element> listDependency = dependenciesNode.getChildren(
				"dependency", ns);
		String groupIdDep;
		String artifactIdDep;
		String versionNumberDep;

		for (Element dependencyNode : listDependency) {
			groupIdDep = dependencyNode.getChildText("groupId", ns);
			artifactIdDep = dependencyNode.getChildText("artifactId", ns);
			versionNumberDep = dependencyNode.getChildText("version", ns);

			if (groupIdDep == null || artifactIdDep == null
					|| versionNumberDep == null) {
				throw new NullPointerException(
						"This pom have depedency without groupId, arifactId or version : "
								+ pom.toString());
			}

			Project project = new Project(groupIdDep, artifactIdDep);
			Version version = new Version(versionNumberDep, null);

			project.addVersion(version);
			dependents.add(project);
		}
	}

	@Override
	public Project getProject() throws NullPointerException {
		if (groupId == null || artifactId == null) {
			throw new NullPointerException(
					"this pom haven't GroupId or ArtifiactId : "
							+ pom.toString());
		}
		return new Project(groupId, artifactId);

	}

	@Override
	public Version getVersion() throws NullPointerException {
		if (versionNumber == null) {
			throw new NullPointerException("this pom haven't version number : "
					+ pom.toString());
		}
		return new Version(versionNumber, pom, dependents);
	}

	@Override
	public String getGroupId() {
		return groupId;
	}

	@Override
	public String getArtifactId() {
		return artifactId;
	}

	@Override
	public String getNumberVersion() {
		return versionNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.lille1.maven_data_extraction.core.extraction.PomExtraction#getDependents
	 * ()
	 */
	@Override
	public List<Project> getDependencies() {
		return dependents;
	}

}
