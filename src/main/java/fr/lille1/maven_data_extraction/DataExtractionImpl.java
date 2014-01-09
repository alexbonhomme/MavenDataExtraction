package fr.lille1.maven_data_extraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

public class DataExtractionImpl implements DataExtraction {

	public File folder;
	public Project project;
	public Version version;
	
	public DataExtractionImpl(Project project, Version version, File folder){
		this.folder = folder;
		this.project = project;
		this.version = version;
	}

	public DataExtractionImpl(File pom, File folder) throws JDOMException, IOException {
		this.folder = folder;
		project = getProject(pom);
		version = getVersion(pom);
		
		System.out.println(project.getGroupId());
		System.out.println(project.getArtifactId());
		System.out.println(version.getVersionNumber());
	}

	private Project getProject(File pom){
		String groupId;
		String artifactId;
		SAXBuilder builder = new SAXBuilder();

		try {
			Document document = (Document) builder.build(pom);
			Element rootNode = document.getRootElement();			
			
			groupId = rootNode.getChildText("groupId", rootNode.getNamespace());
			artifactId = rootNode.getChildText("artifactId", rootNode.getNamespace());
			
			if (groupId != null && artifactId != null){
				return new Project(groupId, artifactId);
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	private Version getVersion(File pom) {
		String versionNumber;
		SAXBuilder builder = new SAXBuilder();

		try {
			Document document = (Document) builder.build(pom);
			Element rootNode = document.getRootElement();
			
			versionNumber = rootNode.getChildText("version", rootNode.getNamespace());
			
			if (versionNumber != null){
				return new Version(versionNumber, pom);
			}
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
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

	public Project getDependent(File xmlFile) throws XPathExpressionException{
		


		
		
		return null;
	}
}
