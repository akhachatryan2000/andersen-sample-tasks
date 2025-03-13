package org.andersen.dto;

import java.util.Arrays;

public class WeightedGraphDto {
    private int[][] weightedGraph;

    private int startNode;

    private int endNode;

    public int[][] getWeightedGraph() {
        return weightedGraph;
    }

    public int getStartNode() {
        return startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public void setWeightedGraph(int[][] weightedGraph) {
        this.weightedGraph = weightedGraph;
    }

    public void setStartNode(int startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }

    @Override
    public String toString() {
        return "WeightedGraphDto{" +
                "weightedGraph=" + Arrays.toString(weightedGraph) +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                '}';
    }
}
