package com.talleriv.Backend.repository;

import com.talleriv.Backend.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Esta interfaz actúa como el repositorio para la entidad Transaction.
// JpaRepository proporciona métodos predefinidos para operaciones CRUD (Create, Read, Update, Delete)
// y consultas más avanzadas, como la paginación, sin implementar lógica personalizada.
// TransactionRepository también incluye consultas específicas para obtener transacciones de acuerdo con criterios personalizados.
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Encuentra todas las transacciones que se realizaron en un mes y año específicos.
     *
     * @param month Mes en que se realizaron las transacciones (por ejemplo, 1 para enero).
     * @param year  Año en que se realizaron las transacciones.
     * @return Lista de transacciones realizadas en el mes y año proporcionados.
     */
    @Query("SELECT t FROM Transaction t " +
            "WHERE YEAR(t.createdAt) = :year AND MONTH(t.createdAt) = :month")
    List<Transaction> findAllByMonthAndYear(@Param("month") int month, @Param("year") int year);

    /**
     * Busca transacciones basándose en un texto de búsqueda y con soporte para paginación.
     * Los campos consultados son:
     * - Descripción de la transacción
     * - Estado de la transacción
     * - Nombre del producto asociado a la transacción
     * - SKU del producto asociado a la transacción
     *
     * @param searchText Texto usado para buscar coincidencias (puede ser parcial).
     * @param pageable   Parámetro para soportar la paginación y ordenar los resultados.
     * @return Página de transacciones que coinciden con el texto de búsqueda.
     */
    @Query("SELECT t FROM Transaction t " +
            "LEFT JOIN t.product p " + // Realiza un LEFT JOIN con la entidad Product
            "WHERE (:searchText IS NULL OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(t.status) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(p.sku) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Transaction> searchTransactions(@Param("searchText") String searchText, Pageable pageable);
}