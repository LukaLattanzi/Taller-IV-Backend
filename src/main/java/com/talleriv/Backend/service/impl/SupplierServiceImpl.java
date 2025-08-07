package com.talleriv.Backend.service.impl;

import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.SupplierDTO;
import com.talleriv.Backend.entity.Supplier;
import com.talleriv.Backend.exceptions.NotFoundException;
import com.talleriv.Backend.repository.SupplierRepository;
import com.talleriv.Backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La clase `SupplierServiceImpl` implementa la lógica de negocio relacionada con los proveedores dentro del sistema.
 * Utiliza la interfaz `SupplierService` para definir operaciones CRUD (Crear, Leer, Actualizar, Eliminar) que se aplican
 * sobre entidades de tipo `Supplier`.
 *
 * **Propósito General**:
 * - Administrar proveedores en el sistema mediante la implementación de operaciones estándar de negocio.
 * - Validar la información y manejar excepciones relacionadas, como la ausencia de un proveedor en la base de datos.
 * - Utilizar mappeo de datos entre entidades (Supplier) y DTOs (SupplierDTO) para mantener la separación entre las capas de datos y lógica.
 */

@Service // Declara esta clase como un servicio Spring.
@RequiredArgsConstructor // Genera automáticamente un constructor para las dependencias marcadas como `final`.
@Slf4j // Proporciona un mecanismo de registro para permitir la depuración.
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository; // Repositorio para interactuar con la base de datos de proveedores.
    private final ModelMapper modelMapper; // Herramienta para mapear objetos entre entidades y DTOs.

    /**
     * Agrega un nuevo proveedor al sistema.
     *
     * @param supplierDTO DTO que contiene los datos del proveedor.
     * @return Respuesta con estado y mensaje exitoso.
     *
     * **Proceso**:
     * 1. Convierte el objeto `SupplierDTO` en una entidad `Supplier`.
     * 2. Guarda la entidad en la base de datos utilizando el repositorio.
     * 3. Devuelve una respuesta indicando que la operación fue exitosa.
     */
    @Override
    public Response addSupplier(SupplierDTO supplierDTO) {
        Supplier supplierToSave = modelMapper.map(supplierDTO, Supplier.class);
        supplierRepository.save(supplierToSave);

        return Response.builder()
                .status(200)
                .message("Supplier creado exitosamente")
                .build();
    }

    /**
     * Actualiza un proveedor existente.
     *
     * @param id ID del proveedor a actualizar.
     * @param supplierDTO DTO con los datos actualizados del proveedor.
     * @return Respuesta con estado y mensaje exitoso.
     *
     * **Proceso**:
     * 1. Busca el proveedor en la base de datos por su ID.
     * 2. Si no se encuentra, lanza una excepción `NotFoundException`.
     * 3. Actualiza los valores modificables, como el nombre o la dirección.
     * 4. Guarda los cambios en la base de datos y devuelve una respuesta.
     */
    @Override
    public Response updateSupplier(Long id, SupplierDTO supplierDTO) {

        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier no encontrado"));

        if (supplierDTO.getName() != null) existingSupplier.setName(supplierDTO.getName());
        if (supplierDTO.getAddress() != null) existingSupplier.setAddress(supplierDTO.getAddress());

        supplierRepository.save(existingSupplier);

        return Response.builder()
                .status(200)
                .message("Supplier actualizado exitosamente")
                .build();
    }

    /**
     * Recupera todos los proveedores registrados.
     *
     * @return Respuesta con una lista de proveedores en formato DTO.
     *
     * **Proceso**:
     * 1. Obtiene todos los proveedores ordenados por ID en orden descendente.
     * 2. Convierte cada entidad `Supplier` a un objeto `SupplierDTO`.
     * 3. Devuelve la lista de proveedores encapsulada en un objeto de respuesta.
     */
    @Override
    public Response getAllSuppliers() {

        List<Supplier> suppliers = supplierRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<SupplierDTO> supplierDTOS = modelMapper.map(suppliers, new TypeToken<List<SupplierDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("Exito al recuperar los suppliers")
                .suppliers(supplierDTOS)
                .build();
    }

    /**
     * Recupera los datos de un proveedor específico.
     *
     * @param id ID del proveedor.
     * @return Respuesta con los detalles del proveedor en formato DTO.
     *
     * **Proceso**:
     * 1. Busca el proveedor en la base de datos por su ID.
     * 2. Si no se encuentra, lanza una excepción `NotFoundException`.
     * 3. Convierte la entidad `Supplier` a `SupplierDTO` y retorna la información dentro de un objeto de respuesta.
     */
    @Override
    public Response getSupplierById(Long id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier no encontrado"));

        SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);

        return Response.builder()
                .status(200)
                .message("Exito al recuperar el supplier")
                .supplier(supplierDTO)
                .build();
    }

    /**
     * Elimina un proveedor del sistema.
     *
     * @param id Identificador del proveedor.
     * @return Respuesta indicando que la eliminación fue exitosa.
     *
     * **Proceso**:
     * 1. Busca el proveedor por su ID.
     * 2. Si no se encuentra, lanza una excepción `NotFoundException`.
     * 3. Elimina el proveedor utilizando su ID.
     * 4. Devuelve una respuesta indicando que la eliminación fue exitosa.
     */
    @Override
    public Response deleteSupplier(Long id) {

        supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier no encontrado"));

        supplierRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Supplier eliminado exitosamente")
                .build();
    }
}