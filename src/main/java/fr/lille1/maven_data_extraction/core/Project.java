package fr.lille1.maven_data_extraction.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gag.annotation.remark.Hack;

public class Project {

	private final String groupId;
	private final String artifactId;
	private final Map<String, Version> versionsMap;
	
	/**
	 * 
	 * @param groupId
	 * @param artifactId
	 */
	public Project(String groupId, String artifactId) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.versionsMap = new HashMap<String, Version>();
	}

	/**
	 * 
	 * @param groupId
	 * @param artifactId
	 * @param versionsList
	 */
	public Project(String groupId, String artifactId,
			Map<String, Version> versionsList) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.versionsMap = versionsList;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * Add a {@link Version} to the {@link Project}. If a {@link Version
	 * version} with the same version number exists that replace the all value.
	 * 
	 * @return The previous {@link Version} associate with the given version
	 *         number or {@link null} if the version does not already exists.
	 */
	public Version addVersion(Version v) {
		return versionsMap.put(v.getVersionNumber(), v);
	}

	/**
	 * Remove the version with the given <code>versionNumber</code>.
	 * 
	 * @return The version associate to the <code>versionNumber</code>, or
	 *         {@link null} if no {@link Version} are found
	 */
	public Version removeVersion(String versionNumber) {
		return versionsMap.remove(versionNumber);
	}

	/**
	 * Return the {@link Version} object associate to the given
	 * <code>versionNumber</code>, or {@link null} if no {@link Version} could
	 * be found.
	 */
	public Version getVersion(String versionNumber) {
		return versionsMap.get(versionNumber);
	}

	/**
	 * Return an {@link Iterator} over the {@link Version} set.
	 */
	public Iterator<Version> getVersionsIterator() {
		return versionsMap.values().iterator();
	}

	/**
	 * Return the size of the {@link Version} set.
	 */
	public int getVersionsSize() {
		return versionsMap.size();
	}

	@Override
	public String toString() {
		return groupId + "." + artifactId;
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
