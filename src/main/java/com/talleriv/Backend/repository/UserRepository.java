package com.talleriv.Backend.repository;

import com.talleriv.Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Esta interfaz actúa como el repositorio para la entidad User.
// JpaRepository proporciona métodos predefinidos para operaciones CRUD (Create, Read, Update, Delete)
// y funcionalidades avanzadas, como búsqueda y paginación, sin necesidad de implementar lógica personalizada.
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Encuentra un usuario en la base de datos utilizando su dirección de correo electrónico.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @return Un Optional que contiene al usuario si existe, o vacío si no se encuentra.
     *
     * Propósito:
     * Este método permite buscar usuarios por su correo electrónico, lo cual resulta útil
     * en funcionalidades como autenticación o validación de existencia previa de usuarios.
     */
    Optional<User> findByEmail(String email);
}