package fr.lille1.maven_data_extraction.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a version for a {@link Project} object.
 * 
 * @author Alexandre Bonhomme
 * 
 */
public class Version {

	private final String versionNumber;
	private String parent;
	private List<Project> listDependencies; 

	/**
	 * @param version
	 */
	
	public Version(String versionNumber){
		this.versionNumber = versionNumber;
		this.parent = null;
		this.listDependencies = new ArrayList<Project>();
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	@Override
	public boolean equals(Object obj) {
		Version version = (Version) obj;

		if (version == null) {
			return false;
		}

		if (!version.getVersionNumber().equals(versionNumber)) {
			return false;
		}

		return true;
	}

	public List<Project> getDependencies() {
		return listDependencies;
	}

	public void setListDependencies(List<Project> listDependencies) {
		this.listDependencies = listDependencies;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public String getParentName() {
		return parent;
	}
	
	public void setParentName(String parent) {
		this.parent = parent;
	}

}
