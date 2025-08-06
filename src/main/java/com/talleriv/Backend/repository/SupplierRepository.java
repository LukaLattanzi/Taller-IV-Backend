package com.talleriv.Backend.repository;

import com.talleriv.Backend.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

// Esta interfaz actúa como el repositorio para la entidad Supplier.
// JpaRepository proporciona métodos predefinidos para operaciones CRUD (Create, Read, Update, Delete)
// y consultas avanzadas, como búsquedas y paginación, sin la necesidad de implementar lógica personalizada.
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Al extender JpaRepository<Supplier, Long>, esta interfaz hereda métodos como:
    // - save(): Guarda o actualiza una entidad Supplier.
    // - findById(): Busca un proveedor por su ID.
    // - findAll(): Devuelve todos los proveedores en forma de lista.
    // - deleteById(): Elimina un proveedor por su ID.
}
