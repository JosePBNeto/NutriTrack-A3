package com.app.exceptions;

public class ResourceNotFoundException extends NutritrackException {
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s not found with id: %d", resource, id));
    }
}
