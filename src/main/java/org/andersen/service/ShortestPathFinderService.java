package org.andersen.service;

import java.util.*;

/**
 * A service to find the shortest path between two nodes using Dijkstra's algorithm.
 * The input graph is weighted, directed graph.
 * Time: O((V+E) logV)
 * Space: O(E+V)
 */
public class ShortestPathFinderService {

    /**
     * Finds the shortest path between two nodes in a weighted, directed graph.
     *
     * @param weightedGraph A 2D array representing the graph, where each row is an edge
     *                      in the format {sourceNode, destinationNode, weight}.
     * @param startNode     The starting node for the path.
     * @param endNode       The destination node for the path.
     * @return A list of node IDs representing the shortest path, or an empty list if no path exists.
     */
    public List<Integer> findShortestPath(int[][] weightedGraph, int startNode, int endNode) {
        // Build adjacency list from the weighted graph
        // Time: O(E) - iterate through all edges once
        // Space: O(V + E) - store all nodes and edges
        Map<Integer, List<int[]>> adjList = new HashMap<>();
        for (int[] edge : weightedGraph) {
            adjList.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new int[]{edge[1], edge[2]});
        }

        // Initialize distances and previous nodes
        // Time: O(V) - initialize V maps.
        // Space: O(V) - store distances and previous nodes.
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previousNodes = new HashMap<>();

        // Priority queue to store nodes to visit, ordered by shortest distance
        // Space: O(V) - in worst case all nodes are in the priority queue.
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        distances.put(startNode, 0);
        pq.offer(new int[]{startNode, 0});

        // Dijkstra's algorithm starts here
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNode = current[0];
            int currentDistance = current[1];

            // If we've already found a shorter path to this node, skip it
            if (currentDistance > distances.getOrDefault(currentNode, Integer.MAX_VALUE)) {
                continue; // Skip outdated entries
            }

            // If we've reached the endNode, reconstruct and return the path
            if (currentNode == endNode) {
                return reconstructPath(previousNodes, endNode);
            }

            // Iterate over the neighbors of the current node
            //Time: O(E) for all neighbors.
            if (adjList.containsKey(currentNode)) {
                for (int[] neighbor : adjList.get(currentNode)) {
                    int nextNode = neighbor[0];
                    int weight = neighbor[1];
                    int newDistance = currentDistance + weight;

                    // Time: O(log V) - insertion into priority queue
                    if (newDistance < distances.getOrDefault(nextNode, Integer.MAX_VALUE)) {
                        distances.put(nextNode, newDistance);
                        previousNodes.put(nextNode, currentNode);
                        pq.offer(new int[]{nextNode, newDistance});
                    }
                }
            }
        }

        return new ArrayList<>(); // No path found
    }

    /**
     * Reconstructs the shortest path from the previousNodes map.
     *
     * @param previousNodes A map containing the previous node in the shortest path for each node.
     * @param endNode       The destination node.
     * @return A list of node IDs representing the shortest path.
     * <p>
     * Time: O(V) in the worst case.
     * Space: O(V) to store the path.
     */
    private List<Integer> reconstructPath(Map<Integer, Integer> previousNodes, int endNode) {
        List<Integer> path = new ArrayList<>();
        Integer current = endNode;

        while (current != null) {
            path.add(0, current);
            current = previousNodes.get(current);
        }

        return path;
    }
}
