package com.talleriv.Backend.service.impl;

import com.talleriv.Backend.dto.ProductDTO;
import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.entity.Category;
import com.talleriv.Backend.entity.Product;
import com.talleriv.Backend.exceptions.NotFoundException;
import com.talleriv.Backend.repository.CategoryRepository;
import com.talleriv.Backend.repository.ProductRepository;
import com.talleriv.Backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * La clase `ProductServiceImpl` implementa las operaciones de la interfaz `ProductService` para 
 * gestionar las operaciones relacionadas con productos. Este servicio maneja la lógica de negocio 
 * que conecta los datos de productos con su correspondiente representación en la base de datos y 
 * su presentación como respuesta a través de DTOs.
 *
 * **Propósito General**:
 * - Implementar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) asociadas a productos.
 * - Manejar la lógica de negocio, como la validación y la actualización de datos, antes de interactuar con la base de datos.
 * - Manejar la subida y almacenamiento de imágenes asociadas a productos.
 *
 * **Características Principales**:
 * - Uso de `ModelMapper` para la conversión de entidades a DTOs (y viceversa).
 * - Uso de excepciones personalizadas (como `NotFoundException`) para un manejo claro de errores.
 * - Control y validación de imágenes cargadas por los usuarios.
 */

@Service // Marca esta clase como un servicio gestionado por Spring.
@Slf4j // Proporciona un mecanismo de registro para depurar o informar sobre eventos en tiempo de ejecución.
@RequiredArgsConstructor // Genera un constructor con los parámetros necesarios para los campos finales.
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository; // Repositorio para realizar operaciones CRUD con la base de datos.
    private final ModelMapper modelMapper; // Herramienta para mapear entre entidades y DTOs.
    private final CategoryRepository categoryRepository; // Repositorio usado para validar categorías asociadas.

    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/product-image/"; // Ruta para almacenar imágenes en el backend.
    private static final String IMAGE_DIRECTOR_FRONTEND = "/home/luka-lattanzi/Documentos/InventoryManagementSystem/Frontend/public/products/"; // Ruta para almacenar imágenes en el frontend.

    /**
     * Guarda un nuevo producto en la base de datos.
     *
     * @param productDTO DTO con la información del producto.
     * @param imageFile Archivo de imagen asociado al producto.
     * @return Respuesta indicando el estado de la operación.
     *
     * **Proceso**:
     * 1. Valida la existencia de la categoría a través de su ID.
     * 2. Convierte el `ProductDTO` en una entidad de tipo `Product`.
     * 3. Si hay una imagen, guarda la imagen en el sistema de archivos y registra su URL.
     * 4. Guarda el producto en la base de datos y devuelve una respuesta exitosa.
     */
    @Override
    public Response saveProduct(ProductDTO productDTO, MultipartFile imageFile) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Categoria no encontrada"));

        Product productToSave = Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .stockQuantity(productDTO.getStockQuantity())
                .description(productDTO.getDescription())
                .category(category)
                .build();

        if (imageFile != null) {
            String imagePath = saveImageToFrontendPublicFolder(imageFile);
            productToSave.setImageUrl(imagePath);
        }

        productRepository.save(productToSave);
        return Response.builder()
                .status(200)
                .message("Producto creado exitosamente")
                .build();
    }

    /**
     * Actualiza un producto existente.
     *
     * @param productDTO DTO con los datos a actualizar.
     * @param imageFile Archivo de imagen actualizado (opcional).
     * @return Respuesta indicando el estado de la operación.
     *
     * **Proceso**:
     * 1. Valida la existencia del producto por su ID.
     * 2. Si se incluye una nueva imagen, se almacena y se actualiza el producto con la nueva URL de la imagen.
     * 3. Si se proporciona una nueva categoría, se valida y se actualiza.
     * 4. Se actualizan los campos proporcionados en el `DTO`.
     * 5. Guarda los cambios en la base de datos y responde exitosamente.
     */
    @Override
    public Response updateProduct(ProductDTO productDTO, MultipartFile imageFile) {
        Product existingProduct = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImageToFrontendPublicFolder(imageFile);
            existingProduct.setImageUrl(imagePath);
        }

        if (productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Categoria no encontrada"));
            existingProduct.setCategory(category);
        }

        if (productDTO.getName() != null && !productDTO.getName().isBlank()) {
            existingProduct.setName(productDTO.getName());
        }
        if (productDTO.getSku() != null && !productDTO.getSku().isBlank()) {
            existingProduct.setSku(productDTO.getSku());
        }
        if (productDTO.getDescription() != null && !productDTO.getDescription().isBlank()) {
            existingProduct.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != null && productDTO.getPrice().compareTo(BigDecimal.ZERO) >= 0) {
            existingProduct.setPrice(productDTO.getPrice());
        }
        if (productDTO.getStockQuantity() != null && productDTO.getStockQuantity() >= 0) {
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        }

        productRepository.save(existingProduct);
        return Response.builder()
                .status(200)
                .message("Producto actualizado exitosamente")
                .build();
    }

    /**
     * Recupera todos los productos de la base de datos.
     *
     * @return Respuesta con la lista de productos.
     */
    @Override
    public Response getAllProducts() {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<ProductDTO> productDTOS = modelMapper.map(products, new TypeToken<List<ProductDTO>>() {}.getType());
        return Response.builder()
                .status(200)
                .message("Exito al recuperar los productos")
                .products(productDTOS)
                .build();
    }

    /**
     * Recupera el detalle de un producto por su identificador.
     *
     * @param id ID del producto.
     * @return Respuesta con los detalles del producto.
     */
    @Override
    public Response getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        return Response.builder()
                .status(200)
                .message("Exito al recuperar el producto")
                .product(modelMapper.map(product, ProductDTO.class))
                .build();
    }

    /**
     * Elimina un producto de la base de datos.
     *
     * @param id Identificador del producto.
     * @return Respuesta confirmando la eliminación.
     *
     * **Proceso**:
     * 1. Valida la existencia del producto a través de su ID.
     * 2. Elimina el producto de la base de datos.
     * 3. Devuelve una respuesta indicando el éxito de la operación.
     */
    @Override
    public Response deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        productRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("Producto eliminado exitosamente")
                .build();
    }

    /**
     * Método auxiliar para guardar imágenes en la carpeta pública del frontend.
     *
     * @param imageFile Archivo de imagen proporcionado.
     * @return Ruta relativa donde se almacena la imagen.
     */
    private String saveImageToFrontendPublicFolder(MultipartFile imageFile) {
        if (!imageFile.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten archivos de imagen.");
        }

        File directory = new File(IMAGE_DIRECTOR_FRONTEND);
        if (!directory.exists()) {
            directory.mkdir();
            log.info("Directorio creado");
        }

        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        String imagePath = IMAGE_DIRECTOR_FRONTEND + uniqueFileName;

        try {
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ocurrió un error al guardar la imagen" + e.getMessage());
        }

        return "products/" + uniqueFileName;
    }
}