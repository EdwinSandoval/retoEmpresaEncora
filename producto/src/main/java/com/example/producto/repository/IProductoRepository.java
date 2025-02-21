package com.example.producto.repository;

import com.example.producto.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProductoRepository extends JpaRepository<Producto, String> {

   // Optional<Producto> findByNombre(String nombre);
}
