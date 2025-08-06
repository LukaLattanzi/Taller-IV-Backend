package com.talleriv.Backend.security;

import com.talleriv.Backend.exceptions.CustomAccessDeniedHandler;
import com.talleriv.Backend.exceptions.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * La clase `SecurityFilter` configura la seguridad en la aplicación utilizando Spring Security, definiendo cómo se manejan
 * las solicitudes HTTP, autenticación, autorización, manejo de excepciones y otros aspectos de seguridad.
 *
 * **Objetivo principal**:
 * Proteger los recursos del backend mediante la implementación de políticas como autenticación basada en tokens JWT,
 * autorización específica por endpoints y manejo de excepciones personalizadas.
 *
 * **Anotaciones utilizadas**:
 * - **`@Configuration`**: 
 *   - Indica que esta clase contiene configuraciones de Spring, en este caso, relacionadas con seguridad.
 * - **`@EnableWebSecurity`**: 
 *   - Habilita las configuraciones de seguridad específicas para la web.
 * - **`@EnableMethodSecurity`**:
 *   - Permite la configuración de seguridad a nivel de métodos mediante anotaciones como `@PreAuthorize`.
 * - **`@RequiredArgsConstructor`**: 
 *   - Genera un constructor con las dependencias `final`, facilitando la inyección de dependencias.
 * - **`@Slf4j`**: 
 *   - Proporciona un logger para registrar eventos importantes durante la ejecución de esta clase.
 *
 * **Atributos**:
 * - `authFilter`: Filtro personalizado para validar tokens JWT (utilizado en el flujo de autenticación de solicitudes HTTP).
 * - `customAuthenticationEntryPoint`: Clase personalizada que maneja respuestas JSON para excepciones de autenticación (401).
 * - `customAccessDeniedHandler`: Clase personalizada para manejar errores de autorización (403) y dar respuestas estructuradas.
 *
 * **Métodos Principales**:
 * 
 * 1. **`securityFilterChain(HttpSecurity httpSecurity)`**:
 *    - Configura los filtros de seguridad para las solicitudes HTTP entrantes.
 *    - Configuraciones específicas:
 *      - **`csrf(AbstractHttpConfigurer::disable)`**:
 *        - Desactiva la protección CSRF, ya que no es necesaria en aplicaciones RESTful con autenticación basada en tokens.
 *      - **`cors(Customizer.withDefaults())`**:
 *        - Habilita el manejo del Cross-Origin Resource Sharing (CORS), configurado en otro lugar (como en `CorsConfig`).
 *      - **`exceptionHandling()`**:
 *        - Configura el controlador de excepciones:
 *          - Uso de `customAccessDeniedHandler` para errores de autorización.
 *          - Uso de `customAuthenticationEntryPoint` para errores de autenticación.
 *      - **`authorizeHttpRequests()`**:
 *        - Define la configuración de acceso a los endpoints:
 *          - Permite el acceso sin autenticación a rutas bajo `/api/auth/**`.
 *          - Requiere autenticación para cualquier otra solicitud.
 *      - **`sessionManagement()`**:
 *        - Establece la política sin estado (`stateless`) para no guardar información de sesiones en el servidor, pues se utiliza JWT para autenticación.
 *      - **`addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)`**:
 *        - Añade el filtro personalizado para validación de JWT antes del filtro predeterminado de Spring Security.
 *    - Retorna un objeto `SecurityFilterChain` construido.
 *
 * 2. **`passwordEncoder()`**:
 *    - Expone un bean para codificación de contraseñas utilizando `BCryptPasswordEncoder`.
 *    - Es utilizado para encriptar y verificar contraseñas almacenadas y proporcionadas por los usuarios.
 *
 * 3. **`authenticationManager(AuthenticationConfiguration authenticationConfiguration)`**:
 *    - Crea y expone un `AuthenticationManager` que permite manejar el proceso de autenticación de usuarios. 
 *    - Aprovecha la configuración predeterminada proporcionada por Spring Security.
 *
 * **Funcionamiento en el Proyecto**:
 * - Define los aspectos fundamentales de la seguridad en el backend:
 *   - **Autenticación**:
 *     - Las solicitudes deben incluir un token JWT válido, validado por el filtro `authFilter` antes de acceder a los endpoints protegidos.
 *   - **Autorización**:
 *     - Configura las reglas para qué usuarios pueden acceder a determinados endpoints.
 *     - Ejemplo: Rutas bajo `/api/auth/**` son de libre acceso, mientras que todas las demás rutas requieren autenticación.
 *   - **Manejo de Excepciones**:
 *     - Proporciona respuestas claras y consistentes para errores de autenticación (401) y autorización (403).
 * - Se integra con otras partes del proyecto, como `JwtUtils` para la validación de tokens y los controladores para el manejo de solicitudes específicas.
 *
 * **Ejemplo de Uso**:
 * 1. **Petición sin autenticación**:
 *    - Una solicitud a `/api/data` sin un token válido responderá con:
 *      - Código HTTP `401`.
 *      - Cuerpo JSON con el mensaje de error generado por `CustomAuthenticationEntryPoint`.
 * 2. **Petición con token válido**:
 *    - Una solicitud con un token válido accederá normalmente a recursos protegidos, siempre que el usuario tenga los permisos necesarios.
 *
 * **Ventajas de esta Configuración**:
 * 1. **Flexibilidad**:
 *    - Permite personalizar reglas de acceso por endpoint.
 * 2. **Eficiencia**:
 *    - Usa una política sin estado, eliminando la necesidad de almacenar sesiones en el servidor.
 * 3. **Consistencia**:
 *    - Proporciona un manejo centralizado y uniforme de autenticación, autorización y excepciones.
 *
 * **Mejoras Futuras**:
 * - Ampliar las capacidades de autorización utilizando roles o permisos más granulares en los endpoints.
 * - Implementar reglas de CORS más específicas para mayor seguridad.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter {
    private final AuthFilter authFilter; // Filtro para validar JWT en cada solicitud.
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint; // Manejo de errores de autenticación (401).
    private final CustomAccessDeniedHandler customAccessDeniedHandler; // Manejo de errores de autorización (403).

    /**
     * Configura la cadena de filtros para la seguridad HTTP en la aplicación.
     *
     * @param httpSecurity Objeto de configuración proporcionado por Spring Security.
     * @return La cadena de filtros de seguridad.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) // Habilita CORS.
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler) // Personaliza manejo de errores 403.
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // Personaliza manejo de errores 401.
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/**").permitAll() // Permite solicitudes sin autenticación para estos endpoints.
                        .anyRequest().authenticated() // Todas las demás solicitudes requieren autenticación.
                )
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Define que no se utilizan sesiones.
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class); // Añade el filtro de autenticación antes del filtro predeterminado.
        return httpSecurity.build();
    }

    /**
     * Proporciona un bean de codificador de contraseñas utilizando BCrypt.
     *
     * @return Instancia de `BCryptPasswordEncoder`.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define el `AuthenticationManager` para manejar autenticaciones.
     *
     * @param authenticationConfiguration Configuración de autenticación proporcionada por Spring.
     * @return El gestor de autenticación global.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}