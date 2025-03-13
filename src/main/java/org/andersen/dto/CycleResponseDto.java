package org.andersen.dto;

public class CycleResponseDto {
    private Boolean isCyclic;
    private String message;

    public CycleResponseDto() {
    }

    public CycleResponseDto(final Boolean isCyclic) {
        this.isCyclic = isCyclic;
    }

    public CycleResponseDto(final Boolean isCyclic, final String message) {
        this.message = message;
        this.isCyclic = isCyclic;
    }

    public Boolean isCyclic() {
        return isCyclic;
    }

    public void setCyclic(final Boolean cyclic) {
        isCyclic = cyclic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
