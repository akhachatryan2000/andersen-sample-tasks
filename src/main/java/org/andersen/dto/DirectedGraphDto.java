package org.andersen.dto;

import java.util.Arrays;

public class DirectedGraphDto {
    private int[][] directedGraph;

    public int[][] getDirectedGraph() {
        return directedGraph;
    }

    @Override
    public String toString() {
        return "DirectedGraphDto{" +
                "directedGraph=" + Arrays.toString(directedGraph) +
                '}';
    }

    public void setDirectedGraph(final int[][] directedGraph) {
        this.directedGraph = directedGraph;
    }
}
