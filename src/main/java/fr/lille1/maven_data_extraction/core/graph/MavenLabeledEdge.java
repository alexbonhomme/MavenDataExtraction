package fr.lille1.maven_data_extraction.core.graph;

import java.util.AbstractMap.SimpleEntry;

/**
 * Specification of {@link LabeledEdge}, this class uses {@link SimpleEntry
 * <String, String>} as label.
 * 
 * @author Alexandre Bonhomme
 * 
 */
public class MavenLabeledEdge extends LabeledEdge<SimpleEntry<String, String>> {

	private static final long serialVersionUID = -2541337682928682169L;

	/**
	 * 
	 * @param sourceVersion
	 * @param targetVersion
	 */
	public MavenLabeledEdge(String sourceVersion, String targetVersion) {
		super(new SimpleEntry<String, String>(sourceVersion, targetVersion));
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
		return "(" + getSourceVersion() + " -> " + getTargetVersion() + ")";
	}

}
