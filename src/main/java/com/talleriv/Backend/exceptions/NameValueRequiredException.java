package com.talleriv.Backend.exceptions;

/**
 * Esta excepción personalizada se utiliza para indicar que un valor obligatorio relacionado con el nombre 
 * (o cualquier otro campo requerido) no ha sido proporcionado o es inválido.
 *
 * **Objetivo Principal**:
 * Proveer un error específico para escenarios donde uno o más valores requeridos en una solicitud están ausentes 
 * o no cumplen con los criterios esperados.
 *
 * **Cómo funciona**:
 * 1. Extiende `RuntimeException`, lo que significa que es una excepción no verificada (unchecked exception).
 * 2. Se lanza cuando un valor obligatorio como un campo de "nombre" falta, es nulo, está vacío o no cumple con el formato esperado.
 * 3. Permite incluir un mensaje descriptivo al momento de ser lanzada, que puede ser utilizado para informar al cliente o 
 *    registrar información en el sistema.
 *
 * **Ejemplo de uso**:
 * ```java
 * if (name == null || name.isBlank()) {
 *     throw new NameValueRequiredException("El campo nombre es obligatorio y no puede estar vacío.");
 * }
 * ```
 *
 * **Ventajas y Propósito**:
 * - Ayuda a identificar rápidamente problemas relacionados con datos faltantes o inapropiados en las solicitudes.
 * - Facilita la centralización de la lógica de validación al lanzar una excepción que puede ser manejada globalmente.
 * 
 * Esta excepción suele ser manejada en un componente como `GlobalExceptionHandler`, donde se construye una respuesta clara 
 * para el cliente, por ejemplo, con un código HTTP `400 BAD REQUEST`.
 */
public class NameValueRequiredException extends RuntimeException {

    /**
     * Constructor que permite proporcionar un mensaje explicativo del error al lanzar la excepción.
     *
     * @param message Mensaje detallado que describe el problema.
     */
    public NameValueRequiredException(String message) {
        super(message); // Envía el mensaje al constructor de la clase base.
    }
}