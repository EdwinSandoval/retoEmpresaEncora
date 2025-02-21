package com.example.producto.builders;

import com.example.producto.dto.request.ProductoRequestDto;
import com.example.producto.dto.response.ProductoResponseDto;
import com.example.producto.entity.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ProductoModelBuilder {

    public static Producto getProductoModel(){
        Producto model = new Producto();
        model.setId("1");
        model.setNombre("Juan");
        model.setPrecio(10.00);
        model.setCantidad(23);


        return model;
    }
    public static ProductoResponseDto getProductoResponse(){
        ProductoResponseDto model = new ProductoResponseDto(
                "1","Juan",10.00,23);

        return model;
    }

    public static ProductoRequestDto getProductoRequest(){
        ProductoRequestDto model = new ProductoRequestDto(
                "Wilmer",20.00,34);
        return model;
    }

    public static List<Producto> getAll(){
        Producto re=new Producto("1","Edwin",10.0,26);
        Producto re1=new Producto("2","Freddy",6.0,15);
        Producto re2=new Producto("3","Jose",5.0,23);
        List<Producto> lista=new ArrayList<>();
        Stream.of(re,re1,re2)
                .forEach(lista::add);

        return lista;
    }

}
