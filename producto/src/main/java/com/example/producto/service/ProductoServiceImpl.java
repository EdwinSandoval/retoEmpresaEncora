package com.example.producto.service;


import com.example.producto.dto.request.ProductoRequestDto;
import com.example.producto.dto.response.ProductoResponseDto;
import com.example.producto.entity.Producto;
import com.example.producto.exceptions.exception.ExceptionValidarCantidad;
import com.example.producto.exceptions.exception.ExceptionValidarPrecio;
import com.example.producto.exceptions.exception.ExisteNombre;
import com.example.producto.exceptions.exception.NoExisteProducto;
import com.example.producto.repository.IProductoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private IProductoRepository iProductoRepository;

    @Override
    public List<ProductoResponseDto> listAll() {
        logger.info("Iniciando la consulta para listar todos los productos.");
        List<ProductoResponseDto> productos = new ArrayList<>();

        try {
            List<Producto> listaProductos = this.iProductoRepository.findAll();

            for (Producto p : listaProductos) {
                ProductoResponseDto productoResponseDto = new ProductoResponseDto(
                        p.getId(), p.getNombre(), p.getPrecio(), p.getCantidad()
                );
                productos.add(productoResponseDto);
            }

            logger.info("Se encontraron {} productos.", productos.size());
        } catch (Exception e) {
            logger.error("Ocurrió un error al listar los productos: {}", e.getMessage());
            throw e;
        }

        return productos;
    }





    private Producto crearProducto(ProductoRequestDto request) {
        if (request.precio().equals(0.0)) {
            throw new ExceptionValidarPrecio("El precio debe ser mayor a 0.0");
        }

        if (request.cantidad() < 0) {
            throw new ExceptionValidarCantidad("La cantidad no puede ser negativa");
        }
        logger.info("Creando producto con nombre: {}", request.nombre());
        Producto producto = new Producto();
        producto.setNombre(request.nombre());
        producto.setPrecio(request.precio());
        producto.setCantidad(request.cantidad());
        return producto;
    }

    private Producto mapearAEntidad(Producto producto) {
        Producto productoEntity = new Producto();
        productoEntity.setNombre(producto.getNombre());
        productoEntity.setPrecio(producto.getPrecio());
        productoEntity.setCantidad(producto.getCantidad());

        return productoEntity;
    }

    private Producto guardarProducto(Producto productoEntity) {
        logger.info("Guardando producto en la base de datos: {}", productoEntity.getNombre());
        Producto productoGuardado = iProductoRepository.save(productoEntity);
        logger.debug("Producto guardado con ID: {}", productoGuardado.getId());
        return productoGuardado;
    }

    private ProductoResponseDto construirDtoRespuesta(Producto productoGuardado) {
        ProductoResponseDto response = new ProductoResponseDto(
                productoGuardado.getId(),
                productoGuardado.getNombre(),
                productoGuardado.getPrecio(),
                productoGuardado.getCantidad()

        );
        logger.info("Producto guardado exitosamente. Respuesta construida: {}", response);
        return response;
    }

    @Override
    public ProductoResponseDto save(ProductoRequestDto request) {
        logger.info("Iniciando proceso para guardar producto: {}", request.nombre());
        // Validar si el producto ya existe
        Producto producto = crearProducto(request);
        // Mapear el producto a una entidad
        Producto productoEntity = mapearAEntidad(producto);
        // Guardar el producto en la base de datos
        Producto productoGuardado = guardarProducto(productoEntity);
        // Construir y devolver el DTO de respuesta
        return construirDtoRespuesta(productoGuardado);
    }

    @Override
    public ProductoResponseDto update(ProductoRequestDto request, String id) {
        logger.info("Iniciando proceso de actualización para el producto con ID: {}", id);
        Optional<Producto> encontrado = iProductoRepository.findById(id);
        if (encontrado.isPresent()) {
            logger.info("Producto encontrado con ID: {}", id);
            Producto productoExiste = encontrado.get();
            productoExiste.setNombre(request.nombre());
            productoExiste.setPrecio(request.precio());
            productoExiste.setCantidad(request.cantidad());
            logger.info("Guardando los cambios del producto con ID: {}", id);
            this.iProductoRepository.save(productoExiste);
            logger.debug("Producto con ID: {} actualizado exitosamente", id);
            return new ProductoResponseDto(productoExiste.getId(),
                    productoExiste.getNombre(),productoExiste.getPrecio(),
                    productoExiste.getCantidad());
        }else{
            throw new NoExisteProducto("No existe producto con ese Id");
        }

    }

    @Override
    public String eliminar(String id) {
        logger.info("Iniciando proceso para eliminar producto con ID: {}", id);
        Optional<Producto> encontrado = iProductoRepository.findById(id);
        if (encontrado.isPresent()) {
            logger.info("Producto encontrado con ID: {}, eliminando producto...", id);
            iProductoRepository.deleteById(id);
            logger.debug("Producto con ID: {} eliminado exitosamente", id);
            return "Producto eliminado correctamente";
        }
        logger.warn("No se encontró un producto con el ID: {}", id);
        return "Producto no se encuentra registrado";
    }

    @Override
    public ProductoResponseDto getProductoById(String id) {
        logger.info("Buscando producto con ID: {}", id);
        Optional<Producto> producto = iProductoRepository.findById(id);
        if (producto.isPresent()) {
            logger.info("Producto encontrado con ID: {}", id);
            return new ProductoResponseDto(producto.get().getId(),
                    producto.get().getNombre(),producto.get().getPrecio(),
                    producto.get().getCantidad());
        }else {
            logger.warn("No se encontró un producto con el ID: {}", id);
            throw new NoExisteProducto("No existe producto con ese Id: "+id);
        }
    }

}
