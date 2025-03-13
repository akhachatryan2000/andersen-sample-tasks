package org.andersen.controller;

import org.andersen.dto.ShortestPathResponseDto;
import org.andersen.dto.WeightedGraphDto;
import org.andersen.service.ShortestPathFinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for finding shortest paths in weighted graphs.
 * This controller provides an endpoint to calculate the shortest path between two nodes in a given weighted graph.
 */
@RestController
@RequestMapping(value = "/weighted-graph")
public class ShortestPathController {

    private static final Logger logger = LoggerFactory.getLogger(ShortestPathController.class);

    private final ShortestPathFinderService shortestPathFinderService;

    @Autowired
    public ShortestPathController(final ShortestPathFinderService shortestPathFinderService) {
        this.shortestPathFinderService = shortestPathFinderService;
    }

    /**
     * Constructs a new ShortestPathController with the specified ShortestPathFinderService.
     *
     * @param weightedGraphDto The wrapper dto containing graph input by the user.
     */
        @PostMapping(value = "/shortest-path")
        public ResponseEntity<ShortestPathResponseDto> findShortestPath(@RequestBody final WeightedGraphDto weightedGraphDto) {
            final int[][] weightedGraph = weightedGraphDto.getWeightedGraph();
            final int startNode = weightedGraphDto.getStartNode();
            final int endNode = weightedGraphDto.getEndNode();

            logger.info("Received request to find shortest path. Start Node: {}, End Node: {}, Graph: {}", startNode, endNode, weightedGraphDto);
            try {
                if (weightedGraph == null || weightedGraph.length == 0) {
                    logger.warn("The received graph is empty");
                    throw new IllegalArgumentException("Graph cannot be empty.");
                }

                if (startNode < 0 || endNode < 0) {
                    logger.warn("The start node or end node are negative. Start Node: {}, End Node: {}", startNode, endNode);
                    throw new IllegalArgumentException("Node IDs must be non-negative.");
                }
                final List<Integer> shortestPath = shortestPathFinderService.findShortestPath(weightedGraph, startNode, endNode);
                logger.info("Shortest path found: {}", shortestPath);
                final ShortestPathResponseDto shortestPathResponseDto = new ShortestPathResponseDto(shortestPath);
                return ResponseEntity.ok(shortestPathResponseDto);

            } catch (final IllegalArgumentException exception) {
                logger.error("Invalid input for shortest path calculation: {}", exception.getMessage());
                return ResponseEntity.badRequest().body(new ShortestPathResponseDto(null, exception.getMessage()));
            } catch (final Exception exception) {
                logger.error("Unexpected error occurred during shortest path calculation", exception);
                return ResponseEntity.internalServerError().body(new ShortestPathResponseDto(null, "Unexpected error occured"));
            }
        }
}

