package org.andersen.service;

import java.util.*;

/**
 * A service to detect whether the input directed graph has a cycle.
 * The graph can contain disconnected components.
 * The time complexity of the graphHasCycle method is O(E+V), and space complexity is O(E+V)
 */
public class CycleDetectorService {

    /**
     * Detects whether the graph has a cycle.
     *
     * @param graphEdges The list of directed edges in the graph.
     * @return true if the graph has a cycle, false otherwise.
     */
    public boolean graphHasCycle(final int[][] graphEdges) {
        Map<Integer, List<Integer>> graph = new HashMap<>(); // O(V) space for nodes, O(E) space for edges=> O(V+E) space

        //building the graph - O(E) time
        for (int[] edge : graphEdges) {
            graph.putIfAbsent(edge[0], new ArrayList<>());
            graph.putIfAbsent(edge[1], new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);
        }

        Set<Integer> onStack = new HashSet<>(); // Nodes currently being explored, O(V) space
        Set<Integer> visited = new HashSet<>();  // Already visited nodes, O(V) space

        //Iterating over the nodes - O(V) time
        for (int node : graph.keySet()) {
            if (!visited.contains(node)) {
                if (depthFirstSearchIterative(node, graph, onStack, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Performs an iterative depth-first search (DFS) to detect cycles in the graph.
     *
     * @param start     The starting node for the DFS.
     * @param graph     The adjacency list representation of the graph.
     * @param onStack   Set of nodes currently on the DFS stack.
     * @param visited   Set of nodes that have already been visited.
     * @return true if a cycle is detected, false otherwise.
     */
    private boolean depthFirstSearchIterative(int start,
                                              Map<Integer, List<Integer>> graph,
                                              Set<Integer> onStack,
                                              Set<Integer> visited) {

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.peek();

            if (visited.contains(node)) {
                stack.pop(); // Node is fully processed, pop from stack
                onStack.remove(node);
                continue;
            }

            // Mark node as visited and add to the onStack set
            visited.add(node);
            onStack.add(node);

            boolean hasUnvisitedChildren = false;
            for (int neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor); // Add unvisited neighbor to stack
                    hasUnvisitedChildren = true;
                } else if (onStack.contains(neighbor)) {
                    return true; // Cycle detected (back edge)
                }
            }

            // If no unvisited children remain, mark node as processed
            if (!hasUnvisitedChildren) {
                stack.pop();
                onStack.remove(node);
            }
        }

        return false;
    }
}