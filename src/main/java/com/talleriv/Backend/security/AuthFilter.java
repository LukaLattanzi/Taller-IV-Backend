package com.talleriv.Backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Esta clase define el filtro `AuthFilter`, que extiende `OncePerRequestFilter` de Spring,
 * utilizado para manejar la autenticación basada en JWT (JSON Web Tokens) en cada solicitud HTTP.
 *
 * **Objetivo principal**:
 * Verificar la validez del token JWT enviado en la cabecera de autorización de las solicitudes.
 * Si el token es válido, autenticar al usuario y configurar el contexto de seguridad para esa solicitud.
 *
 * **Anotaciones utilizadas**:
 * - **`@Component`**:
 *   - Indica que esta clase es un componente gestionado por Spring y que será detectado automáticamente como un bean en el contenedor IoC.
 * - **`@Slf4j`**:
 *   - Proporciona un logger para registrar mensajes e información importante sobre el filtro.
 * - **`@RequiredArgsConstructor`**:
 *   - Crea un constructor para los campos marcados como `final`, permitiendo inyección de dependencias.
 *
 * **Dependencias del Filtro**:
 * - **`JwtUtils`**:
 *   - Clase auxiliar para manejar la generación, validación y extracción de información de los tokens JWT.
 * - **`CustomUserDetailsService`**:
 *   - Proporciona los detalles del usuario (como correo electrónico y roles) desde el token JWT.
 *
 * **Métodos principales**:
 *
 * 1. **`doFilterInternal()`**:
 *    - Es el método que se ejecuta en cada solicitud entrante para aplicar las validaciones del token JWT.
 *    - Flujo detallado:
 *      1. Llama al método `getTokenFromRequest()` para extraer el token desde la cabecera `Authorization`.
 *      2. Si el token existe y no es nulo:
 *         - Obtiene el correo electrónico del usuario desde el token utilizando `JwtUtils`.
 *         - Carga los detalles del usuario utilizando `CustomUserDetailsService`.
 *         - Verifica que el token sea válido para ese usuario.
 *         - Si es válido:
 *           - Crea un objeto `UsernamePasswordAuthenticationToken` con la información del usuario.
 *           - Configura el contexto de seguridad con este token para autorizar la solicitud.
 *      3. Si el token no es válido o ocurre algún problema, el filtro registra el error mediante el logger.
 *      4. Continúa la cadena de filtros con `filterChain.doFilter()`.
 *
 * 2. **`getTokenFromRequest()`**:
 *    - Extrae el token del encabezado `Authorization` en la solicitud HTTP.
 *    - Retorna `null` si:
 *      - El encabezado `Authorization` no está presente.
 *      - El valor del encabezado no contiene el prefijo `Bearer`.
 *    - Si el token está presente, elimina el prefijo `Bearer ` y lo retorna.
 *
 * **Funcionamiento en el Proyecto**:
 * - Este filtro es parte del flujo de seguridad configurado con Spring Security.
 * - Se ejecuta en **cada solicitud**, revisando si el cliente envía un token JWT válido.
 * - Si el token no es válido o falta, el contexto de seguridad no se configura, y el control pasa a los siguientes filtros o controladores.
 * - Si el token es válido:
 *   - Se autentica el usuario configurando el contexto de seguridad con sus roles y permisos.
 *   - Esto permite que el cliente acceda a rutas protegidas según sus privilegios.
 *
 * **Ejemplo de Flujo con JWT**:
 * 1. El cliente envía una solicitud con un encabezado:
 *    ```
 *    Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
 *    ```
 * 2. El filtro:
 *    - Extrae el token desde el encabezado.
 *    - Valida el token JWT.
 *    - Si es válido:
 *      - Obtiene el correo electrónico y los roles del usuario desde el token.
 *      - Configura el contexto de seguridad para esta solicitud.
 * 3. La solicitud es procesada y se autoriza el acceso a los recursos solicitados según los permisos del usuario.
 * 4. Si el token es inválido o falta:
 *    - Se registra un error con el logger y la solicitud no es autenticada.
 *
 * **Ventajas del Uso de `AuthFilter`**:
 * 1. **Seguridad en cada solicitud**:
 *    - Garantiza que solo los usuarios con tokens válidos puedan acceder a recursos protegidos.
 * 2. **Centralización de validación**:
 *    - La lógica de autenticación se centraliza aquí, manteniendo el código más limpio en otras partes de la aplicación.
 * 3. **Flexibilidad**:
 *    - Al trabajar con JWT, no es necesario almacenar sesiones en el servidor, permitiendo un sistema más escalable.
 *
 * **Ejemplo en el Proyecto**:
 * - Ruta protegida:
 *   Si un cliente intenta acceder a un endpoint como `/api/products` sin un token válido, la solicitud será rechazada.
 * - Respuesta esperada si faltan credenciales:
 *   ```json
 *   {
 *       "status": 401,
 *       "message": "Unauthorized"
 *   }
 *   ```
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extraer el token desde la cabecera Authorization.
        String token = getTokenFromRequest(request);

        if (token != null) {
            String email = jwtUtils.getUsernameFromToken(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // Validar el token y configurar el contexto de seguridad si es válido.
            if (StringUtils.hasText(email) && jwtUtils.isTokenValid(token, userDetails)) {
                log.info("El token es valido, {}", email);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        try {
            // Continuar con la cadena de filtros.
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Ocurrió un error en AuthFilter: {}", e.getMessage());
        }
    }

    /**
     * Extrae el token JWT del encabezado Authorization.
     *
     * @param request Solicitud HTTP entrante.
     * @return Token JWT si está presente y es válido, o `null` si no existe.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String tokenWithBearer = request.getHeader("Authorization");
        if (tokenWithBearer != null && tokenWithBearer.startsWith("Bearer ")) {
            return tokenWithBearer.substring(7); // Eliminar el prefijo "Bearer ".
        }
        return null;
    }
}