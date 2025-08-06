package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.CategoryDTO;
import com.talleriv.Backend.dto.Response;

/**
 * La interfaz `CategoryService` define los métodos que deben ser implementados por cualquier clase que maneje 
 * la lógica de negocio relacionada con las categorías en el sistema.
 * 
 * **Objetivo principal**:
 * Abstraer la lógica para manejar categorías en la aplicación, organizando de manera clara qué operaciones
 * están disponibles para gestionar estas entidades a través de los servicios.
 * 
 * **Métodos Declarados**:
 * 1. **`createCategory(CategoryDTO categoryDTO)`**:
 *    - Recibe un objeto de datos `CategoryDTO` y gestiona la creación de una nueva categoría. 
 *    - Retorna un objeto de tipo `Response` con los detalles de la operación, como el estado (éxito o error) 
 *      y cualquier mensaje relevante.
 * 
 * 2. **`getAllCategories()`**:
 *    - Recupera una lista de todas las categorías disponibles del sistema.
 *    - Retorna un `Response` que incluye la lista de categorías o un mensaje de error, si aplica.
 * 
 * 3. **`getCategoryById(Long id)`**:
 *    - Busca y recupera una categoría específica en función del ID proporcionado.
 *    - Retorna un `Response` que contiene los detalles de la categoría, en caso de encontrarse.
 *    - Puede lanzar una excepción personalizada (`NotFoundException`) si la categoría no existe.
 * 
 * 4. **`updateCategory(Long id, CategoryDTO categoryDTO)`**:
 *    - Permite actualizar los datos de una categoría específica, identificada por su ID.
 *    - Recibe un objeto `CategoryDTO` que contiene los datos nuevos, además del ID de la categoría.
 *    - Retorna un `Response` con el estado de la operación.
 *
 * 5. **`deleteCategory(Long id)`**:
 *    - Elimina una categoría del sistema utilizando el ID proporcionado.
 *    - Retorna un `Response` que confirma la eliminación o describe cualquier error que ocurra.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Al usar esta interfaz en clases de implementación** (como `CategoryServiceImpl`):
 *   - Se organiza la lógica de negocio relacionada con las categorías, como la interacción con la base de datos,
 *     las validaciones y la conversión entre objetos `Category` (entidad de base de datos) y `CategoryDTO`.
 * 
 * - **Interacción con los controladores**:
 *   - Los métodos de esta interfaz son invocados directamente por los controladores (por ejemplo, `CategoryController`)
 *     para manejar las solicitudes REST que involucren las categorías.
 *
 * **Ventajas de Usar esta Interfaz**:
 * 1. **Código Limpio y Escalable**:
 *    - Define qué operaciones están disponibles para las categorías, desacoplando la lógica de negocio específica 
 *      de la capa de presentación (controladores).
 * 2. **Flexibilidad**:
 *    - Permite definir múltiples implementaciones del servicio (por ejemplo, en caso de diferentes bases de datos o API externas).
 * 3. **Centralización**:
 *    - Todas las operaciones relacionadas con las categorías están estandarizadas y se manejan desde un único punto.
 *
 * **Ejemplo de Uso**:
 * - En un controlador:
 *   ```java
 *   @PostMapping("/categories")
 *   public ResponseEntity<Response> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
 *       Response response = categoryService.createCategory(categoryDTO);
 *       return ResponseEntity.status(response.getStatus()).body(response);
 *   }
 *   ```
 */
public interface CategoryService {

    /**
     * Crea una nueva categoría.
     * 
     * @param categoryDTO Objeto DTO que contiene los datos de la nueva categoría.
     * @return Respuesta con el resultado de la operación.
     */
    Response createCategory(CategoryDTO categoryDTO);

    /**
     * Recupera todas las categorías disponibles.
     * 
     * @return Respuesta con la lista de categorías.
     */
    Response getAllCategories();

    /**
     * Recupera una categoría por su identificador único.
     * 
     * @param id Identificador único de la categoría.
     * @return Respuesta con los detalles de la categoría.
     */
    Response getCategoryById(Long id);

    /**
     * Actualiza los datos de una categoría existente.
     * 
     * @param id ID de la categoría a actualizar.
     * @param categoryDTO DTO con los nuevos datos de la categoría.
     * @return Respuesta con el estado de la operación.
     */
    Response updateCategory(Long id, CategoryDTO categoryDTO);

    /**
     * Elimina una categoría por su ID.
     * 
     * @param id ID de la categoría a eliminar.
     * @return Respuesta confirmando la eliminación.
     */
    Response deleteCategory(Long id);
}