package com.talleriv.Backend.repository;

import com.talleriv.Backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// Esta interfaz actúa como el repositorio para la entidad Product.
// JpaRepository proporciona métodos predefinidos para operaciones CRUD (Create, Read, Update, Delete)
// y consultas avanzadas, como búsquedas y paginación, sin la necesidad de implementar lógica personalizada.
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Al extender JpaRepository<Product, Long>, esta interfaz hereda métodos como:
    // - save(): Guarda o actualiza una entidad Category.
    // - findById(): Busca una categoría por su ID.
    // - findAll(): Devuelve todas las categorías en forma de lista.
    // - deleteById(): Elimina una categoría por su ID.
}