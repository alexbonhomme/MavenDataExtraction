package fr.lille1.maven_data_extraction;

import java.io.File;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import fr.lille1.maven_data_extraction.core.Project;
import fr.lille1.maven_data_extraction.core.Version;

public interface DataExtraction {

	/**
	 * @param folder
	 * @return list of files ".pom" contained in folder
	 */
	List<File> findPom(File folder);

	/**
	 * @param file
	 * @return
	 * @throws XPathExpressionException
	 */
	Version getDependent(File file);
	
	List<Version> getAllDependent();

}
