package com.example.producto.exceptions.exception;

public class NoExisteProducto extends RuntimeException {
    public NoExisteProducto(String message) {
        super(message);
    }
}
