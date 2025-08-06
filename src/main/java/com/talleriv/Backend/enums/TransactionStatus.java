package com.talleriv.Backend.enums;

// Enum que representa los posibles estados de una transacción
public enum TransactionStatus {
    PENDING,    // La transacción está pendiente
    PROCESSING, // La transacción está en proceso
    COMPLETED,  // La transacción ha sido completada exitosamente
    CANCELED    // La transacción ha sido cancelada
}