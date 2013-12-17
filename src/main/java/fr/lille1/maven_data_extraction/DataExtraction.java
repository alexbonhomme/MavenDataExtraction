package fr.lille1.maven_data_extraction;

import java.io.File;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

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
	Project getDependent(File file)	throws XPathExpressionException;

}
