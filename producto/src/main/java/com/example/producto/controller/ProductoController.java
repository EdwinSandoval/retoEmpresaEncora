package com.example.producto.controller;


import com.example.producto.dto.request.ProductoRequestDto;
import com.example.producto.dto.response.ProductoResponseDto;
import com.example.producto.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private IProductoService productoService;

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoResponseDto>> listAll(){
        return  ResponseEntity.ok(productoService.listAll());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductoResponseDto> buscarProducto(@PathVariable(name = "id") String idProducto){
        return new ResponseEntity<>(this.productoService.getProductoById(idProducto), HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<ProductoResponseDto> save(@RequestBody ProductoRequestDto request){
        return new ResponseEntity<>(this.productoService.save(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{id}")
    public String delete(@PathVariable("id") String id){
        return productoService.eliminar(id);
    }

    @PutMapping("/actualizar/{id}")
    public ProductoResponseDto actualizar(@RequestBody ProductoRequestDto body, @PathVariable("id") String idUsuario) {
        return productoService.update(body, idUsuario);
    }
}
