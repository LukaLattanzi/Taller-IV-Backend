package com.talleriv.Backend.entity;

import com.talleriv.Backend.enums.TransactionStatus;
import com.talleriv.Backend.enums.TransactionType;
import jakarta.persistence.*;
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
@Table(name = "transactions") // Especifica el nombre de la tabla en la base de datos
public class Transaction {

    // Identificador único de la transacción
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cantidad total de productos en la transacción
    private Integer totalProducts;

    // Precio total de la transacción
    private BigDecimal totalPrice;

    // Tipo de la transacción (compra, venta, etc.)
    @Enumerated(EnumType.STRING) // Almacena el nombre del enum como cadena
    private TransactionType transactionType;

    // Estado de la transacción (pendiente, completada, fallida, etc.)
    @Enumerated(EnumType.STRING) // Almacena el nombre del enum como cadena
    private TransactionStatus status;

    // Descripción adicional de la transacción
    private String description;

    // Fecha de última actualización
    private LocalDateTime updatedAt;

    // Fecha de creación de la transacción (inmutable)
    private final LocalDateTime createdAt = LocalDateTime.now();

    // Relación muchos a uno con la entidad User (usuario que realizó la transacción)
    @ManyToOne(fetch = FetchType.LAZY) // Carga diferida para optimizar rendimiento
    @JoinColumn(name = "user_id") // Columna de clave foránea en la tabla 'transactions'
    private User user;

    // Relación muchos a uno con la entidad Product (producto relacionado con la transacción)
    @ManyToOne(fetch = FetchType.LAZY) // Carga diferida para optimizar rendimiento
    @JoinColumn(name = "product_id") // Columna de clave foránea en la tabla 'transactions'
    private Product product;

    // Relación muchos a uno con la entidad Supplier (proveedor relacionado con la transacción)
    @ManyToOne(fetch = FetchType.LAZY) // Carga diferida para optimizar rendimiento
    @JoinColumn(name = "supplier_id") // Columna de clave foránea en la tabla 'transactions'
    private Supplier supplier;

    @Override
    public String toString() {
        return "Transacción{" +
                "id=" + id +
                ", totalProductos=" + totalProducts +
                ", precioTotal=" + totalPrice +
                ", tipoTransacción=" + transactionType +
                ", estado=" + status +
                ", descripción='" + description + '\'' +
                ", fechaActualización=" + updatedAt +
                ", fechaCreación=" + createdAt +
                '}';
    }
}