package com.example.producto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "producto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Producto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private String  id;
        private String nombre;
        private Double precio;
        private Integer cantidad;


}
