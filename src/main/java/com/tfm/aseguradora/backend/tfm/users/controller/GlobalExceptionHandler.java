package com.tfm.aseguradora.backend.tfm.users.controller;

import com.tfm.aseguradora.backend.tfm.users.service.exception.*;
import com.tfm.aseguradora.generated.backend.tfm.users.controller.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

import java.time.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto();
        message.setStatusCode(HttpStatus.NOT_FOUND.value());
        message.setTimestamp(OffsetDateTime.now());
        message.setMessage("Entity not found");
        message.setDescription("Entity " + ex.getResourceClass() + "  with id " + ex.getResourceIdentifier() + " not found");
        return message;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto badRequestException(BadRequestException ex, WebRequest request) {
        ErrorMessageDto message = new ErrorMessageDto();
        message.setStatusCode(HttpStatus.BAD_REQUEST.value());
        message.setTimestamp(OffsetDateTime.now());
        message.setMessage("Bad Request: some parameter are wrong");
        message.setDescription(ex.getMessage());
        return message;
    }

}