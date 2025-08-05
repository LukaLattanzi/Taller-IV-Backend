package com.talleriv.Backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Anotaciones de JPA y Lombok para reducir código boilerplate
@Entity // Indica que esta clase es una entidad JPA
@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@AllArgsConstructor // Genera constructor con todos los argumentos
@NoArgsConstructor // Genera constructor sin argumentos
@Builder // Implementa el patrón Builder para crear objetos de manera más flexible
@Table(name = "products") // Especifica el nombre de la tabla en la base de datos
public class Product {
    
    // Identificador único del producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del producto (campo obligatorio)
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    // SKU único del producto (campo obligatorio)
    @NotBlank(message = "El SKU es obligatorio")
    @Column(unique = true)
    private String sku;

    // Precio del producto (debe ser positivo)
    @Positive(message = "El precio del producto debe ser un valor positivo")
    private BigDecimal price;

    // Cantidad en stock (no puede ser negativa)
    @Min(value = 0, message = "La cantidad en stock no puede ser menor que cero")
    private Integer stockQuantity;

    // Descripción del producto
    private String description;

    // URL de la imagen del producto
    private String imageUrl;

    // Fecha de vencimiento del producto
    private LocalDateTime expiryDate;

    // Fecha de última actualización
    private LocalDateTime updatedAt;

    // Fecha de creación (inmutable)
    private final LocalDateTime createdAt = LocalDateTime.now();

    // Relación muchos a uno con la entidad Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", precio=" + price +
                ", cantidadEnStock=" + stockQuantity +
                ", descripción='" + description + '\'' +
                ", urlImagen='" + imageUrl + '\'' +
                ", fechaVencimiento=" + expiryDate +
                ", fechaActualización=" + updatedAt +
                ", fechaCreación=" + createdAt +
                '}';
    }
}