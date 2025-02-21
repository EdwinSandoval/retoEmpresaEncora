package com.example.producto.dto.response;


public record ProductoResponseDto (

        String  id,
        String nombre,
        Double precio,
        Integer cantidad
        ) {
    public ProductoResponseDto {
    }
}
