package com.talleriv.Backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Anotaciones de JPA y Lombok para reducir código boilerplate
@Entity // Indica que esta clase es una entidad JPA
@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@AllArgsConstructor // Genera constructor con todos los argumentos
@NoArgsConstructor // Genera constructor sin argumentos
@Builder // Implementa el patrón Builder para crear objetos de manera más flexible
@Table(name = "categories") // Especifica el nombre de la tabla en la base de datos
public class Category {

    // Identificador único de la categoría
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la categoría (campo obligatorio)
    @NotBlank(message = "El nombre es obligatorio")
    @Column(unique = true) // El nombre de la categoría debe ser único
    private String name;

    // Lista de productos relacionados con esta categoría (relación uno a muchos)
    @OneToMany(mappedBy = "category") // Mapeado por el atributo "category" en la entidad Product
    private List<Product> products;

    @Override
    public String toString() {
        return "Categoría{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                '}';
    }
}