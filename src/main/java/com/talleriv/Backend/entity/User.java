package com.talleriv.Backend.entity;

import com.talleriv.Backend.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// Anotaciones de JPA y Lombok para reducir código repetitivo
@Entity // Indica que esta clase es una entidad JPA
@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@AllArgsConstructor // Genera el constructor con todos los argumentos
@NoArgsConstructor // Genera el constructor sin argumentos
@Builder // Implementa el patrón Builder para creación de objetos de forma más flexible
@Table(name = "users") // Especifica el nombre de la tabla en la base de datos
public class User {

    // Identificador único del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del usuario (campo obligatorio)
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    // Email del usuario (campo obligatorio y debe ser único)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Column(unique = true) // El email debe ser único
    private String email;

    // Contraseña del usuario (campo obligatorio)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    // Número de teléfono del usuario (campo obligatorio)
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Column(name = "phone_number") // Personaliza el nombre de la columna
    private String phoneNumber;

    // Rol asignado al usuario
    @Enumerated(EnumType.STRING) // Almacena el nombre del enum como cadena
    private UserRole role;

    // Lista de transacciones asociadas al usuario
    @OneToMany(mappedBy = "user") // Relación uno a muchos con la entidad Transaction
    private List<Transaction> transactions;

    // Fecha de creación del usuario (inmutable)
    @Column(name = "created_at") // Personaliza el nombre de la columna
    private final LocalDateTime createdAt = LocalDateTime.now();

    // Método toString sobrescrito para presentar la información del usuario
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", correo='" + email + '\'' +
                ", contraseña='" + password + '\'' +
                ", teléfono='" + phoneNumber + '\'' +
                ", rol=" + role +
                ", fechaCreación=" + createdAt +
                '}';
    }
}