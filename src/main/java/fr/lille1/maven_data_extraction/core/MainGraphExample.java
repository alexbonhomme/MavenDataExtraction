package fr.lille1.maven_data_extraction.core;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import fr.lille1.maven_data_extraction.Project;

public class MainGraphExample {

	public static void main(String[] args) {
		DirectedGraph<Project, DefaultEdge> g = GraphExample.getInstance();

		System.out.println(g.toString());
	}

}
