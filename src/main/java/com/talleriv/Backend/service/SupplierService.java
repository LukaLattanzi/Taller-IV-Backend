package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.SupplierDTO;

/**
 * La interfaz `SupplierService` define el contrato para las operaciones relacionadas con la gestión de proveedores
 * en la aplicación. Esta interfaz se centra en ofrecer métodos para gestionar la creación, actualización, obtención y 
 * eliminación de proveedores.
 *
 * **Objetivo principal**:
 * Proveer una capa de abstracción para manejar la lógica de negocio asociada a los proveedores, garantizando 
 * consistencia y claridad en las operaciones expuestas.
 *
 * **Métodos Declarados**:
 * 
 * 1. **`addSupplier(SupplierDTO supplierDTO)`**:
 *    - Permite agregar un nuevo proveedor al sistema.
 *    - Recibe un objeto `SupplierDTO` que contiene los datos del proveedor a registrar.
 *    - Retorna un objeto `Response` indicando el estado y el resultado de la operación.
 *
 * 2. **`updateSupplier(Long id, SupplierDTO supplierDTO)`**:
 *    - Permite actualizar los datos de un proveedor existente.
 *    - Recibe el `id` del proveedor a actualizar y un objeto `SupplierDTO` con los nuevos datos.
 *    - Devuelve un objeto `Response` reflejando el estado de la operación.
 *
 * 3. **`getAllSuppliers()`**:
 *    - Recupera la lista de todos los proveedores disponibles en el sistema.
 *    - Retorna un objeto `Response` que puede incluir la lista de proveedores o un mensaje indicando
 *      el estado de la operación.
 *
 * 4. **`getSupplierById(Long id)`**:
 *    - Recupera la información de un proveedor específico, identificado por su `id`.
 *    - Retorna un objeto `Response` con los datos del proveedor o con un mensaje de error en caso de que no se encuentre.
 *
 * 5. **`deleteSupplier(Long id)`**:
 *    - Permite eliminar un proveedor del sistema utilizando su `id`.
 *    - Devuelve un objeto `Response` confirmando la eliminación o indicando cualquier error producido durante la operación.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Relaciones con otras capas**:
 *   - La interfaz es consumida principalmente por los controladores (por ejemplo, `SupplierController`)
 *     para manejar las solicitudes REST relacionadas con los proveedores.
 * - **Organización del código**:
 *   - Abstrae la lógica de negocio para mantener limpias las capas superiores como los controladores,
 *     delegando las reglas específicas relacionadas con los proveedores a la implementación del servicio.
 *
 * **Ventajas del Enfoque de la Interfaz**:
 * 1. **Desacoplamiento**:
 *    - Permite que las capas superiores (como controladores) no dependan directamente de una implementación.
 *    - Esto facilita la sustitución de una implementación por otra si es necesario en el futuro.
 * 
 * 2. **Documentación Implícita**:
 *    - Define claramente las capacidades relacionadas con los proveedores que ofrece la aplicación.
 * 
 * 3. **Escalabilidad**:
 *    - Hace sencillo añadir nuevas funcionalidades relacionadas con proveedores en un único lugar,
 *      sin afectar otros módulos.
 *
 * **Ejemplo de Implementación**:
 * - En una implementación como `SupplierServiceImpl`, el método `addSupplier` podría:
 *   1. Validar los datos del `SupplierDTO`.
 *   2. Mapear el DTO a la entidad `Supplier` y persistirlo en la base de datos mediante un repositorio.
 *   3. Retornar un objeto `Response` que confirme la operación:
 *      ```java
 *      public Response addSupplier(SupplierDTO supplierDTO) {
 *          Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);
 *          supplierRepository.save(supplier);
 *          return Response.builder()
 *                  .status(200)
 *                  .message("Supplier added successfully")
 *                  .build();
 *      }
 *      ```
 * 
 * **Ventajas de Centralizar la Lógica aquí**:
 * - Facilita la validación previa de datos antes de las operaciones con las entidades.
 * - Asegura consistencia en las respuestas devueltas al cliente.
 * - Permite reutilizar la lógica de negocio desde diferentes controladores o servicios internos.
 */
public interface SupplierService {

    /**
     * Agrega un nuevo proveedor en el sistema.
     *
     * @param supplierDTO Objeto DTO con los datos del proveedor a registrar.
     * @return Objeto `Response` con el estado y resultado de la operación.
     */
    Response addSupplier(SupplierDTO supplierDTO);

    /**
     * Actualiza un proveedor existente.
     *
     * @param id Identificador único del proveedor a actualizar.
     * @param supplierDTO Objeto DTO con los nuevos datos del proveedor.
     * @return Objeto `Response` con el estado y resultado de la operación.
     */
    Response updateSupplier(Long id, SupplierDTO supplierDTO);

    /**
     * Recupera la lista de todos los proveedores registrados.
     *
     * @return Objeto `Response` con la lista de proveedores o el estado de la operación.
     */
    Response getAllSuppliers();

    /**
     * Recupera los detalles de un proveedor específico por su ID.
     *
     * @param id Identificador único del proveedor.
     * @return Objeto `Response` con los datos del proveedor o estado de la operación.
     */
    Response getSupplierById(Long id);

    /**
     * Elimina un proveedor del sistema identificado por su ID.
     *
     * @param id Identificador único del proveedor a eliminar.
     * @return Objeto `Response` confirmando el resultado de la operación.
     */
    Response deleteSupplier(Long id);
}