package fr.lille1.maven_data_extraction.core;

import java.io.File;

public class Project {
	private final String groupId;
	private final String artifactId;
	private final String version;
	private final File pom;

	
	public Project(String groupId, String artifactId, String version, File pom){
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.pom = pom;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersion() {
		return version;
	}
	
	public File getPom() {
		return pom;
	}

	@Override
	public String toString() {
		return groupId + ":" + artifactId + ":" + version + ":" + pom.getPath();
	}

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

		if (!project.getPom().equals(pom)) {
			return false;
		}

		if (!project.getVersion().equals(version)) {
			return false;
		}

		return true;
	}
}
