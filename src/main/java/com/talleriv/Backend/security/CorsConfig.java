package com.talleriv.Backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * La clase `CorsConfig` configura el manejo de CORS (Cross-Origin Resource Sharing) en la aplicación backend.
 *
 * **¿Qué es CORS?**:
 * CORS es un mecanismo de seguridad que controla cómo los recursos en un servidor pueden ser solicitados desde
 * un dominio diferente al del propio servidor. Es importante en aplicaciones web que interactúan con APIs desde
 * distintos orígenes (diferentes URLs o puertos).
 *
 * **Objetivo principal**:
 * Permitir o restringir cuáles dominios, métodos HTTP y rutas pueden interactuar con la API del backend.
 *
 * **Anotaciones utilizadas**:
 * - **`@Configuration`**:
 *   - Marca esta clase como un bean de configuración en Spring. Esto significa que será detectada automáticamente
 *     para definir configuraciones personalizadas dentro del contenedor de IoC de Spring.
 * - **`@Bean`**:
 *   - Define un método como un bean administrado por Spring, asegurando su instancia única (singleton).
 *
 * **Método principal**:
 * - `webMvcConfigurer()`:
 *   - Configura CORS para la aplicación. Implementa un `WebMvcConfigurer` anónimo que personaliza la configuración
 *     global de CORS.
 *
 * **Configuración CORS**:
 * - **`registry.addMapping("/**")`**:
 *   - Permite que todas las rutas de la API (`/**`) estén disponibles para solicitudes desde diferentes orígenes.
 * - **`allowedMethods("GET", "POST", "PUT", "DELETE")`**:
 *   - Especifica los métodos HTTP permitidos: `GET`, `POST`, `PUT` y `DELETE`.
 * - **`allowedOrigins("*")`**:
 *   - Permite solicitudes desde cualquier origen (dominio o IP). El asterisco `*` significa que no se aplica restricción en el dominio.
 *
 * **Funcionamiento en el Proyecto**:
 * - Este archivo es vital para habilitar el acceso entre dominios cuando el frontend se encuentra en un servidor
 *   diferente al backend (por ejemplo, si el frontend está en `http://localhost:3000` y el backend en `http://localhost:5050`).
 * - Sin esta configuración, las solicitudes HTTP desde un dominio distinto al backend serían rechazadas por restricciones de CORS.
 *
 * **Ejemplo de Uso en el Proyecto**:
 * 1. Si el frontend ubicado en `http://localhost:3000` realiza una solicitud `POST` al endpoint `/api/login` en el backend:
 *    - Gracias a esta configuración, el backend permite la solicitud, ya que el dominio, ruta y método HTTP son válidos.
 *    - Sin esta configuración, el navegador impediría la solicitud debido a restricciones de seguridad CORS.
 *
 * 2. Solicitudes desde dominios no permitidos o con métodos HTTP no listados serían rechazadas automáticamente por el backend.
 *
 * **Ventajas de esta Configuración**:
 * 1. **Desarrollo Simplificado**:
 *    - Facilita la conexión entre distintas aplicaciones (frontend y backend) durante el desarrollo o en producción.
 * 2. **Control de Acceso**:
 *    - Aunque actualmente cualquier origen está permitido (`*`), se puede restringir a dominios específicos para mayor seguridad.
 * 3. **Flexibilidad**:
 *    - Se permite personalizar los métodos HTTP y rutas accesibles según los requerimientos del sistema.
 *
 * **Future Improvements**:
 * - Cambiar `allowedOrigins("*")` para permitir únicamente ciertos dominios de confianza en entornos de producción.
 * - Agregar configuraciones específicas para manejar cookies o encabezados personalizados si se requiere autenticación en CORS.
 */
@Configuration
public class CorsConfig {
    /**
     * Define un bean de configuración para el manejo de CORS.
     *
     * @return Configuración personalizada de `WebMvcConfigurer`.
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permite solicitudes a todas las rutas y desde cualquier origen.
                registry.addMapping("/**")
                        // Métodos HTTP permitidos.
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        // Orígenes permitidos (actualmente todos).
                        .allowedOrigins("*");
            }
        };
    }
}