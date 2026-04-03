package com.rodo.auth.dto;

public record ErrorResponse(
        int codigo,
        String mensaje
) { }