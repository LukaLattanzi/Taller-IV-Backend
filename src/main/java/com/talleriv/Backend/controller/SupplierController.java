package com.talleriv.Backend.controller;

import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.SupplierDTO;
import com.talleriv.Backend.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// SupplierController actúa como controlador REST para gestionar solicitudes relacionadas con proveedores.
// Este controlador permite realizar operaciones como creación, actualización, eliminación y lectura de proveedores.
@RestController
@RequestMapping("/api/suppliers") // Define el prefijo de ruta base para todas las operaciones relacionadas con proveedores.
@RequiredArgsConstructor // Genera automáticamente un constructor para los campos finales, como supplierService.
public class SupplierController {

    // Inyección del servicio SupplierService que contiene la lógica de negocio centrada en la gestión de proveedores.
    private final SupplierService supplierService;

    /**
     * Método para crear un nuevo proveedor.
     *
     * @param supplierDTO Objeto que contiene la información del proveedor a crear.
     *                    Este objeto debe cumplir las validaciones definidas en SupplierDTO.
     * @return Una ResponseEntity con un objeto Response que indica el resultado de la operación.
     */
    @PostMapping("/add") // Ruta POST para agregar un nuevo proveedor: /api/suppliers/add.
    @PreAuthorize("hasAuthority('ADMIN')") // Solo los usuarios con rol ADMIN pueden acceder a este método.
    public ResponseEntity<Response> addSupplier(@RequestBody @Valid SupplierDTO supplierDTO) {
        // Llama al servicio para agregar el proveedor y retorna el resultado.
        return ResponseEntity.ok(supplierService.addSupplier(supplierDTO));
    }

    /**
     * Método para obtener todos los proveedores registrados.
     *
     * @return Una ResponseEntity con un objeto Response que contiene la lista de proveedores.
     */
    @GetMapping("/all") // Ruta GET para obtener todos los proveedores: /api/suppliers/all.
    public ResponseEntity<Response> getAllSuppliers() {
        // Llama al servicio para obtener la lista completa de proveedores y retorna el resultado.
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    /**
     * Método para obtener un proveedor específico basado en su ID.
     *
     * @param id ID del proveedor que se desea consultar.
     * @return Una ResponseEntity con un objeto Response que contiene los detalles del proveedor solicitado.
     */
    @GetMapping("/{id}") // Ruta GET para obtener un proveedor por su ID: /api/suppliers/{id}.
    public ResponseEntity<Response> getSupplierById(@PathVariable Long id) {
        // Llama al servicio para consultar un proveedor específico por su ID.
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    /**
     * Método para actualizar los detalles de un proveedor existente.
     *
     * @param id          ID del proveedor a actualizar.
     * @param supplierDTO Objeto que contiene la información actualizada del proveedor. Debe ser válido (@Valid).
     * @return Una ResponseEntity con un objeto Response que indica el resultado de la operación de actualización.
     */
    @PutMapping("/update/{id}") // Ruta PUT para actualizar un proveedor: /api/suppliers/update/{id}.
    @PreAuthorize("hasAuthority('ADMIN')") // Solo los usuarios con rol ADMIN pueden acceder a este método.
    public ResponseEntity<Response> updateSupplier(@PathVariable Long id, @RequestBody @Valid SupplierDTO supplierDTO) {
        // Llama al servicio para realizar la actualización y retorna el resultado.
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
    }

    /**
     * Método para eliminar un proveedor basado en su ID.
     *
     * @param id ID del proveedor a eliminar.
     * @return Una ResponseEntity con un objeto Response que indica si el proveedor se eliminó correctamente.
     */
    @DeleteMapping("/delete/{id}") // Ruta DELETE para eliminar un proveedor: /api/suppliers/delete/{id}.
    @PreAuthorize("hasAuthority('ADMIN')") // Solo los usuarios con rol ADMIN pueden acceder a este método.
    public ResponseEntity<Response> deleteSupplier(@PathVariable Long id) {
        // Llama al servicio para realizar la eliminación del proveedor y retorna el resultado.
        return ResponseEntity.ok(supplierService.deleteSupplier(id));
    }
}