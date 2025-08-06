package com.talleriv.Backend.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talleriv.Backend.dto.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Esta clase es un manejador personalizado para situaciones en las que un acceso es denegado.
 * 
 * **Objetivo principal**:
 * Implementar la interfaz `AccessDeniedHandler` de Spring Security para manejar excepciones de tipo `AccessDeniedException`
 * y enviar una respuesta JSON personalizada al cliente cuando un usuario intenta acceder a recursos protegidos 
 * para los que no tiene privilegios suficientes.
 * 
 * **Cómo funciona**:
 * 1. Cuando ocurre una excepción de tipo `AccessDeniedException`, Spring Security invoca el método `handle()`.
 * 2. En este método:
 *    - Se construye un objeto de respuesta del tipo `Response` con un estado `403 FORBIDDEN` y el mensaje de la excepción.
 *    - La respuesta se configura para devolver contenido de tipo `application/json`.
 *    - Los datos del objeto `Response` se convierten a una cadena JSON utilizando la instancia de `ObjectMapper`.
 *    - Finalmente, el contenido en formato JSON se envía al cliente como una respuesta HTTP.
 * 
 * Este enfoque asegura que los errores relacionados con permisos sean comunicados de forma clara y en un formato estructurado
 * (JSON), lo cual es útil para el cliente (por ejemplo, una aplicación frontend) que consume la API.
 */
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // Instancia de ObjectMapper utilizada para convertir objetos Java a formato JSON.
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        // Construcción del objeto de respuesta personalizado.
        Response errorResponse = Response.builder()
                .status(HttpStatus.FORBIDDEN.value()) // Código de estado HTTP 403.
                .message(accessDeniedException.getMessage()) // Mensaje de error específico.
                .build();

        // Configuración de la respuesta HTTP a nivel de encabezados y estado.
        response.setContentType("application/json"); // Especifica el tipo de contenido devuelto (JSON).
        response.setStatus(HttpStatus.FORBIDDEN.value()); // Establece el código HTTP 403.

        // Conversión del objeto de errorResponse a JSON y escritura en el cuerpo de la respuesta.
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}