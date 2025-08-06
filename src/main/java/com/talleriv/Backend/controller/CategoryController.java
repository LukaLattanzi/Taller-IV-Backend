package com.talleriv.Backend.controller;

import com.talleriv.Backend.dto.CategoryDTO;
import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// CategoryController actúa como controlador REST para manejar las solicitudes relacionadas con categorías.
// Gestiona tareas como creación, actualización, eliminación y recuperación de categorías.
@RestController
@RequestMapping("/api/categories") // Define un prefijo de ruta común para el controlador.
@RequiredArgsConstructor // Genera automáticamente un constructor para los campos finales, como categoryService.
public class CategoryController {

    // Inyección de CategoryService que contiene la lógica de negocio para manejar las categorías.
    private final CategoryService categoryService;

    /**
     * Método para crear una nueva categoría.
     *
     * @param categoryDTO Objeto que contiene la información de la nueva categoría.
     *                    Este objeto debe ser válido (@Valid) según las reglas definidas en CategoryDTO.
     * @return Una ResponseEntity con un objeto Response indicando el resultado de la operación.
     */
    @PostMapping("/add") // Ruta POST para agregar una nueva categoría: /api/categories/add.
    @PreAuthorize("hasAuthority('ADMIN')") // Solo los usuarios con el rol ADMIN pueden acceder a este método.
    public ResponseEntity<Response> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        // categoryService.createCategory: Delegación de la lógica de creación al servicio.
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    /**
     * Método para obtener todas las categorías.
     *
     * @return Una ResponseEntity con un objeto Response que contiene la lista de todas las categorías.
     */
    @GetMapping("/all") // Ruta GET para obtener todas las categorías: /api/categories/all.
    public ResponseEntity<Response> getAllCategories() {
        // categoryService.getAllCategories: Llama al servicio para obtener todas las categorías.
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Método para obtener una categoría específica a partir de su ID.
     *
     * @param id ID de la categoría a buscar.
     * @return Una ResponseEntity con un objeto Response para la categoría buscada.
     */
    @GetMapping("/{id}") // Ruta GET para obtener una categoría por ID: /api/categories/{id}.
    public ResponseEntity<Response> getCategoryById(@PathVariable Long id) {
        // categoryService.getCategoryById: Consulta la categoría con el ID proporcionado.
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    /**
     * Método para actualizar una categoría existente.
     *
     * @param id          ID de la categoría a actualizar.
     * @param categoryDTO Objeto con los nuevos datos de la categoría. Debe ser válido (@Valid).
     * @return Una ResponseEntity con un objeto Response indicando el resultado de la actualización.
     */
    @PutMapping("/update/{id}") // Ruta PUT para actualizar una categoría: /api/categories/update/{id}.
    @PreAuthorize("hasAuthority('ADMIN')") // Solo los usuarios con el rol ADMIN pueden acceder a este método.
    public ResponseEntity<Response> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDTO categoryDTO) {
        // categoryService.updateCategory: Llama al servicio para actualizar la categoría.
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    /**
     * Método para eliminar una categoría específica.
     *
     * @param id ID de la categoría a eliminar.
     * @return Una ResponseEntity con un objeto Response indicando el estado de la eliminación.
     */
    @DeleteMapping("/delete/{id}") // Ruta DELETE para eliminar una categoría: /api/categories/delete/{id}.
    @PreAuthorize("hasAuthority('ADMIN')") // Solo los usuarios con el rol ADMIN pueden acceder a este método.
    public ResponseEntity<Response> deleteCategory(@PathVariable Long id) {
        // categoryService.deleteCategory: Llama al servicio para eliminar la categoría.
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}