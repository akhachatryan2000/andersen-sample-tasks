package org.andersen;

import org.andersen.service.CycleDetectorService;

public class Main {
    public static void main(String[] args) {
        final CycleDetectorService cycleDetectorService = new CycleDetectorService();
        boolean result = cycleDetectorService.graphHasCycle(new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}});
        System.out.println("Output: " + result);
    }
}