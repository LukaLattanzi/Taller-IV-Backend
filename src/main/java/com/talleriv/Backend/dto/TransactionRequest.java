package com.talleriv.Backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Este archivo define la clase `TransactionRequest`, la cual se utiliza para modelar los datos 
 * enviados en las solicitudes relacionadas con la creación de transacciones en la aplicación.
 *
 * **Objetivo principal**:
 * Actuar como un Data Transfer Object (DTO) que encapsula la información necesaria para registrar
 * o procesar una transacción. Esto permite validar y transportar los datos enviados por el cliente 
 * de manera clara y estructurada entre las capas de la aplicación.
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**: 
 *   - Genera automáticamente los métodos `getters`, `setters`, `toString`, `equals`, y `hashCode`.
 *   - Facilita un manejo más limpio y directo de los atributos de la clase.
 * - **`@AllArgsConstructor`**:
 *   - Genera un constructor que incluye todos los campos definidos en la clase.
 * - **`@NoArgsConstructor`**:
 *   - Genera un constructor vacío.
 * - **`@JsonIgnoreProperties(ignoreUnknown = true)`**:
 *   - Ignora cualquier propiedad desconocida durante la deserialización del JSON, permitiendo mayor flexibilidad
 *     al procesar las solicitudes del cliente.
 * - **`@Positive`** (de `jakarta.validation.constraints`):
 *   - Valida que los valores de los campos anotados sean positivos.
 *   - Si alguno de estos campos no cumple con esta validación, se lanza un error con un mensaje generado.
 *
 * **Campos de la Clase**:
 * - **`productId`**:
 *   - Representa el identificador único del producto involucrado en la transacción.
 *   - Validado como un valor positivo mediante `@Positive`.
 *   - Se utiliza para relacionar la transacción con un producto específico.
 * - **`quantity`**:
 *   - Representa la cantidad de productos asociados a la transacción.
 *   - Validado como un valor positivo mediante `@Positive`.
 * - **`supplierId`**:
 *   - Identificador del proveedor asociado a la transacción.
 *   - Es opcional y se usa si la transacción involucra un proveedor específico.
 * - **`description`**:
 *   - Una descripción opcional para complementar información sobre la transacción.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Captura de datos**:
 *   - Esta clase recibe los datos de un cliente (por ejemplo, en una solicitud POST en formato JSON)
 *     para crear o procesar una transacción.
 *   - Ejemplo de JSON enviado por el cliente:
 *     ```json
 *     {
 *         "productId": 10,
 *         "quantity": 5,
 *         "supplierId": 2,
 *         "description": "Compra de artículos de oficina"
 *     }
 *     ```
 * - **Validación**:
 *   - Los datos se validan automáticamente, gracias a las anotaciones `@Positive`, asegurando que los valores sean correctos:
 *     - Si `productId` o `quantity` no son números positivos, se genera un mensaje de error al cliente, como:
 *       ```json
 *       {
 *           "status": 400,
 *           "message": "Product id is required"
 *       }
 *       ```
 * - **Procesamiento**:
 *   - Una vez validados, los datos de la transacción se utilizan para ejecutarse en la lógica del sistema (como la relación
 *     con tablas de productos, proveedores, y cálculos).
 *
 * **Ventajas del Uso de Request DTOs**:
 * 1. **Validación Automática**:
 *    - Reduce los errores gracias a la validación en los datos ingresados por el cliente de manera declarativa.
 * 2. **Flexibilidad**:
 *    - Ignora campos desconocidos en las solicitudes JSON, permitiendo un procesamiento más ágil al interactuar con clientes externos.
 * 3. **Mantenimiento Simplicado**:
 *    - Al encapsular la lógica relacionada con las solicitudes en un DTO, se facilita la modificación posterior, como 
 *      agregar nuevas validaciones o información.
 *
 * **Ejemplo en el Proyecto**:
 * - Cuando el cliente invoca un endpoint como `/transactions/create` con los datos en JSON:
 *   1. Spring deserializa el cuerpo de la solicitud e instancia un objeto de tipo `TransactionRequest`.
 *   2. Los datos son validados automáticamente.
 *   3. Después de la validación, el servicio de transacciones procesa estos datos para registrar la transacción en el sistema.
 *
 * **Ejemplo de Respuesta Exitosa**:
 * ```json
 * {
 *     "status": 201,
 *     "message": "Transaction created successfully"
 * }
 * ```
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {

    // Identificador único del producto (campo obligatorio, valor debe ser positivo).
    @Positive(message = "El id del producto es obligatorio")
    private Long productId;

    // Cantidad de productos (campo obligatorio, valor debe ser positivo).
    @Positive(message = "La cantidad es obligatoria")
    private Integer quantity;

    // Identificador del proveedor relacionado con la transacción (opcional).
    private Long supplierId;

    // Descripción de la transacción (opcional).
    private String description;
}