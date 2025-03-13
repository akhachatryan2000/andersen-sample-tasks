package org.andersen.dto;

import java.util.List;

public class ShortestPathResponseDto {
    private List<Integer> shortestPath;
    private String message;

    public ShortestPathResponseDto() {

    }

    public ShortestPathResponseDto(final List<Integer> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public ShortestPathResponseDto(final List<Integer> shortestPath, String message) {
        this.shortestPath = shortestPath;
        this.message = message;
    }

    public List<Integer> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(final List<Integer> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
