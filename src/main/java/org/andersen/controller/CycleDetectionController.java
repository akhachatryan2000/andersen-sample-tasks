package org.andersen.controller;

import org.andersen.dto.CycleResponseDto;
import org.andersen.dto.DirectedGraphDto;
import org.andersen.service.CycleDetectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for detecting cycles in directed graphs.
 * This controller provides an endpoint to check if a given directed graph contains a cycle.
 */
@RestController
@RequestMapping(value = "/directed-graph")
public class CycleDetectionController {

    private static final Logger logger = LoggerFactory.getLogger(CycleDetectionController.class);

    private final CycleDetectorService cycleDetectorService;

    @Autowired
    public CycleDetectionController(final CycleDetectorService cycleDetectorService) {
        this.cycleDetectorService = cycleDetectorService;
    }

    /**
     * Constructs a new CycleDetectionController with the specified CycleDetectorService.
     *
     * @param directedGraphDto The wrapper dto containing graph input by the user.
     */
    @PostMapping(value = "/detect-cycle")
    public ResponseEntity<CycleResponseDto> isGraphCyclic(@RequestBody final DirectedGraphDto directedGraphDto) {
        final int[][] directedGraph = directedGraphDto.getDirectedGraph();
        logger.info("Received request to detect cycle in graph {}", directedGraph);
        try {
            if (directedGraph == null || directedGraph.length == 0) {
                throw new IllegalArgumentException("Graph can not be empty");
            }
            final boolean result = cycleDetectorService.graphHasCycle(directedGraph);
            logger.info("Cycle detection result: {}", result);
            return ResponseEntity.ok(new CycleResponseDto(result));
        } catch (final IllegalArgumentException exception) {
            logger.error("Invalid graph input: {}", exception.getMessage());
            return ResponseEntity.badRequest().body(new CycleResponseDto(null, exception.getMessage()));
        } catch (final Exception exception) {
            logger.error("Unexpected error during cycle detection", exception);
            return ResponseEntity.internalServerError().body(new CycleResponseDto(null, "Unexpected error"));
        }
    }
}
