package com.example.producto.service;

import com.example.producto.builders.ProductoModelBuilder;
import com.example.producto.dto.request.ProductoRequestDto;
import com.example.producto.dto.response.ProductoResponseDto;
import com.example.producto.entity.Producto;
import com.example.producto.exceptions.exception.ExisteNombre;
import com.example.producto.exceptions.exception.NoExisteProducto;
import com.example.producto.repository.IProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductoServiceImplTest {

    @InjectMocks
    private ProductoServiceImpl clienteService;

    @Mock
    private IProductoRepository iProductoRepository;

    private Producto cliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Producto();
        cliente.setId("1");
        cliente.setNombre("Juan");
        cliente.setPrecio(10.00);
        cliente.setCantidad(23);


    }

    @Test
    public void testListAll() {
        List<Producto> esperado= ProductoModelBuilder.getAll();
        when(iProductoRepository.findAll()).thenReturn(esperado);
        List<ProductoResponseDto> resultado = clienteService.listAll();
        assertEquals(3,resultado.size());
        assertEquals("Edwin", resultado.get(0).nombre());
        assertEquals("Freddy", resultado.get(1).nombre());

    }

    @Test
    public void testSave() {
        ProductoRequestDto request = ProductoModelBuilder.getProductoRequest();

        when(iProductoRepository.save(any(Producto.class))).thenReturn(ProductoModelBuilder.getProductoModel());

        ProductoResponseDto response = clienteService.save(request);

        assertEquals("Juan", response.nombre());
        verify(iProductoRepository).save(any(Producto.class));
    }

    @Test
    public void testUpdate() {
        ProductoRequestDto request = new ProductoRequestDto("Julio",10.0,24);

        when(iProductoRepository.findById("1")).thenReturn(Optional.of(cliente));
        when(iProductoRepository.save(any(Producto.class))).thenReturn(cliente);

        ProductoResponseDto response = clienteService.update(request, "1");

        assertEquals("Julio", response.nombre());
        verify(iProductoRepository).save(cliente);
    }

    @Test
    public void testEliminar() {
        when(iProductoRepository.findById("1")).thenReturn(Optional.of(cliente));

        String result = clienteService.eliminar("1");

        assertEquals("Producto eliminado correctamente", result);
        verify(iProductoRepository).deleteById("1");
    }

    @Test
    public void testGetProductoById() {
        when(iProductoRepository.findById("1")).thenReturn(Optional.of(cliente));

        ProductoResponseDto response = clienteService.getProductoById("1");

        assertEquals("Juan", response.nombre());
    }

    @Test
    public void testGetProductoById_NoExistente() {
        when(iProductoRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(NoExisteProducto.class, () -> {
            clienteService.getProductoById("999");
        });
    }


}