package fr.lille1.maven_data_extraction.core;

import java.util.AbstractMap.SimpleEntry;

public class MavenLabeledEdge extends LabeledEdge<SimpleEntry<String, String>> {

	private static final long serialVersionUID = -2541337682928682169L;

	public MavenLabeledEdge(String sourceVersion, String targetVersion) {
		super(new SimpleEntry<>(sourceVersion, targetVersion));
	}

	/**
	 * Return the version number of the source project.
	 */
	public String getSourceVersion() {
		return label.getKey();
	}

	/**
	 * Return the version number of the target project.
	 */
	public String getTargetVersion() {
		return label.getValue();
	}

	@Override
	public String toString() {
		return "(" + getSourceVersion() + ", " + getTargetVersion() + ")";
	}

}
