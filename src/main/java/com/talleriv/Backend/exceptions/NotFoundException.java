package com.talleriv.Backend.exceptions;

/**
 * Esta excepción personalizada se utiliza para indicar que un recurso solicitado no fue encontrado.
 *
 * **Objetivo Principal**:
 * Manejar escenarios en los que no se encuentra un recurso esperado en la base de datos u otro lugar del sistema.
 * Esto podría incluir entidades como productos, usuarios, categorías, entre otros.
 *
 * **Cómo funciona**:
 * 1. La clase extiende `RuntimeException`, lo que significa que es una excepción no verificada 
 *    (unchecked exception).
 * 2. Es lanzada en los puntos del código donde un recurso requerido no existe.
 * 3. Incluye un constructor que permite proporcionar un mensaje descriptivo, que puede ser utilizado 
 *    en los registros del sistema o devuelto al cliente.
 *
 * **Ejemplo de Uso**:
 * ```java
 * Product product = productRepository.findById(id)
 *         .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));
 * ```
 *
 * **Ventajas**:
 * - Proporciona un manejo más específico para casos en los que un recurso no es encontrado.
 * - Permite respuestas claras y personalizadas al cliente.
 * - Facilita la centralización del manejo de este tipo de errores en un manejador global como `GlobalExceptionHandler`.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructor que permite personalizar el mensaje de la excepción.
     *
     * @param message Mensaje descriptivo del error.
     */
    public NotFoundException(String message) {
        super(message); // Envía el mensaje al constructor de la clase base.
    }
}