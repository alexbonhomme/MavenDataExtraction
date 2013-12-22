package fr.lille1.maven_data_extraction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.lille1.maven_data_extraction.core.Project;

public class DataExtractionImpl implements DataExtraction {

	public File folder;
	public Project projet;

	public DataExtractionImpl(File folder, Project projet) {
		this.folder = folder;
		this.projet = projet;
	}


	/* (non-Javadoc)
	 * @see fr.lille1.maven_data_extraction.DataExtraction#findPom(java.io.File)
	 */
	public List<File> findPom(File folder) {
		List<File> listFile =  new ArrayList<File>();
		
		File[] files = folder.listFiles();
		for (File file : files){
			if (file.isDirectory()){
				listFile.addAll(findPom(file));
			} else {
				listFile.add(file);
			}
		}
		return listFile;
	}

	
	/* (non-Javadoc)
	 * @see fr.lille1.maven_data_extraction.DataExtraction#getDependent(java.io.File)
	 */
	public Project getDependent(File file) throws XPathExpressionException{
		Project projet = null;
		boolean hasParent = false;
		
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile("//project/*");
		
		
		Object result = expr.evaluate(file, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item( i );
			String nodeName = node.getNodeName();
			String nodeValue = node.getChildNodes().item( 0 ).getNodeValue();
			
			if( nodeName.equals( "parent" ) ) {
				hasParent = true;
    }
		}
		
		
		return projet;
	}

}
