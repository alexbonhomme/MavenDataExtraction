package fr.lille1.maven_data_extraction.core;

import java.util.ArrayList;
import java.util.List;

import com.google.gag.annotation.remark.Hack;

public class Project {

	private final String groupId;
	private final String artifactId;
	private final List<Version> versionsList;
	
	/**
	 * 
	 * @param groupId
	 * @param artifactId
	 */
	public Project(String groupId, String artifactId) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.versionsList = new ArrayList<Version>();
	}

	/**
	 * 
	 * @param groupId
	 * @param artifactId
	 * @param versionsList
	 */
	public Project(String groupId, String artifactId, List<Version> versionsList) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.versionsList = versionsList;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public boolean addVersion(Version v) {
		return versionsList.add(v);
	}

	public boolean removeVersion(Version v) {
		return versionsList.remove(v);
	}

	public List<Version> getVersionsList() {
		return versionsList;
	}

	@Override
	public String toString() {
		return groupId + ":" + artifactId;
	}

	@Hack(value = "do not compare the versionsList")
	@Override
	public boolean equals(Object obj) {
		Project project = (Project) obj;

		if (project == null) {
			return false;
		}

		if (!project.getArtifactId().equals(artifactId)) {
			return false;
		}

		if (!project.getGroupId().equals(groupId)) {
			return false;
		}

		return true;
	}
}
