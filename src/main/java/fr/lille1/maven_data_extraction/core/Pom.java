package fr.lille1.maven_data_extraction.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Pom {

	private final File pom;
	private final String groupId;
	private final String artifactId;
	private final String versionNumber;
	private List<Project> dependents;

	public Pom(File pom, String groupId, String artifactId, String versionNumber) {
		this.pom = pom;
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.versionNumber = versionNumber;
		this.dependents = new ArrayList<Project>();
	}

	public Project getProject() throws NullPointerException {
		if ((groupId == null) || (artifactId == null)) {
			throw new NullPointerException(
					"this pom haven't GroupId or ArtifiactId : "
							+ pom.toString());
		}
		return new Project(groupId, artifactId);
	}

	public Version getVersion() throws NullPointerException {
		if (versionNumber == null) {
			throw new NullPointerException("this pom haven't version number : "
					+ pom.toString());
		}
		return new Version(versionNumber, pom, dependents);
	}

	public File getPom() {
		return pom;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public List<Project> getDependents() {
		return dependents;
	}
	
	public void setDependents(List<Project> dependents) {
		this.dependents = dependents;
	}
	
	public void AddDependency(Project project) {
		dependents.add(project);
	}
}
