package com.talleriv.Backend.exceptions;

import com.talleriv.Backend.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Esta clase es un manejador global de excepciones para una aplicación Spring. 
 *
 * **Objetivo principal**:
 * Centraliza el manejo de excepciones en un único lugar, para interceptar y procesar diferentes errores que ocurren en la aplicación.
 * Gracias a esto, se evita tener que capturar excepciones en múltiples lugares del código, y se puede generar
 * una respuesta uniforme y consistente para el cliente (por ejemplo, en el formato JSON utilizado por el backend).
 *
 * **Cómo funciona**:
 * 1. La clase está anotada con `@ControllerAdvice`, lo que indica que esta actúa como un interceptor global
 *    para todas las excepciones lanzadas por los controladores de la aplicación.
 * 2. Cada método dentro de esta clase maneja un tipo específico de excepción utilizando la anotación `@ExceptionHandler`.
 *    - Cuando una excepción es lanzada en cualquier parte del código, si coincide con algún manejador definido
 *      (por ejemplo, `NotFoundException`), ese método será ejecutado.
 * 3. Cada manejador construye un objeto `Response`, que se devuelve al cliente en formato JSON junto con un estado HTTP adecuado.
 *
 * **Manejadores de excepciones definidos**:
 * - **`Exception`**: Captura cualquier excepción genérica que no tenga un manejador específico. Responde con:
 *   - Código de estado: `500 INTERNAL_SERVER_ERROR`.
 * - **`NotFoundException`**: Maneja casos donde un recurso solicitado no existe. Responde con:
 *   - Código de estado: `404 NOT_FOUND`.
 * - **`NameValueRequiredException`**: Se lanza cuando un valor no válido o faltante es enviado, respondiendo con:
 *   - Código de estado: `400 BAD_REQUEST`.
 * - **`InvalidCredentialsException`**: Captura errores de credenciales inválidas y responde con:
 *   - Código de estado: `400 BAD_REQUEST`.
 *
 * **Ventajas**:
 * - Define respuestas claras y consistentes para distintos tipos de errores en formato JSON.
 * - Centraliza el manejo de errores, reduciendo duplicación de código y aumentando la legibilidad.
 * - Proporciona mensajes descriptivos que pueden ser usados por el cliente para mostrar información al usuario o para depuración.
 *
 * Ejemplo de respuesta JSON a una excepción:
 * ```json
 * {
 *   "status": 404,
 *   "message": "Recurso no encontrado"
 * }
 * ```
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manejador genérico para todas las excepciones no capturadas explícitamente.
     *
     * @param ex La excepción lanzada.
     * @return Una respuesta JSON con estado `500` y el mensaje del error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleAllExceptions(Exception ex){
        Response response = Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()) // Estado HTTP 500.
                .message(ex.getMessage()) // Mensaje del error.
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejador para excepciones de tipo `NotFoundException`.
     *
     * @param ex Excepción lanzada cuando un recurso no es encontrado.
     * @return Una respuesta JSON con estado `404` y un mensaje descriptivo.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleNotFoundException(NotFoundException ex){
        Response response = Response.builder()
                .status(HttpStatus.NOT_FOUND.value()) // Estado HTTP 404.
                .message(ex.getMessage()) // Mensaje de error.
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Manejador para excepciones de tipo `NameValueRequiredException`.
     *
     * @param ex Excepción lanzada por valores requeridos faltantes o inválidos.
     * @return Una respuesta JSON con estado `400` y un mensaje descriptivo.
     */
    @ExceptionHandler(NameValueRequiredException.class)
    public ResponseEntity<Response> handleNameValueRequiredException(NameValueRequiredException ex){
        Response response = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value()) // Estado HTTP 400.
                .message(ex.getMessage()) // Mensaje del error.
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manejador para excepciones de tipo `InvalidCredentialsException`.
     *
     * @param ex Excepción lanzada por credenciales de usuario inválidas.
     * @return Una respuesta JSON con estado `400` y un mensaje descriptivo.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Response> handleInvalidCredentialsException(InvalidCredentialsException ex){
        Response response = Response.builder()
                .status(HttpStatus.BAD_REQUEST.value()) // Estado HTTP 400.
                .message(ex.getMessage()) // Mensaje de error.
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}