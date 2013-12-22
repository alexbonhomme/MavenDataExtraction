package fr.lille1.maven_data_extraction.core;

import org.jgrapht.graph.DefaultEdge;

/**
 * Implementation of a labeled edge. The label is type of <code>T</code>
 * 
 * @author Alexandre Bonhomme
 * 
 * @param <T>
 */
public class LabeledEdge<T> extends DefaultEdge {

	private static final long serialVersionUID = -4102546139353062548L;
	
	protected final T label;

	/**
	 * @param label
	 */
	public LabeledEdge(T label) {
		this.label = label;
	}

	public T getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return "MavenEdge [label=" + label + "]";
	}
}
