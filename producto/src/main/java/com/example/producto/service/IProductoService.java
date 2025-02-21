package com.example.producto.service;


import com.example.producto.dto.request.ProductoRequestDto;
import com.example.producto.dto.response.ProductoResponseDto;

import java.util.List;

public interface IProductoService {
    List<ProductoResponseDto> listAll();

    ProductoResponseDto save(ProductoRequestDto request);

    ProductoResponseDto update(ProductoRequestDto request, String id);

    String eliminar(String id);
    ProductoResponseDto getProductoById(String id);

}
