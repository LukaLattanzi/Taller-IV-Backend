package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.TransactionRequest;
import com.talleriv.Backend.enums.TransactionStatus;

/**
 * La interfaz `TransactionService` define las operaciones necesarias para gestionar las transacciones en el sistema.
 * Estas operaciones incluyen el manejo de inventario, ventas, devoluciones, consultas y actualizaciones de estado 
 * de transacciones.
 *
 * **Objetivo principal**:
 * Centralizar la lógica de transacciones de inventario y ventas para garantizar un manejo eficiente, 
 * seguro y estructurado de las operaciones relacionadas.
 *
 * **Métodos Declarados**:
 * 
 * 1. **`restockInventory(TransactionRequest transactionRequest)`**:
 *    - Permite incrementar el inventario de un producto específico en el sistema.
 *    - Recibe un objeto `TransactionRequest` que contiene los detalles de la operación, como el producto, 
 *      proveedor y cantidad.
 *    - Retorna un objeto `Response` con el estado de la operación.
 *
 * 2. **`sell(TransactionRequest transactionRequest)`**:
 *    - Maneja la operación de venta de un producto en el sistema.
 *    - Disminuye la cantidad disponible del producto y registra una transacción.
 *    - Recibe un `TransactionRequest` que incluye detalles como el producto y la cantidad.
 *    - Devuelve un `Response` indicando el estado final de la transacción.
 *
 * 3. **`returnToSupplier(TransactionRequest transactionRequest)`**:
 *    - Administra las devoluciones de productos al proveedor.
 *    - Ajusta el inventario y registra la transacción como una devolución.
 *    - Retorna un `Response` con los detalles del proceso.
 *
 * 4. **`getAllTransactions(int page, int size, String searchText)`**:
 *    - Recupera todas las transacciones registradas, con soporte para búsqueda y paginación.
 *    - Recibe la página, el tamaño de la página y el texto de búsqueda para filtrar resultados.
 *    - Retorna un `Response` que incluye la lista de transacciones.
 *
 * 5. **`getTransactionById(Long id)`**:
 *    - Recupera los detalles de una única transacción usando su identificador único.
 *    - Retorna un objeto `Response` con los detalles de la transacción encontrada.
 *
 * 6. **`getAllTransactionByMonthAndYear(int month, int year)`**:
 *    - Recupera todas las transacciones de un mes y año específicos.
 *    - Incluye un filtrado en base a los valores proporcionados.
 *    - Devuelve un `Response` con la lista de transacciones.
 *
 * 7. **`updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus)`**:
 *    - Actualiza el estado de una transacción específica (como PROCESANDO, COMPLETADA, etc.).
 *    - Recibe el `id` de la transacción y su nuevo estado.
 *    - Retorna un `Response` para confirmar los cambios realizados.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Interacción con otras capas**:
 *   - Utilizado principalmente por el controlador para responder a solicitudes relacionadas con el inventario 
 *     y las ventas. Su implementación podría interactuar con repositorios y servicios auxiliares.
 * - **Lógica de negocio**:
 *   - Centraliza la manipulación del inventario (aumentar o disminuir cantidades).
 *   - Facilita el seguimiento de registros históricos de todas las operaciones que afectan a productos.
 *
 * **Ventajas del diseño basado en la interfaz**:
 * 1. **Modularidad**:
 *    - Desacopla la lógica de negocio de las capas superiores (controladores) y garantiza una organización 
 *      clara del código.
 * 
 * 2. **Escalabilidad**:
 *    - Permite agregar nuevas funcionalidades relacionadas con las transacciones sin romper el código existente.
 *
 * 3. **Reutilización**:
 *    - Métodos como `getAllTransactions` y `getAllTransactionByMonthAndYear` pueden ser reutilizados en varios módulos.
 *
 * **Ejemplo práctico**:
 * - Para procesar una venta (`sell`):
 *   1. Validar los datos de entrada (producto, cantidad).
 *   2. Disminuir el inventario del producto.
 *   3. Registrar la transacción en la base de datos.
 *   4. Retornar un objeto `Response` confirmando el éxito de la operación.
 *
 * **Consideraciones de seguridad**:
 * - Validar el acceso del usuario para actualizar o modificar registros en métodos como `updateTransactionStatus`.
 * - Imponer restricciones en cantidades negativas para evitar inconsistencias en el inventario.
 */
public interface TransactionService {

    /**
     * Incrementa el inventario de un producto basado en una solicitud de transacción.
     *
     * @param transactionRequest Objeto con los detalles de la operación.
     * @return Un objeto `Response` conteniendo el resultado de la operación.
     */
    Response restockInventory(TransactionRequest transactionRequest);

    /**
     * Maneja la venta de un producto, reduciendo su inventario.
     *
     * @param transactionRequest Detalles de la transacción de venta.
     * @return Respuesta con el estado de la operación.
     */
    Response sell(TransactionRequest transactionRequest);

    /**
     * Administra la devolución de una cantidad de productos al proveedor.
     *
     * @param transactionRequest Objeto con los detalles de la devolución.
     * @return Respuesta con el estado de la operación.
     */
    Response returnToSupplier(TransactionRequest transactionRequest);

    /**
     * Recupera todas las transacciones con soporte para búsqueda y paginación.
     *
     * @param page Número de página de los resultados.
     * @param size Cantidad de elementos por página.
     * @param searchText Texto de búsqueda para filtrado.
     * @return Respuesta incluyendo la lista de transacciones.
     */
    Response getAllTransactions(int page, int size, String searchText);

    /**
     * Obtiene los detalles de una transacción específica mediante su ID.
     *
     * @param id Identificador único de la transacción.
     * @return Respuesta con los detalles encontrados.
     */
    Response getTransactionById(Long id);

    /**
     * Recupera transacciones realizadas en un mes y año específicos.
     *
     * @param month Mes (de 1 a 12).
     * @param year Año.
     * @return Respuesta con las transacciones realizadas en ese período.
     */
    Response getAllTransactionByMonthAndYear(int month, int year);

    /**
     * Actualiza el estado de una transacción específica.
     *
     * @param transactionId ID de la transacción a actualizar.
     * @param transactionStatus Nuevo estado de la transacción.
     * @return Respuesta confirmando la operación.
     */
    Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus);
}