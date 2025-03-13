package org.andersen;

import org.andersen.service.ShortestPathFinderService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ShortestPathFinderService shortestPathFinderService = new ShortestPathFinderService();
        int[][] graph = {{1, 2, 5}, {1, 3, 10}, {2, 4, 2}, {3, 4, 1}, {4, 5, 3}};
        List<Integer> shortestPath = shortestPathFinderService.findShortestPath(graph, 1, 5);

        System.out.println("Output: " + shortestPath);
    }
}