package com.tfm.aseguradora.backend.tfm.users.service.exception;

import lombok.*;

@Data
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    private Class resourceClass;

    private String resourceIdentifier;

    public ResourceNotFoundException(Class resourceClass, String resourceIdentifier) {
        this.resourceClass = resourceClass;
        this.resourceIdentifier = resourceIdentifier;
    }

    public ResourceNotFoundException(Class resourceClass, Integer resourceIdentifier) {
        this.resourceClass = resourceClass;
        this.resourceIdentifier = String.valueOf(resourceIdentifier);
    }

}