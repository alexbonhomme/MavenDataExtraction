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
	private String parentGroupId;
	private String parentAritfactId;
	private List<Project> listDependencies; 

	/**
	 * @param version
	 */
	public Version(String versionNumber){
		this.versionNumber = versionNumber;
		this.listDependencies = new ArrayList<Project>();
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public List<Project> getDependencies() {
		return listDependencies;
	}

	public void setListDependencies(List<Project> listDependencies) {
		this.listDependencies = listDependencies;
	}

	public boolean hasParent() {
		return ((parentAritfactId != null) && (parentGroupId != null));
	}
	
	public String getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getParentAritfactId() {
		return parentAritfactId;
	}

	public void setParentAritfactId(String parentAritfactId) {
		this.parentAritfactId = parentAritfactId;
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

	@Override
	public String toString() {
		return "Verion: " + versionNumber
				+ (hasParent() ? " - Parent: " + parentGroupId + "." + parentAritfactId : "");
	}

}
