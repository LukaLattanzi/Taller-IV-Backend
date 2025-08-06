package com.talleriv.Backend.controller;

import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.TransactionRequest;
import com.talleriv.Backend.enums.TransactionStatus;
import com.talleriv.Backend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TransactionController actúa como controlador REST para manejar solicitudes relacionadas con transacciones.
// Proporciona operaciones para gestionar compras, ventas, devoluciones, consultas y actualizaciones de transacciones.
@RestController
@RequestMapping("/api/transactions") // Define un prefijo común para las rutas de este controlador.
@RequiredArgsConstructor // Genera automáticamente un constructor para los campos finales como transactionService.
public class TransactionController {

    // Inyección del servicio TransactionService que contiene la lógica de negocio relacionada con transacciones.
    private final TransactionService transactionService;

    /**
     * Crea una nueva transacción de reposición de inventario (compra).
     *
     * @param transactionRequest Objeto que contiene los detalles de la transacción.
     *                           Este objeto debe cumplir las validaciones definidas en TransactionRequest.
     * @return Una ResponseEntity con un objeto Response indicando el resultado de la compra.
     */
    @PostMapping("/purchase") // Ruta POST para registrar una transacción de tipo compra: /api/transactions/purchase.
    public ResponseEntity<Response> restockInventory(@RequestBody @Valid TransactionRequest transactionRequest) {
        // Llama al servicio para procesar la compra y retorna el resultado.
        return ResponseEntity.ok(transactionService.restockInventory(transactionRequest));
    }

    /**
     * Crea una nueva transacción de venta.
     *
     * @param transactionRequest Objeto con los detalles de la venta.
     * @return Una ResponseEntity con un objeto Response indicando el resultado de la venta.
     */
    @PostMapping("/sell") // Ruta POST para registrar una transacción de tipo venta: /api/transactions/sell.
    public ResponseEntity<Response> sell(@RequestBody @Valid TransactionRequest transactionRequest) {
        // Llama al servicio para procesar la venta y retorna el resultado.
        return ResponseEntity.ok(transactionService.sell(transactionRequest));
    }

    /**
     * Registra una devolución de inventario al proveedor.
     *
     * @param transactionRequest Objeto con los detalles de la devolución.
     * @return Una ResponseEntity con un objeto Response indicando el resultado de la devolución.
     */
    @PostMapping("/return") // Ruta POST para registrar una devolución: /api/transactions/return.
    public ResponseEntity<Response> returnToSupplier(@RequestBody @Valid TransactionRequest transactionRequest) {
        // Llama al servicio para procesar la devolución y retorna el resultado.
        return ResponseEntity.ok(transactionService.returnToSupplier(transactionRequest));
    }

    /**
     * Obtiene una lista paginada de todas las transacciones con un criterio opcional de búsqueda.
     *
     * @param page       Número de la página a consultar (por defecto: 0).
     * @param size       Tamaño de la página (por defecto: 1000).
     * @param searchText Texto para filtrar resultados (opcional).
     * @return Una ResponseEntity con un objeto Response que contiene la lista de transacciones.
     */
    @GetMapping("/all") // Ruta GET para obtener una lista de todas las transacciones: /api/transactions/all.
    public ResponseEntity<Response> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size,
            @RequestParam(required = false) String searchText
    ) {
        // Llama al servicio para obtener todas las transacciones con la paginación y el filtro indicados.
        return ResponseEntity.ok(transactionService.getAllTransactions(page, size, searchText));
    }

    /**
     * Obtiene los detalles de una transacción específica por su ID.
     *
     * @param id ID único de la transacción.
     * @return Una ResponseEntity con un objeto Response que contiene los detalles de la transacción solicitada.
     */
    @GetMapping("/{id}") // Ruta GET para obtener una transacción por ID: /api/transactions/{id}.
    public ResponseEntity<Response> getTransactionById(@PathVariable Long id) {
        // Llama al servicio para buscar la transacción por su ID.
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    /**
     * Obtiene una lista de transacciones realizadas en un mes y año específicos.
     *
     * @param month Mes en que se realizaron las transacciones.
     * @param year  Año en que se realizaron las transacciones.
     * @return Una ResponseEntity con un objeto Response que contiene la lista de transacciones encontradas.
     */
    @GetMapping("/by-month-year") // Ruta GET para obtener transacciones según mes y año: /api/transactions/by-month-year.
    public ResponseEntity<Response> getAllTransactionByMonthAndYear(
            @RequestParam int month,
            @RequestParam int year
    ) {
        // Llama al servicio para obtener las transacciones por el mes y año indicados.
        return ResponseEntity.ok(transactionService.getAllTransactionByMonthAndYear(month, year));
    }

    /**
     * Actualiza el estado de una transacción específica.
     *
     * @param transactionId ID único de la transacción a actualizar.
     * @param status        Nuevo estado para la transacción.
     * @return Una ResponseEntity con un objeto Response indicando el resultado de la actualización.
     */
    @PutMapping("/update/{transactionId}") // Ruta PUT para actualizar el estado de una transacción: /api/transactions/update/{transactionId}.
    public ResponseEntity<Response> updateTransactionStatus(
            @PathVariable Long transactionId,
            @RequestBody @Valid TransactionStatus status) {
        // Llama al servicio para actualizar el estado de la transacción.
        return ResponseEntity.ok(transactionService.updateTransactionStatus(transactionId, status));
    }
}