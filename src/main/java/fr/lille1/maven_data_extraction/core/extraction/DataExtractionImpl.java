package fr.lille1.maven_data_extraction.core.extraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

public class DataExtractionImpl implements DataExtraction {

	private File folder;
	private HashMap<String, Project> projectMap;

	public DataExtractionImpl(File folder) {
		this.folder = folder;
		this.projectMap = new HashMap<String, Project>();
	}

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

	private void addProject(File pom) throws NullPointerException {
		PomExtraction pomExtraction = new PomExtractionImpl(pom);

		try {
			Project project = pomExtraction.getProject();
			Version version = pomExtraction.getVersion();
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

	public HashMap<String, Project> getAllProject() {
		List<File> listPom = findPom(folder);

		for (File pom : listPom) {
			addProject(pom);
		}
		return projectMap;
	}
}
