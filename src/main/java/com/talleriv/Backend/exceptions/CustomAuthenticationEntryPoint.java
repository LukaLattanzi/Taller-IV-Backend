package com.talleriv.Backend.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talleriv.Backend.dto.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Esta clase define un **punto de entrada personalizado** para manejar solicitudes no autenticadas en una
 * aplicación que utiliza Spring Security.
 *
 * **Objetivo principal**:
 * Implementar la interfaz `AuthenticationEntryPoint` para gestionar excepciones de autenticación 
 * (por ejemplo, cuando un usuario no autenticado intenta acceder a recursos protegidos) y responder con un mensaje específico
 * en formato JSON.
 *
 * **Cómo funciona**:
 * 1. Cuando una solicitud que no está autenticada intenta acceder a un recurso protegido, Spring Security llama al 
 *    método `commence()` de esta clase.
 * 2. En este método:
 *    - Se crea un objeto de respuesta del tipo `Response`, configurando el estado como `401 UNAUTHORIZED` y 
 *      asignando el mensaje de la excepción `AuthenticationException`.
 *    - La respuesta HTTP se configura para devolver contenido de tipo `application/json`.
 *    - El objeto de respuesta se convierte a formato JSON utilizando `ObjectMapper` y se escribe en el cuerpo de la respuesta HTTP.
 *
 * **Ventajas**:
 * - Proporciona un mensaje de error claro y estructurado (en formato JSON) para facilitar la comunicación entre el backend
 *   y un cliente API (como una aplicación frontend).
 * - La respuesta personalizada puede incluir detalles sobre el error y resolver problemas comunes para el cliente.
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Instancia de ObjectMapper utilizada para convertir objetos Java a formato JSON.
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        // Construcción de un objeto de respuesta personalizada.
        Response errorResponse = Response.builder()
                .status(HttpStatus.UNAUTHORIZED.value()) // Código de estado HTTP 401.
                .message(authException.getMessage()) // Mensaje que detalla el error de autenticación.
                .build();

        // Configuración del tipo de contenido y el estado de la respuesta HTTP.
        response.setContentType("application/json"); // Especifica un contenido tipo JSON.
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Código HTTP 401.

        // Conversión del objeto de respuesta a JSON y escritura en el cuerpo de la respuesta.
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}