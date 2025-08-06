package com.talleriv.Backend.controller;

import com.talleriv.Backend.dto.LoginRequest;
import com.talleriv.Backend.dto.RegisterRequest;
import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// La clase AuthController actúa como controlador REST para manejar 
// las solicitudes relacionadas con la autenticación del usuario (registro y login).
@RestController
@RequestMapping("/api/auth") // Define el punto de acceso común para todas las rutas de este controlador.
@RequiredArgsConstructor // Genera automáticamente un constructor con los campos finales (final), en este caso, userService.
public class AuthController {

    // Inyección del servicio UserService, que contiene la lógica de negocio 
    // relacionada con los usuarios, como el registro y el inicio de sesión.
    private final UserService userService;

    /**
     * Método para manejar el registro de nuevos usuarios.
     *
     * @param registerRequest Objeto que contiene la información de registro enviada por el cliente.
     *                        Este objeto debe ser válido (@Valid) según las validaciones definidas en RegisterRequest.
     * @return Una ResponseEntity con un objeto Response que contiene información sobre el resultado del registro.
     */
    @PostMapping("/register") // Ruta POST para registrar usuarios: /api/auth/register.
    public ResponseEntity<Response> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        // userService.registerUser: Llama al servicio que maneja la lógica de registro.
        // ResponseEntity.ok: Retorna una respuesta HTTP con estado 200 (OK) y el cuerpo proporcionado.
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    /**
     * Método para manejar el inicio de sesión de los usuarios.
     *
     * @param loginRequest Objeto con las credenciales de acceso proporcionadas por el cliente.
     *                     Este objeto debe ser válido (@Valid) según las validaciones definidas en LoginRequest.
     * @return Una ResponseEntity con un objeto Response que contiene información sobre el resultado del inicio de sesión.
     */
    @PostMapping("/login") // Ruta POST para el inicio de sesión: /api/auth/login.
    public ResponseEntity<Response> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        // userService.loginUser: Llama al servicio que maneja la lógica del inicio de sesión.
        // ResponseEntity.ok: Retorna una respuesta HTTP con estado 200 (OK) y el cuerpo proporcionado.
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}