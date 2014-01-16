package fr.lille1.maven_data_extraction.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fr.lille1.maven_data_extraction.core.exceptions.MavenDataExtractionException;

public class Pom {

	private final File pomFile;
	private final String groupId;
	private final String artifactId;
	private String versionNumber;
	private String parentArtifactId;
	private List<Project> dependencies;

	private static final Logger log = Logger.getLogger(Pom.class);

	public Pom(File pom, String groupId, String artifactId, String versionNumber) {
		this.pomFile = pom;
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.versionNumber = versionNumber;
		this.parentArtifactId = null;
		this.dependencies = new ArrayList<Project>();

		testFieldContaint();
	}

	private void testFieldContaint() {
		if ((groupId == null) || (artifactId == null)) {
			throw new MavenDataExtractionException(
					"This pom haven't GroupId or ArtifiactId : "
							+ pomFile.toString());
		}
		
		if (versionNumber == null) {
			log.trace("Pom without version" + pomFile);
			versionNumber = "last";
		}
	}

	public Project createProject() {
		return new Project(groupId, artifactId);
	}

	public Version createVersion() {
		Version version = new Version(versionNumber);
		version.setListDependencies(dependencies);
		version.setParentAritfactId(parentArtifactId);
		version.setParentGroupId(groupId);
		return version;
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

	public String getParentArtifactId() {
		return parentArtifactId;
	}

	public void setParentArtifactId(String parentArtifactId) {
		this.parentArtifactId = parentArtifactId;
	}

	public boolean hasParent() {
		return parentArtifactId != null;
	}

	public List<Project> getDependents() {
		return dependencies;
	}

	public void setDependents(List<Project> dependents) {
		this.dependencies = dependents;
	}

	public void AddDependency(Project project) {
		dependencies.add(project);
	}
}
