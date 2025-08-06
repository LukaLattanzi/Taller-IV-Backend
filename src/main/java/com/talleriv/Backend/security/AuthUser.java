package com.talleriv.Backend.security;

import com.talleriv.Backend.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Esta clase implementa la interfaz `UserDetails` de Spring Security, proporcionando una representación personalizada
 * del usuario autenticado dentro del sistema, basada en la entidad `User`.
 *
 * **Objetivo principal**:
 * Integrar los usuarios de la aplicación con el ecosistema de seguridad de Spring Security,
 * definiendo los roles, credenciales y el estado del usuario.
 *
 * **Anotaciones utilizadas**:
 * - **`@Data`**:
 *   - Genera automáticamente los métodos `getters`, `setters`, `equals()`, `hashCode()` y `toString()`.
 * - **`@Builder`**:
 *   - Facilita la creación de instancias utilizando el patrón Builder, simplificando la configuración del objeto `AuthUser`.
 *
 * **Atributos**:
 * - `user`:
 *   - Instancia de la clase `User` que contiene toda la información original del usuario (correo electrónico, contraseña y rol).
 *
 * **Métodos Sobrescritos de `UserDetails`**:
 *
 * 1. **`getAuthorities()`**:
 *    - Devuelve una lista de los roles o autoridades del usuario, utilizando la clase `SimpleGrantedAuthority`.
 *    - Se extrae el rol del usuario desde la propiedad `user.getRole()`, convirtiéndolo en un objeto `GrantedAuthority`.
 *    - Ejemplo de retorno: `[ROLE_ADMIN]`, `[ROLE_USER]`.
 *
 * 2. **`getPassword()`**:
 *    - Retorna la contraseña del usuario, que se encuentra almacenada en el campo `user.password`.
 *
 * 3. **`getUsername()`**:
 *    - Retorna el nombre de usuario del usuario. En este caso, el correo electrónico del usuario (`user.email`).
 *
 * 4. **`isAccountNonExpired()`**:
 *    - Proporciona información sobre si la cuenta del usuario ha expirado.
 *    - Actualmente delega al comportamiento predeterminado de la interfaz `UserDetails` retornando `true`.
 *
 * 5. **`isAccountNonLocked()`**:
 *    - Indica si la cuenta del usuario está bloqueada.
 *    - En esta implementación, siempre retorna `true`, lo que significa que no se manejan cuentas bloqueadas.
 *
 * 6. **`isCredentialsNonExpired()`**:
 *    - Indica si las credenciales del usuario (contraseña) han expirado.
 *    - Retorna `true`, asumiendo que las credenciales nunca expiran.
 *
 * 7. **`isEnabled()`**:
 *    - Indica si el usuario está activo y habilitado para autenticarse.
 *    - Siempre retorna `true`, pero en una implementación más compleja podría depender de un estado en la base de datos.
 *
 * **Funcionamiento en el Proyecto**:
 * - Esta clase es utilizada por Spring Security para autenticar y autorizar usuarios.
 * - Los pasos involucrados son los siguientes:
 *   1. Durante el proceso de autenticación, Spring Security solicita los detalles del usuario mediante un servicio de tipo `UserDetailsService`.
 *   2. El servicio retorna una instancia de esta clase (`AuthUser`), representando al usuario autenticado.
 *   3. Spring utiliza el contenido de `AuthUser` para validar las credenciales, autenticarse y asignar roles de autorización.
 *
 * **Ventajas del Uso de `AuthUser`**:
 * 1. **Integración con Spring Security**:
 *    - Implementar `UserDetails` permite configurar usuarios según las reglas de Spring Security.
 * 2. **Flexibilidad**:
 *    - Los datos del usuario original (`User`) están disponibles dentro de esta clase, lo cual permite trabajar con más información de la que requiere Spring por defecto.
 * 3. **Escalabilidad**:
 *    - Es posible agregar más propiedades o lógica específica en métodos como `isEnabled` o `isAccountNonLocked` para manejar casos de negocio más amplios.
 *
 * **Ejemplo de Uso en el Proyecto**:
 * - Un cliente realiza una solicitud al sistema protegida mediante autenticación.
 * - Spring Security valida el token del cliente y utiliza un `CustomUserDetailsService` para cargar dicho usuario, el cual se mapea en un objeto `AuthUser`.
 * - Luego, Spring valida la autenticación con la contraseña y roles del usuario, autorizando o denegando el acceso al recurso solicitado.
 */
@Data
@Builder
public class AuthUser implements UserDetails {

    // Objeto usuario original asociado.
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna los roles o permisos como instancias de GrantedAuthority.
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        // Retorna la contraseña almacenada.
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Retorna el correo electrónico como nombre de usuario.
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Indica que la cuenta no ha expirado (comportamiento por defecto).
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        // Indica que la cuenta no está bloqueada.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Indica que las credenciales no han expirado.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Indica que el usuario está habilitado.
        return true;
    }
}