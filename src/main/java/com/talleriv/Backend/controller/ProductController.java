package com.talleriv.Backend.controller;

import com.talleriv.Backend.dto.ProductDTO;
import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

// ProductController actúa como controlador REST para manejar solicitudes relacionadas con productos.
// Gestiona la creación, actualización, eliminación y recuperación de productos.
@RestController
@RequestMapping("/api/products") // Define un prefijo de ruta común para todas las operaciones de productos.
@RequiredArgsConstructor // Genera automáticamente un constructor con campos finales, como productService.
@Slf4j // Proporciona soporte para el registro de información (logging) en la clase.
public class ProductController {

    // Inyección del servicio ProductService, que contiene la lógica de negocio relacionada con productos.
    private final ProductService productService;

    /**
     * Método para crear un nuevo producto.
     *
     * @param imageFile     Archivo de imagen del producto.
     * @param name          Nombre del producto.
     * @param sku           SKU (código identificador único) del producto.
     * @param price         Precio del producto.
     * @param stockQuantity Cantidad de stock disponible para el producto.
     * @param categoryId    ID de la categoría a la que pertenece el producto.
     * @param description   Descripción opcional del producto.
     * @return Una ResponseEntity con un objeto Response indicando el resultado de la operación.
     */
    @PostMapping("/add") // Ruta POST para agregar un producto: /api/products/add.
    @PreAuthorize("hasAuthority('ADMIN')") // Solo los administradores pueden acceder a este método.
    public ResponseEntity<Response> saveProduct(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("name") String name,
            @RequestParam("sku") String sku,
            @RequestParam("price") BigDecimal price,
            @RequestParam("stockQuantity") Integer stockQuantity,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "description", required = false) String description
    ) {
        // Crea un nuevo DTO de producto y configura los valores proporcionados por los parámetros.
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setSku(sku);
        productDTO.setPrice(price);
        productDTO.setStockQuantity(stockQuantity);
        productDTO.setCategoryId(categoryId);
        productDTO.setDescription(description);

        // Llama al servicio para guardar el producto y devuelve el resultado.
        return ResponseEntity.ok(productService.saveProduct(productDTO, imageFile));
    }

    /**
     * Método para actualizar un producto existente.
     *
     * @param imageFile     Archivo de imagen opcional para el producto.
     * @param name          Nombre opcional del producto.
     * @param sku           SKU (código identificador único) opcional del producto.
     * @param price         Precio opcional del producto.
     * @param stockQuantity Cantidad de stock opcional para el producto.
     * @param productId     ID del producto a actualizar (obligatorio).
     * @param categoryId    ID opcional de la categoría a la que pertenece el producto.
     * @param description   Descripción opcional para el producto.
     * @return Una ResponseEntity con un objeto Response indicando el estado de la actualización.
     */
    @PutMapping("/update") // Ruta PUT para actualizar un producto: /api/products/update.
    @PreAuthorize("hasAuthority('ADMIN')") // Restringido a usuarios con el rol de administrador.
    public ResponseEntity<Response> updateProduct(
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sku", required = false) String sku,
            @RequestParam(value = "price", required = false) BigDecimal price,
            @RequestParam(value = "stockQuantity", required = false) Integer stockQuantity,
            @RequestParam(value = "productId", required = true) Long productId,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "description", required = false) String description
    ) {
        // Crea un nuevo DTO de producto y configura los valores opcionales proporcionados.
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setSku(sku);
        productDTO.setPrice(price);
        productDTO.setStockQuantity(stockQuantity);
        productDTO.setCategoryId(categoryId);
        productDTO.setProductId(productId);
        productDTO.setDescription(description);

        // Llama al servicio para actualizar el producto y devuelve el resultado.
        return ResponseEntity.ok(productService.updateProduct(productDTO, imageFile));
    }

    /**
     * Método para obtener todos los productos disponibles.
     *
     * @return Una ResponseEntity con un objeto Response que contiene la lista de productos.
     */
    @GetMapping("/all") // Ruta GET para obtener todos los productos: /api/products/all.
    public ResponseEntity<Response> getAllProducts() {
        // Llama al servicio para recuperar todos los productos y devuelve el resultado.
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Método para obtener un producto específico por su ID.
     *
     * @param id ID del producto a recuperar.
     * @return Una ResponseEntity con un objeto Response para el producto solicitado.
     */
    @GetMapping("/{id}") // Ruta GET para obtener un producto por su ID: /api/products/{id}.
    public ResponseEntity<Response> getProductById(@PathVariable Long id) {
        // Llama al servicio para recuperar el producto por ID y devuelve el resultado.
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Método para eliminar un producto por su ID.
     *
     * @param id ID del producto a eliminar.
     * @return Una ResponseEntity con un objeto Response indicando el estado de la eliminación.
     */
    @DeleteMapping("/delete/{id}") // Ruta DELETE para eliminar un producto: /api/products/delete/{id}.
    @PreAuthorize("hasAuthority('ADMIN')") // Restringido a usuarios con el rol de administrador.
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        // Llama al servicio para eliminar el producto y devuelve el resultado.
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}