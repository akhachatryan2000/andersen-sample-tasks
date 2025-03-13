package org.andersen.controller;

import org.andersen.dto.CycleResponseDto;
import org.andersen.dto.DirectedGraphDto;
import org.andersen.service.CycleDetectorService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class CycleDetectionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private CycleDetectorService cycleDetectorService;

    @Test
    public void testIsGraphCyclic_Success_CycleExists() {
        // Arrange
        int[][] graph = {{1, 2}, {2, 3}, {3, 1}}; // Graph with a cycle
        boolean expectedResult = true;
        Mockito.when(cycleDetectorService.graphHasCycle(graph)).thenReturn(expectedResult);

        DirectedGraphDto request = new DirectedGraphDto();
        request.setDirectedGraph(graph);

        // Act
        ResponseEntity<CycleResponseDto> response = restTemplate.postForEntity(
                "/directed-graph/detect-cycle",
                request,
                CycleResponseDto.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResult, response.getBody().isCyclic());
    }

    @Test
    public void testIsGraphCyclic_Success_NoCycle() {
        // Arrange
        int[][] graph = {{1, 2}, {2, 3}, {3, 4}}; // Graph without a cycle
        boolean expectedResult = false;
        Mockito.when(cycleDetectorService.graphHasCycle(graph)).thenReturn(expectedResult);

        DirectedGraphDto request = new DirectedGraphDto();
        request.setDirectedGraph(graph);

        // Act
        ResponseEntity<CycleResponseDto> response = restTemplate.postForEntity(
                "/directed-graph/detect-cycle",
                request,
                CycleResponseDto.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResult, response.getBody().isCyclic());
    }

    @Test
    public void testIsGraphCyclic_BadRequest_EmptyGraph() {
        // Arrange
        int[][] emptyGraph = {}; // Empty graph
        String expectedErrorMessage = "Graph can not be empty";

        DirectedGraphDto request = new DirectedGraphDto();
        request.setDirectedGraph(emptyGraph);

        // Act
        ResponseEntity<CycleResponseDto> response = restTemplate.postForEntity(
                "/directed-graph/detect-cycle",
                request,
                CycleResponseDto.class
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedErrorMessage, response.getBody().getMessage());
    }

    @Test
    public void testIsGraphCyclic_BadRequest_NullGraph() {
        // Arrange
        DirectedGraphDto request = new DirectedGraphDto();
        request.setDirectedGraph(null);

        // Act
        ResponseEntity<CycleResponseDto> response = restTemplate.postForEntity(
                "/directed-graph/detect-cycle",
                request,
                CycleResponseDto.class
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Graph can not be empty", response.getBody().getMessage());
    }
}