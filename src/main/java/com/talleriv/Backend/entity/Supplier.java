package com.talleriv.Backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Anotaciones de JPA y Lombok para reducir código boilerplate
@Entity // Indica que esta clase es una entidad JPA
@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@AllArgsConstructor // Genera constructor con todos los argumentos
@NoArgsConstructor // Genera constructor sin argumentos
@Builder // Implementa el patrón Builder para crear objetos de manera más flexible
@Table(name = "suppliers") // Especifica el nombre de la tabla en la base de datos
public class Supplier {

    // Identificador único del proveedor
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del proveedor (campo obligatorio)
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    // Dirección del proveedor (campo opcional)
    private String address;

    @Override
    public String toString() {
        return "Proveedor{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", dirección='" + address + '\'' +
                '}';
    }
}