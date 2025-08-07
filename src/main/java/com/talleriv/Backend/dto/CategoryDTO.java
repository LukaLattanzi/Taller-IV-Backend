package com.talleriv.Backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Este archivo define la clase `CategoryDTO` (Data Transfer Object), utilizada para representar 
 * y transferir información sobre categorías en la aplicación.
 *
 * **Objetivo principal**:
 * Actuar como una representación simplificada de la entidad `Category` para intercambiar datos entre
 * las capas del sistema (controladores, servicios, etc.) sin exponer directamente la entidad del modelo.
 * Esto mejora la seguridad y la flexibilidad, además de permitir validaciones específicas.
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**:
 *   - Genera automáticamente los métodos `getters`, `setters`, `toString`, `equals` y `hashCode`.
 *   - Proporciona un manejo limpio y sencillo de los datos en esta clase.
 * - **`@AllArgsConstructor`**:
 *   - Genera un constructor con todos los campos de la clase.
 * - **`@NoArgsConstructor`**:
 *   - Genera un constructor sin argumentos.
 * - **`@JsonInclude(JsonInclude.Include.NON_NULL)`**:
 *   - Asegura que solo se incluyan en la salida JSON los campos no nulos.
 *   - Esto es útil para simplificar la respuesta JSON al cliente omitiendo valores innecesarios.
 * - **`@JsonIgnoreProperties(ignoreUnknown = true)`**:
 *   - Ignora cualquier propiedad no reconocida durante la deserialización de JSON.
 *   - Permite flexibilidad al procesar respuestas de clientes que podrían incluir datos adicionales.
 * - **`@NotBlank`** (de `jakarta.validation.constraints`):
 *   - Implementa una validación para asegurarse de que el campo `name` no sea nulo ni esté vacío.
 *   - La respuesta generada incluirá el mensaje definido si esta restricción no se cumple.
 *
 * **Campos de la Clase**:
 * - `Long id`: Identificador único de la categoría. Este puede ser generado automáticamente por la base de datos.
 * - `String name`: Nombre de la categoría. Este campo es obligatorio y será validado (no nulo ni vacío).
 *
 * **Funcionamiento en el Proyecto**:
 * - **Al crear o actualizar una categoría**:
 *   - Las solicitudes hacia las APIs enviarán datos en el formato de este DTO (por ejemplo, en JSON).
 *   - Los datos serán validados (por ejemplo, se verificará que `name` no esté vacío).
 * - **Al recuperar información de categorías**:
 *   - Los datos de la entidad `Category` desde la base de datos serán convertidos a `CategoryDTO` 
 *     para ser enviados al cliente.
 *   - Esto asegura que solo se incluyan los datos necesarios (por ejemplo, dejando fuera información sensible o irrelevante).
 *
 * **Ejemplo de JSON esperado para crear una categoría**:
 * ```json
 * {
 *   "name": "Electronics"
 * }
 * ```
 *
 * **Ejemplo de JSON devuelto al cliente**:
 * ```json
 * {
 *   "id": 1,
 *   "name": "Electronics"
 * }
 * ```
 *
 * **Ventajas del Uso del DTO**:
 * - Separa las entidades del modelo de datos (que suelen incluir lógica de persistencia) de las 
 *   transferencias de datos (focalizadas en envíos y respuestas de APIs).
 * - Simplifica las interacciones cliente-servidor, al asegurarse de que solo los datos relevantes sean
 *   expuestos o procesados.
 * - Facilita la validación antes de realizar operaciones en la capa de servicio o en la base de datos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDTO {

    // Identificador único de la categoría.
    private Long id;

    // Nombre de la categoría (campo obligatorio).
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

}