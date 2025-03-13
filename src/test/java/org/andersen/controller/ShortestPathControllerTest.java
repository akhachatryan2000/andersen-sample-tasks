package org.andersen.controller;

import org.andersen.dto.ShortestPathResponseDto;
import org.andersen.dto.WeightedGraphDto;
import org.andersen.service.ShortestPathFinderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class ShortestPathControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private ShortestPathFinderService shortestPathFinderService;

    @Test
    public void testFindShortestPath_Success() {
        // Arrange
        int[][] graph = {{1, 2, 5}, {1, 3, 10}, {2, 4, 2}, {3, 4, 1}, {4, 5, 3}};
        int startNode = 1;
        int endNode = 5;
        List<Integer> expectedPath = Arrays.asList(1, 2, 4, 5);
        Mockito.when(shortestPathFinderService.findShortestPath(graph, startNode, endNode)).thenReturn(expectedPath);

        WeightedGraphDto request = new WeightedGraphDto();
        request.setEndNode(endNode);
        request.setStartNode(startNode);
        request.setWeightedGraph(graph);

        // Act
        ResponseEntity<ShortestPathResponseDto> response = restTemplate.postForEntity(
                "/weighted-graph/shortest-path",
                request,
                ShortestPathResponseDto.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedPath, response.getBody().getShortestPath());
    }

    @Test
    public void testFindShortestPath_BadRequest_EmptyGraph() {
        // Arrange
        int[][] emptyGraph = {};  // Empty graph
        int startNode = 1;
        int endNode = 5;
        String expectedErrorMessage = "Graph cannot be empty.";

        WeightedGraphDto request = new WeightedGraphDto();
        request.setEndNode(endNode);
        request.setStartNode(startNode);
        request.setWeightedGraph(emptyGraph);

        // Act
        ResponseEntity<ShortestPathResponseDto> response = restTemplate.postForEntity(
                "/weighted-graph/shortest-path",
                request,
                ShortestPathResponseDto.class
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedErrorMessage, response.getBody().getMessage());
    }

    @Test
    public void testFindShortestPath_BadRequest_NegativeNode() {
        // Arrange
        int[][] graph = {{1, 2, 5}, {1, 3, 10}, {2, 4, 2}, {3, 4, 1}, {4, 5, 3}};
        int startNode = -1;  // Negative start node
        int endNode = 5;
        String expectedErrorMessage = "Node IDs must be non-negative.";

        WeightedGraphDto request = new WeightedGraphDto();
        request.setEndNode(endNode);
        request.setStartNode(startNode);
        request.setWeightedGraph(graph);

        // Act
        ResponseEntity<ShortestPathResponseDto> response = restTemplate.postForEntity(
                "/weighted-graph/shortest-path",
                request,
                ShortestPathResponseDto.class
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedErrorMessage, response.getBody().getMessage());
    }
}
