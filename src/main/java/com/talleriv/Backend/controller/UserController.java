package com.talleriv.Backend.controller;

import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.UserDTO;
import com.talleriv.Backend.entity.User;
import com.talleriv.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// UserController actúa como controlador REST para gestionar solicitudes relacionadas con usuarios.
// Proporciona operaciones como obtener usuarios, actualizaciones, eliminaciones y consultas específicas.
@RestController
@RequestMapping("/api/users") // Define un prefijo común para todas las rutas de este controlador.
@RequiredArgsConstructor // Genera automáticamente un constructor para los campos finales como userService.
public class UserController {

    // Inyección del servicio UserService que contiene la lógica de negocio relacionada con usuarios.
    private final UserService userService;

    /**
     * Método para obtener una lista de todos los usuarios registrados.
     *
     * @return Una ResponseEntity con un objeto Response que contiene la lista de usuarios.
     */
    @GetMapping("/all") // Ruta GET para obtener todos los usuarios: /api/users/all.
    @PreAuthorize("hasAuthority('ADMIN')") // Solo pueden acceder los usuarios con el rol ADMIN.
    public ResponseEntity<Response> getAllUsers() {
        // Llama al servicio para recuperar todos los usuarios.
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Método para actualizar la información de un usuario existente.
     *
     * @param id      ID del usuario que se desea actualizar.
     * @param userDTO Objeto que contiene los nuevos datos del usuario.
     * @return Una ResponseEntity con un objeto Response indicando el resultado de la operación.
     */
    @PutMapping("/update/{id}") // Ruta PUT para actualizar un usuario: /api/users/update/{id}.
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        // Llama al servicio para actualizar el usuario y retorna el resultado.
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    /**
     * Método para eliminar un usuario específico por su ID.
     *
     * @param id ID del usuario que se desea eliminar.
     * @return Una ResponseEntity con un objeto Response indicando el resultado de la eliminación.
     */
    @DeleteMapping("/delete/{id}") // Ruta DELETE para eliminar un usuario: /api/users/delete/{id}.
    @PreAuthorize("hasAuthority('ADMIN')") // Solo los usuarios con el rol ADMIN pueden realizar esta operación.
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        // Llama al servicio para eliminar al usuario y retorna el resultado.
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    /**
     * Método para obtener la información de un usuario y sus transacciones asociadas.
     *
     * @param userId ID del usuario del cual se desean consultar las transacciones.
     * @return Una ResponseEntity con un objeto Response que contiene la información del usuario y sus transacciones.
     */
    @GetMapping("/transactions/{userId}") // Ruta GET para obtener un usuario y sus transacciones: /api/users/transactions/{userId}.
    public ResponseEntity<Response> getUserAndTransactions(@PathVariable Long userId) {
        // Llama al servicio para obtener al usuario y sus transacciones.
        return ResponseEntity.ok(userService.getUserTransactions(userId));
    }

    /**
     * Método para obtener la información del usuario actualmente autenticado.
     *
     * @return Una ResponseEntity con un objeto User representando al usuario autenticado.
     */
    @GetMapping("/current") // Ruta GET para obtener al usuario autenticado: /api/users/current.
    public ResponseEntity<User> getCurrentUser() {
        // Llama al servicio para recuperar los detalles del usuario autenticado.
        return ResponseEntity.ok(userService.getCurrentLoggedInUser());
    }
}