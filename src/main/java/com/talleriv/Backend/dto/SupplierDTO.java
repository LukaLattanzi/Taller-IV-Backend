package com.talleriv.Backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Este archivo define la clase `SupplierDTO` (Data Transfer Object), que representa los datos de un proveedor
 * en la aplicación y facilita la transferencia de información a través de las diferentes capas (controladores,
 * servicios y clientes).
 *
 * **Objetivo principal**:
 * Proporcionar una representación simplificada de las entidades relacionadas con los proveedores para
 * garantizar transferencias de datos estructuradas, seguras y específicas.
 * Esto permite evitar la exposición directa de las entidades del modelo de datos a los clientes (frontend o APIs externas).
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**:
 *   - Genera automáticamente los métodos `getters`, `setters`, `toString`, `equals` y `hashCode`.
 *   - Permite un manejo más limpio y directo de los atributos de la clase.
 * - **`@AllArgsConstructor`**:
 *   - Crea un constructor que incluye todos los campos definidos en la clase.
 * - **`@NoArgsConstructor`**:
 *   - Crea un constructor vacío, útil para casos donde se necesita inicializar una instancia sin campos predefinidos.
 * - **`@JsonInclude(JsonInclude.Include.NON_NULL)`**:
 *   - Excluye del resultado JSON los campos que son `null`, asegurando respuestas más compactas y claras.
 * - **`@JsonIgnoreProperties(ignoreUnknown = true)`**:
 *   - Ignora propiedades desconocidas durante la deserialización de JSON. Esto previene errores si el cliente
 *     envía datos adicionales que no están definidos en este DTO.
 * - **`@NotBlank`**:
 *   - Valida que el campo `name` no sea vacío ni nulo. Si esta restricción se viola, se retornará un mensaje de error.
 *
 * **Campos de la Clase**:
 * - `Long id`:
 *   - Identificador único del proveedor.
 *   - Este ID es generalmente generado automáticamente y se utiliza para identificar proveedores en el sistema.
 * - `String name`:
 *   - Nombre del proveedor.
 *   - Este campo es obligatorio, y está validado con la anotación `@NotBlank`.
 * - `String address`:
 *   - Dirección del proveedor.
 *   - Este campo es opcional y puede contener la ubicación física del proveedor, si aplica.
 *
 * **Funcionamiento en el Proyecto**:
 * - **Interacción con la API**:
 *   - Este DTO se usa tanto para solicitud de datos (crear o actualizar un proveedor) como para enviar respuestas al cliente.
 *   - Ejemplo de JSON enviado para crear un proveedor:
 *     ```json
 *     {
 *         "name": "Proveedor Principal",
 *         "address": "Calle Principal, 123"
 *     }
 *     ```
 *   - Ejemplo de JSON en la respuesta de un proveedor creado:
 *     ```json
 *     {
 *         "id": 1,
 *         "name": "Proveedor Principal",
 *         "address": "Calle Principal, 123"
 *     }
 *     ```
 * - **Referencia en otras Clases**:
 *   - Este DTO se utiliza en clases como `TransactionDTO` para representar el proveedor asociado a una transacción.
 *   - Esto asegura que solo los datos relevantes del proveedor sean incluidos en transacciones o relaciones externas.
 *
 * **Ventajas del Uso del DTO**:
 * 1. **Seguridad**:
 *    - Evita exponer detalles internos del modelo de datos.
 *    - Asegura validaciones específicas antes de procesar los datos.
 * 2. **Flexibilidad**:
 *    - Permite responder con solo los datos relevantes para las solicitudes externas.
 *    - Ignora propiedades desconocidas, facilitando integraciones con clientes externos.
 * 3. **Estandarización**:
 *    - Define un diseño consistente para el manejo y transferencia de datos en la aplicación.
 *
 * **Validaciones**:
 * - `@NotBlank` en el campo `name` asegura que siempre se proporcionará un nombre:
 *   - Si no se cumple, se devolverá un error como:
 *     ```json
 *     {
 *         "status": 400,
 *         "message": "Name is required"
 *     }
 *     ```
 *
 * **Ejemplo en el Proyecto**:
 * - Al procesar una transacción, se puede incluir el proveedor asociado utilizando este DTO. Esto permite retornar al cliente detalles como el nombre y dirección del proveedor, manteniendo simplicidad y claridad en las respuestas.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDTO {

    // Identificador único del proveedor.
    private Long id;

    // Nombre del proveedor (obligatorio).
    @NotBlank(message = "Name is required")
    private String name;

    // Dirección del proveedor (opcional).
    private String address;
}