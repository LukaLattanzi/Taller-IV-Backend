package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.ProductDTO;
import com.talleriv.Backend.dto.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * La interfaz `ProductService` define las operaciones esenciales relacionadas con la gestión 
 * de productos dentro de la aplicación. Proporciona un contrato claro para la implementación 
 * de la lógica de negocio de los productos.
 *
 * **Objetivo principal**:
 * Centralizar y abstraer el manejo de productos, permitiendo que las implementaciones encapsulen 
 * toda la lógica de negocio relacionada con creación, actualización, recuperación y eliminación
 * de productos.
 *
 * **Métodos Declarados**:
 * 
 * 1. **`saveProduct(ProductDTO productDTO, MultipartFile imageFile)`**:
 *    - Permite crear un nuevo producto en el sistema.
 *    - Recibe:
 *      - Un objeto `ProductDTO`, que contiene los datos básicos del producto.
 *      - Un archivo `MultipartFile` para la gestión de la imagen asociada al producto.
 *    - Retorna un objeto `Response` con información del resultado de la operación, incluyendo el 
 *      éxito o error y, opcionalmente, información del producto creado.
 *
 * 2. **`updateProduct(ProductDTO productDTO, MultipartFile imageFile)`**:
 *    - Permite actualizar los datos existentes de un producto.
 *    - Similar a `saveProduct`, recibe un `ProductDTO` y un archivo de imagen opcional.
 *    - Devuelve un `Response` que refleja el estado de la operación de actualización.
 *
 * 3. **`getAllProducts()`**:
 *    - Recupera todos los productos registrados en el sistema.
 *    - Devuelve un `Response` que contiene una lista de productos u otra información relevante al estado
 *      de la operación (por ejemplo, si no existen productos registrados).
 *
 * 4. **`getProductById(Long id)`**:
 *    - Recupera los detalles de un producto específico basado en su identificador único (`id`).
 *    - Retorna un `Response` con la información del producto o un mensaje de error si el producto no se encuentra.
 *
 * 5. **`deleteProduct(Long id)`**:
 *    - Permite eliminar un producto identificado por su ID.
 *    - Devuelve un `Response` que confirma la eliminación o reporta un error en caso de fallar.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Relación con los controladores**:
 *   - La interfaz es utilizada por el controlador REST (`ProductController` o similar) para manejar 
 *     las solicitudes relacionadas con productos. Por ejemplo:
 *     ```java
 *     @PostMapping("/products")
 *     public ResponseEntity<Response> saveProduct(@ModelAttribute ProductDTO productDTO, 
 *                                                 @RequestParam MultipartFile imageFile) {
 *         Response response = productService.saveProduct(productDTO, imageFile);
 *         return ResponseEntity.status(response.getStatus()).body(response);
 *     }
 *     ```
 * - **Interacción con la base de datos**:
 *   - Aunque la interfaz no detalla directamente la persistencia, se espera que cualquier clase que 
 *     implemente `ProductService` maneje la interacción con el repositorio correspondiente mediante JPA 
 *     u otros métodos.
 *
 * **Ventajas del Enfoque de la Interfaz**:
 * - **Abstracción**:
 *   - Las clases que consumen este servicio (como controladores) no dependen de una implementación específica.
 *   - Esto facilita cambios futuros, como sustituir un repositorio JPA por una API externa, sin afectar el código cliente.
 * 
 * - **Legibilidad**:
 *   - La interfaz documenta las capacidades relacionadas con productos de manera clara y concisa.
 * 
 * - **Flexibilidad**:
 *   - Permite definir múltiples implementaciones según las necesidades del sistema, como almacenamiento distribuido 
 *     o gestión de imágenes en servicios externos como AWS S3.
 *
 * **Ejemplo de Implementación General**:
 * - En una implementación como `ProductServiceImpl`, métodos como `saveProduct` podrían incluir los siguientes pasos:
 *   1. Validar los datos del `ProductDTO` recibido.
 *   2. Manejar el almacenamiento del archivo `MultipartFile`, como guardar la imagen del producto en el sistema de archivos
 *      o en un almacenamiento externo.
 *   3. Convertir el `ProductDTO` a la entidad `Product` para persistir los datos en la base de datos.
 *   4. Retornar un objeto `Response` que confirme la operación y, opcionalmente, devuelva los datos del producto.
 *
 * **Ejemplo de Uso**:
 * 1. Crear un producto:
 *    ```java
 *    ProductDTO newProduct = new ProductDTO();
 *    newProduct.setName("Laptop");
 *    Response response = productService.saveProduct(newProduct, imageFile);
 *    ```
 * 
 * 2. Recuperar todos los productos:
 *    ```java
 *    Response response = productService.getAllProducts();
 *    List<ProductDTO> products = response.getData();
 *    ```
 */
public interface ProductService {

    /**
     * Crea un nuevo producto en el sistema.
     * 
     * @param productDTO Objeto DTO que contiene los datos del producto.
     * @param imageFile Archivo de imagen opcional asociado al producto.
     * @return Respuesta con el estado de la operación.
     */
    Response saveProduct(ProductDTO productDTO, MultipartFile imageFile);

    /**
     * Actualiza los datos de un producto existente.
     * 
     * @param productDTO Objeto DTO con el nuevo estado del producto.
     * @param imageFile Archivo de imagen opcional con la nueva imagen.
     * @return Respuesta con el estado de la operación.
     */
    Response updateProduct(ProductDTO productDTO, MultipartFile imageFile);

    /**
     * Obtiene todos los productos registrados en el sistema.
     * 
     * @return Respuesta con la lista de productos o un estado que indique que no hay productos.
     */
    Response getAllProducts();

    /**
     * Recupera la información de un producto específico por su ID.
     * 
     * @param id Identificador único del producto.
     * @return Respuesta con los detalles del producto.
     */
    Response getProductById(Long id);

    /**
     * Elimina un producto del sistema basado en su ID.
     * 
     * @param id ID del producto a eliminar.
     * @return Respuesta indicando el resultado de la operación.
     */
    Response deleteProduct(Long id);
}