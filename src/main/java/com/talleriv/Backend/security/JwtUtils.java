package com.talleriv.Backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * La clase `JwtUtils` proporciona utilidades para el manejo de JSON Web Tokens (JWT) en la aplicación, 
 * permitiendo la generación, validación y extracción de información desde los tokens.
 *
 * **Objetivo principal**:
 * Implementar un sistema de autenticación basado en JWT para la protección de recursos del backend.
 * Esta clase se encarga de manejar la generación segura del token JWT, verificar su validez y extraer los datos necesarios del mismo.
 *
 * **Anotaciones utilizadas**:
 * - **`@Service`**: 
 *   - Marca esta clase como un componente gestionado por Spring, lo que permite su inyección y uso en otras partes del proyecto.
 * - **`@Slf4j`**: 
 *   - Proporciona un logger para registrar eventos importantes durante la ejecución de esta clase.
 * - **`@PostConstruct`**:
 *   - Indica que el método `init()` debe ser ejecutado inmediatamente después de inicializar el bean.
 *
 * **Atributos y Configuración**:
 * - `EXPIRATION_TIME_IN_MILLISEC`: Define la duración del token (6 meses en este caso). Controla el tiempo de expiración de un JWT para seguridad.
 * - `secreteJwtString`: Se inyecta desde el archivo de propiedades del proyecto (`application.properties`). Representa la clave secreta utilizada para firmar los tokens.
 * - `key`: Llave secreta para firmar y verificar los tokens. Se inicializa en el método `init()` utilizando `SecretKeySpec`.
 *
 * **Métodos Principales**:
 * 
 * 1. **`generateToken(String email)`**:
 *    - Genera un JWT único basado en el correo electrónico proporcionado (como sujeto del token).
 *    - Establece la fecha de emisión (`issuedAt`) y la fecha de expiración (`expiration`) del token.
 *    - Firma el token utilizando la clave secreta.
 *    - Retorna el token en formato compacto.
 *
 * 2. **`getUsernameFromToken(String token)`**:
 *    - Extrae el nombre de usuario (correo electrónico en este caso) desde el JWT, utilizando la función predeterminada `Claims.getSubject()`.
 *
 * 3. **`extractClaims(String token, Function<Claims, T> claimsTFunction)`**:
 *    - Método genérico para extraer cualquier información de las `Claims` del token.
 *    - Utiliza una función `claimsTFunction` como argumento para determinar qué dato específico se extrae del token.
 *
 * 4. **`isTokenValid(String token, UserDetails userDetails)`**:
 *    - Verifica la validez de un token comparando:
 *      - Si el nombre de usuario extraído del token coincide con el de la entidad del sistema (`UserDetails`).
 *      - Si el token no ha expirado.
 *    - Retorna `true` si ambas condiciones se cumplen; de lo contrario, `false`.
 *
 * 5. **`isTokenExpired(String token)`**:
 *    - Verifica si el token ha expirado comparando su fecha de expiración con la fecha actual.
 *
 * **Flujo de Trabajo**:
 * - **Generación de Token**:
 *   1. Cuando un usuario inicia sesión exitosamente, el sistema genera un JWT con sus detalles (correo electrónico) y lo envía al cliente.
 *   2. El cliente usa este token en cada solicitud protegida al backend, enviándolo en el encabezado HTTP `Authorization`.
 * 
 * - **Validación del Token**:
 *   1. El backend intercepta las solicitudes entrantes protegidas.
 *   2. Extrae el token del encabezado `Authorization` y lo verifica utilizando los métodos `isTokenValid()` y `isTokenExpired()`.
 *   3. Si el token es válido, se concede acceso; de lo contrario, se niega.
 *
 * **Funcionamiento en el Proyecto**:
 * - `JwtUtils` es crucial para la seguridad de la aplicación, ya que protege los endpoints restringidos mediante autenticación JWT.
 * - Métodos como `generateToken()` y `isTokenValid()` aseguran que solo los usuarios autenticados puedan acceder a los recursos protegidos.
 *
 * **Ejemplo de Uso**:
 * 1. **Generación de Token**:
 *    ```java
 *    String token = jwtUtils.generateToken("user@example.com");
 *    ```
 *    Retorna algo similar a: `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNjkwODgxNzI3LCJleHAiOjE3NTQwNzU3Mjd9.wUKGJZESM...`.
 *
 * 2. **Validación de Token**:
 *    ```java
 *    boolean isValid = jwtUtils.isTokenValid(token, userDetails);
 *    ```
 *    Retorna `true` si el token es válido; de lo contrario, `false`.
 *
 * **Ventajas de `JwtUtils`**:
 * 1. **Seguridad Centralizada**:
 *    - Agrupa toda la lógica relacionada con JWT en un único lugar, simplificando el mantenimiento del proyecto.
 * 2. **Desempeño**:
 *    - Utiliza JWT, lo cual evita consultas repetidas al servidor o base de datos para validar tokens, ya que toda la información está contenida en el token.
 * 3. **Flexibilidad**:
 *    - Es posible extender la lógica de generación y validación, como incluir más `Claims` dentro del JWT según sea necesario.
 *
 * **Posibles Mejoras**:
 * - Implementar un sistema de lista negra para tokens JWT, permitiendo revocarlos manualmente en casos específicos.
 * - Agregar soporte para encabezados y claims adicionales para mayor información.
 */
@Service
@Slf4j
public class JwtUtils {

    private static final long EXPIRATION_TIME_IN_MILLISEC = 100L * 60L * 60L * 24L * 30L * 6L; // Expira en 6 meses.
    private SecretKey key;

    @Value("${secreteJwtString}")
    private String secreteJwtString; // Clave secreta definida en las propiedades del proyecto.

    /**
     * Método inicial que configura la clave secreta para el token JWT al iniciar la aplicación.
     */
    @PostConstruct
    private void init() {
        byte[] keyByte = secreteJwtString.getBytes(StandardCharsets.UTF_8);
        this.key = new SecretKeySpec(keyByte, "HmacSHA256"); // Llave de firma con HMAC-SHA256.
    }

    /**
     * Genera un token JWT con un identificador único (sujeto) y datos de tiempo.
     *
     * @param email El correo del usuario a incluir en el JWT.
     * @return El token JWT generado.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email) // El sujeto principal del token.
                .issuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión.
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILLISEC)) // Fecha de expiración.
                .signWith(key) // Firma utilizando la clave secreta.
                .compact(); // Compacta el token.
    }

    /**
     * Extrae el usuario (correo electrónico) desde el token.
     *
     * @param token El token JWT del cual extraer el sujeto.
     * @return El correo del usuario.
     */
    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extrae información de los claims del token JWT.
     *
     * @param token           El token de entrada.
     * @param claimsTFunction Función para decidir qué claim obtener.
     * @param <T>             Tipo del dato retornado.
     * @return El valor específico del claim.
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    /**
     * Verifica si un token es válido para un usuario.
     *
     * @param token       Token JWT a validar.
     * @param userDetails Información del usuario autenticado.
     * @return `true` si es válido; de lo contrario, `false`.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Comprueba si el token ha expirado.
     *
     * @param token El token JWT.
     * @return `true` si ha expirado; de lo contrario, `false`.
     */
    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}