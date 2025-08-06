package com.talleriv.Backend.enums;

// Enum que representa los tipos de transacciones posibles
public enum TransactionType {
    PURCHASE, // La transacción es una compra de inventario
    SALE,  // La transacción es una venta de inventario
    RETURN_TO_SUPPLIER // La transacción es una devolución al proveedor
}