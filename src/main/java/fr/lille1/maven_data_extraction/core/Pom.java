package fr.lille1.maven_data_extraction.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Pom {

	private final File pomFile;
	private final String groupId;
	private final String artifactId;
	private final String versionNumber;
	private List<Project> dependents;

	private static final Logger log = Logger
			.getLogger(Pom.class);
	
	public Pom(File pom, String groupId, String artifactId, String versionNumber) {
		this.pomFile = pom;
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.versionNumber = versionNumber;
		this.dependents = new ArrayList<Project>();
	}

	public Project createProject() {
		if ((groupId == null) || (artifactId == null)) {
			throw new NullPointerException(
					"This pom haven't GroupId or ArtifiactId : "
							+ pomFile.toString());
		}
		return new Project(groupId, artifactId);
	}

	public Version createVersion() {
		if (versionNumber == null) {
			log.debug("Pom without version" + pomFile);
			return new Version("last", pomFile, dependents);
		}
		return new Version(versionNumber, pomFile, dependents);
	}

	public File getPomFile() {
		return pomFile;
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
