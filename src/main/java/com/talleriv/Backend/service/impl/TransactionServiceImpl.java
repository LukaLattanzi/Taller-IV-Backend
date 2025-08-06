package com.talleriv.Backend.service.impl;

import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.TransactionDTO;
import com.talleriv.Backend.dto.TransactionRequest;
import com.talleriv.Backend.entity.Product;
import com.talleriv.Backend.entity.Supplier;
import com.talleriv.Backend.entity.Transaction;
import com.talleriv.Backend.entity.User;
import com.talleriv.Backend.enums.TransactionStatus;
import com.talleriv.Backend.enums.TransactionType;
import com.talleriv.Backend.exceptions.NameValueRequiredException;
import com.talleriv.Backend.exceptions.NotFoundException;
import com.talleriv.Backend.repository.ProductRepository;
import com.talleriv.Backend.repository.SupplierRepository;
import com.talleriv.Backend.repository.TransactionRepository;
import com.talleriv.Backend.service.TransactionService;
import com.talleriv.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * La clase `TransactionServiceImpl` implementa la lógica relacionada con las transacciones
 * de inventario del sistema, como compras, ventas y devoluciones.
 *
 * **Propósito General**:
 * - Gestionar operaciones de transacción sobre productos con sus respectivos usuarios y proveedores.
 * - Registrar entradas y salidas de inventario.
 * - Consultar y modificar transacciones existentes.
 * - Generar respuestas encapsuladas en un objeto `Response`.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    /**
     * Realiza una transacción de reposición de inventario (compra).
     *
     * @param transactionRequest Datos de la transacción a registrar.
     * @return Respuesta con estado exitoso.
     *
     * **Proceso**:
     * 1. Valida la existencia del proveedor.
     * 2. Recupera el producto y el proveedor de la base de datos.
     * 3. Actualiza el stock del producto.
     * 4. Registra una nueva transacción con tipo `PURCHASE`.
     */
    @Override
    public Response restockInventory(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if (supplierId == null) throw new NameValueRequiredException("Supplier Id id Required");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        User user = userService.getCurrentLoggedInUser();

        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.PURCHASE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Transaction Made Successfully")
                .build();
    }

    /**
     * Registra una venta de productos.
     *
     * @param transactionRequest Información de la transacción de venta.
     * @return Respuesta con estado exitoso.
     *
     * **Proceso**:
     * 1. Recupera el producto de la base de datos.
     * 2. Disminuye la cantidad en stock.
     * 3. Crea una transacción con tipo `SALE`.
     */
    @Override
    public Response sell(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Integer quantity = transactionRequest.getQuantity();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        User user = userService.getCurrentLoggedInUser();

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.SALE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Transaction Sold Successfully")
                .build();
    }

    /**
     * Registra una devolución de productos al proveedor.
     *
     * @param transactionRequest Datos de la devolución.
     * @return Respuesta con estado exitoso.
     *
     * **Proceso**:
     * 1. Recupera producto y proveedor.
     * 2. Disminuye la cantidad en stock.
     * 3. Registra una transacción con tipo `RETURN_TO_SUPPLIER`.
     */
    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if (supplierId == null) throw new NameValueRequiredException("Supplier Id id Required");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        User user = userService.getCurrentLoggedInUser();

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.RETURN_TO_SUPPLIER)
                .status(TransactionStatus.PROCESSING)
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(BigDecimal.ZERO)
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Transaction Returned Successfully Initialized")
                .build();
    }

    /**
     * Obtiene una lista paginada de transacciones, filtrada por texto si se proporciona.
     *
     * @param page Número de página.
     * @param size Tamaño de página.
     * @param searchText Texto de búsqueda opcional.
     * @return Respuesta con lista de transacciones en formato DTO.
     */
    @Override
    public Response getAllTransactions(int page, int size, String searchText) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Transaction> transactionPage = transactionRepository.searchTransactions(searchText, pageable);

        List<TransactionDTO> transactionDTOS = modelMapper
                .map(transactionPage.getContent(), new TypeToken<List<TransactionDTO>>() {}.getType());

        transactionDTOS.forEach(transactionDTOItem -> {
            transactionDTOItem.setUser(null);
            transactionDTOItem.setProduct(null);
            transactionDTOItem.setSupplier(null);
        });

        return Response.builder()
                .status(200)
                .message("success")
                .transactions(transactionDTOS)
                .build();
    }

    /**
     * Busca una transacción por su ID.
     *
     * @param id Identificador de la transacción.
     * @return Transacción encontrada en formato DTO.
     */
    @Override
    public Response getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction Not Found"));

        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
        transactionDTO.getUser().setTransactions(null); // Evita recursión al devolver el usuario

        return Response.builder()
                .status(200)
                .message("success")
                .transaction(transactionDTO)
                .build();
    }

    /**
     * Recupera todas las transacciones realizadas en un mes y año específico.
     *
     * @param month Mes (1 a 12).
     * @param year Año (ej: 2025).
     * @return Lista de transacciones en formato DTO.
     */
    @Override
    public Response getAllTransactionByMonthAndYear(int month, int year) {
        List<Transaction> transactions = transactionRepository.findAllByMonthAndYear(month, year);

        List<TransactionDTO> transactionDTOS = modelMapper
                .map(transactions, new TypeToken<List<TransactionDTO>>() {}.getType());

        transactionDTOS.forEach(transactionDTOItem -> {
            transactionDTOItem.setUser(null);
            transactionDTOItem.setProduct(null);
            transactionDTOItem.setSupplier(null);
        });

        return Response.builder()
                .status(200)
                .message("success")
                .transactions(transactionDTOS)
                .build();
    }

    /**
     * Actualiza el estado de una transacción específica.
     *
     * @param transactionId ID de la transacción.
     * @param transactionStatus Nuevo estado a aplicar.
     * @return Respuesta indicando actualización exitosa.
     */
    @Override
    public Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus) {
        Transaction existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction Not Found"));

        existingTransaction.setStatus(transactionStatus);
        existingTransaction.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(existingTransaction);

        return Response.builder()
                .status(200)
                .message("Transaction Status Successfully Updated")
                .build();
    }
}
