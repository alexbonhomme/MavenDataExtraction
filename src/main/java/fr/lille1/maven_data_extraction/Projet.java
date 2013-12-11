package fr.lille1.maven_data_extraction;

import java.io.File;

public class Projet {
	private String groupId;
	private String artifactId;
	private String version;
	private File pom;

	public Projet(String groupId, String artifactId, String version, File pom){
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.pom = pom;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public File getPom() {
		return pom;
	}

	public void setPom(File pom) {
		this.pom = pom;
	}


}
