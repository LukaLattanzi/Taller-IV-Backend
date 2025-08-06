package com.talleriv.Backend.service.impl;

import com.talleriv.Backend.dto.LoginRequest;
import com.talleriv.Backend.dto.RegisterRequest;
import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.UserDTO;
import com.talleriv.Backend.entity.User;
import com.talleriv.Backend.enums.UserRole;
import com.talleriv.Backend.exceptions.InvalidCredentialsException;
import com.talleriv.Backend.exceptions.NotFoundException;
import com.talleriv.Backend.repository.UserRepository;
import com.talleriv.Backend.security.JwtUtils;
import com.talleriv.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La clase `UserServiceImpl` implementa la lógica de negocio relacionada con la gestión de usuarios en el sistema.
 * Se encarga del registro, autenticación, consulta y administración de usuarios utilizando `UserRepository`
 * y utilidades de seguridad como `PasswordEncoder` y `JwtUtils`.
 *
 * **Propósito General**:
 * - Registrar y autenticar usuarios.
 * - Consultar y actualizar datos personales y de rol.
 * - Obtener al usuario actualmente autenticado.
 * - Proveer información relacionada a las transacciones del usuario.
 */

@Service // Declara esta clase como un servicio gestionado por Spring.
@RequiredArgsConstructor // Genera constructor para inyectar dependencias final.
@Slf4j // Habilita el sistema de logs (para debugging si se necesitara).
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; // Repositorio para persistencia y consultas de usuarios.
    private final PasswordEncoder passwordEncoder; // Codifica contraseñas de forma segura (hash).
    private final ModelMapper modelMapper; // Convierte entidades a DTOs y viceversa.
    private final JwtUtils jwtUtils; // Generador y validador de tokens JWT para autenticación.

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param registerRequest Objeto con los datos necesarios para el registro.
     * @return Respuesta con estado 200 si el registro fue exitoso.
     *
     * **Proceso**:
     * 1. Define un rol por defecto (`MANAGER`) si no se especifica uno.
     * 2. Codifica la contraseña ingresada.
     * 3. Guarda al nuevo usuario en la base de datos.
     */
    @Override
    public Response registerUser(RegisterRequest registerRequest) {

        UserRole role = UserRole.MANAGER;

        if (registerRequest.getRole() != null) {
            role = registerRequest.getRole();
        }

        User userToSave = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(role)
                .build();

        userRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message("user created successfully")
                .build();
    }

    /**
     * Autentica a un usuario y genera un token JWT si las credenciales son válidas.
     *
     * @param loginRequest Contiene el email y contraseña del usuario.
     * @return Respuesta con token JWT, rol y estado de éxito.
     *
     * **Proceso**:
     * 1. Busca al usuario por su email.
     * 2. Verifica que la contraseña ingresada coincida con la almacenada.
     * 3. Genera un token JWT válido por 6 meses.
     */
    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email not Found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("password does not match");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return Response.builder()
                .status(200)
                .message("user logged in successfully")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 month")
                .build();
    }

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     *
     * @return Respuesta con lista de usuarios (DTO) ordenada de forma descendente por ID.
     *
     * **Proceso**:
     * 1. Recupera todos los usuarios desde la base de datos.
     * 2. Convierte la lista a DTOs.
     * 3. Elimina la lista de transacciones de cada DTO por seguridad.
     */
    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<UserDTO> userDTOS = modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());

        userDTOS.forEach(userDTO -> userDTO.setTransactions(null));

        return Response.builder()
                .status(200)
                .message("success")
                .users(userDTOS)
                .build();
    }

    /**
     * Recupera el usuario actualmente autenticado en el contexto de seguridad.
     *
     * @return Entidad `User` correspondiente al usuario logueado.
     *
     * **Proceso**:
     * 1. Obtiene el email del usuario desde el `SecurityContext`.
     * 2. Busca el usuario en la base de datos.
     * 3. Elimina la lista de transacciones para evitar ciclos o información innecesaria.
     */
    @Override
    public User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        user.setTransactions(null);

        return user;
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id ID del usuario a actualizar.
     * @param userDTO Objeto con los nuevos datos del usuario.
     * @return Respuesta de éxito si la actualización fue realizada.
     *
     * **Proceso**:
     * 1. Verifica la existencia del usuario.
     * 2. Actualiza los campos provistos que no son nulos.
     * 3. Codifica la nueva contraseña si fue ingresada.
     */
    @Override
    public Response updateUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getName() != null) existingUser.setName(userDTO.getName());
        if (userDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getRole() != null) existingUser.setRole(userDTO.getRole());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPhoneNumber(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(existingUser);

        return Response.builder()
                .status(200)
                .message("User Successfully updated")
                .build();
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param id ID del usuario a eliminar.
     * @return Respuesta indicando que el usuario fue eliminado correctamente.
     *
     * **Proceso**:
     * 1. Verifica que el usuario exista.
     * 2. Lo elimina utilizando su ID.
     */
    @Override
    public Response deleteUser(Long id) {

        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        userRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("User Successfully Deleted")
                .build();
    }

    /**
     * Recupera las transacciones asociadas a un usuario.
     *
     * @param id ID del usuario.
     * @return Respuesta con usuario y sus transacciones (DTO).
     *
     * **Proceso**:
     * 1. Busca al usuario por ID.
     * 2. Convierte la entidad a DTO.
     * 3. Elimina los datos redundantes de las transacciones (user y supplier) para evitar ciclos.
     */
    @Override
    public Response getUserTransactions(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        userDTO.getTransactions().forEach(transactionDTO -> {
            transactionDTO.setUser(null);
            transactionDTO.setSupplier(null);
        });

        return Response.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();
    }

}
