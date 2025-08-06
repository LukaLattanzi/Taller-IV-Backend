package com.talleriv.Backend.exceptions;

/**
 * Esta excepción personalizada se utiliza para indicar que las credenciales ingresadas por un usuario son inválidas.
 *
 * **Objetivo Principal**:
 * Ofrecer una forma específica y clara de manejar errores relacionados con autenticaciones fallidas debido a 
 * credenciales incorrectas en la aplicación.
 *
 * **Cómo funciona**:
 * 1. La clase extiende `RuntimeException`, lo que permite lanzarla en cualquier momento sin necesidad de declararla.
 * 2. Es utilizada en puntos donde se valida si las credenciales ingresadas (por ejemplo, nombre de usuario o contraseña) no coinciden
 *    con los registros del sistema o cumplen con los criterios adecuados.
 * 3. Incluye un constructor que permite definir un mensaje de error específico, el cual puede ser consumido
 *    para registrar detalles o ser enviado directamente al cliente.
 *
 * **Ejemplo de uso**:
 * ```java
 * if (!isValidCredentials(username, password)) {
 *     throw new InvalidCredentialsException("Las credenciales ingresadas no son válidas.");
 * }
 * ```
 * 
 * **Ventajas de una excepción personalizada**:
 * - Aumenta la claridad del código al identificar el propósito exacto del error.
 * - Mejora el manejo de errores, ya que puede ser capturada específica y centralmente (por ejemplo, en un `GlobalExceptionHandler`).
 * - Proporciona mensajes de error relevantes que enriquecen la comunicación entre sistema y cliente.
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructor de la excepción que permite establecer un mensaje descriptivo del error.
     *
     * @param message Mensaje de error que se mostrará al lanzarse la excepción.
     */
    public InvalidCredentialsException(String message) {
        super(message); // Envía el mensaje proporcionado al constructor de la clase base.
    }
}