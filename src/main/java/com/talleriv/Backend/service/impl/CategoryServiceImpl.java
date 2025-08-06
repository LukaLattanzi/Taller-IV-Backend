package com.talleriv.Backend.service.impl;

import com.talleriv.Backend.dto.CategoryDTO;
import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.entity.Category;
import com.talleriv.Backend.exceptions.NotFoundException;
import com.talleriv.Backend.repository.CategoryRepository;
import com.talleriv.Backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La clase `CategoryServiceImpl` implementa la lógica de negocio para la gestión de categorías en el sistema.
 * Se trata de un componente de servicio anotado con `@Service` en un entorno Spring, lo que indica que será usado
 * como parte del manejo de la lógica en la aplicación.
 *
 * **Objetivo principal**:
 * - Administrar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) relacionadas con las entidades categoría.
 * - Actuar como intermediario entre la capa de persistencia (`CategoryRepository`) y los controladores
 *   que manejan las solicitudes del cliente.
 *
 * **Características principales**:
 * - Utiliza la anotación `@RequiredArgsConstructor` para la inyección de dependencias de forma implícita.
 * - Maneja excepciones específicas como `NotFoundException` para mejorar el control de errores.
 * - Realiza conversiones entre entidades (`Category`) y DTOs (`CategoryDTO`) usando `ModelMapper`.
 * - Devuelve respuestas estandarizadas encapsuladas en objetos de tipo `Response`.
 */

// Anotaciones de configuración de Spring
@Service // Anotación que marca esta clase como un servicio a ser gestionado por el contexto de Spring.
@RequiredArgsConstructor // Genera un constructor con los atributos finales marcados como `private final`.
@Slf4j // Proporciona un logger para facilitar el registro de información y errores.

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository; // Repositorio para interactuar con la base de datos.
    private final ModelMapper modelMapper; // Herramienta para mapear entidades a DTOs y viceversa.

    /**
     * Método para crear una nueva categoría.
     *
     * @param categoryDTO Objeto que contiene los datos de la categoría a crear.
     * @return Un objeto `Response` que indica el estado de la operación.
     *
     * **Proceso**:
     * 1. Convierte el `CategoryDTO` recibido a una entidad `Category` usando `ModelMapper`.
     * 2. Guarda la nueva categoría en la base de datos mediante `categoryRepository`.
     * 3. Retorna una respuesta exitosa.
     */
    @Override
    public Response createCategory(CategoryDTO categoryDTO) {
        Category categoryToSave = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(categoryToSave);

        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    /**
     * Método para recuperar todas las categorías ordenadas por 'id' en orden descendente.
     *
     * @return Una respuesta que contiene la lista de categorías disponibles en el sistema.
     *
     * **Proceso**:
     * 1. Recupera todas las categorías de la base de datos usando el repositorio con ordenamiento.
     * 2. Convierte las entidades recuperadas a una lista de `CategoryDTO` usando `ModelMapper`.
     * 3. Devuelve esta lista encapsulada en un objeto `Response`.
     */
    @Override
    public Response getAllCategories() {

        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<CategoryDTO> categoryDTOS = modelMapper.map(categories, new TypeToken<List<CategoryDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .categories(categoryDTOS)
                .build();
    }

    /**
     * Método para obtener una categoría específica basada en su identificador.
     *
     * @param id Identificador único de la categoría.
     * @return Respuesta que contiene los detalles de la categoría solicitada.
     *
     * **Proceso**:
     * 1. Busca la categoría en la base de datos mediante su ID.
     * 2. Si no se encuentra, lanza una excepción `NotFoundException`.
     * 3. Convierte la entidad encontrada a un DTO.
     * 4. Devuelve el DTO dentro de una respuesta.
     */
    @Override
    public Response getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);

        return Response.builder()
                .status(200)
                .message("success")
                .category(categoryDTO)
                .build();
    }

    /**
     * Método para actualizar una categoría existente.
     *
     * @param id          Identificador de la categoría a actualizar.
     * @param categoryDTO Detalles actualizados de la categoría.
     * @return Respuesta indicando el estado de la operación.
     *
     * **Proceso**:
     * 1. Recupera la categoría existente por su ID.
     * 2. Si no existe, lanza una `NotFoundException`.
     * 3. Actualiza el nombre de la categoría con el valor del DTO.
     * 4. Guarda la categoría actualizada en la base de datos.
     * 5. Devuelve una respuesta indicativa de éxito.
     */
    @Override
    public Response updateCategory(Long id, CategoryDTO categoryDTO) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);

        return Response.builder()
                .status(200)
                .message("Category Successfully Updated")
                .build();
    }

    /**
     * Método para eliminar una categoría basada en su identificador.
     *
     * @param id Identificador de la categoría a eliminar.
     * @return Respuesta confirmando la eliminación.
     *
     * **Proceso**:
     * 1. Recupera la categoría por su ID.
     * 2. Si no se encuentra, lanza una `NotFoundException`.
     * 3. Elimina la categoría de la base de datos usando el repositorio.
     * 4. Devuelve una respuesta de confirmación.
     */
    @Override
    public Response deleteCategory(Long id) {

        categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        categoryRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Category Successfully Deleted")
                .build();
    }
}
