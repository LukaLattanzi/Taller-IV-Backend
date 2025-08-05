package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.TransactionRequest;
import com.talleriv.Backend.enums.TransactionStatus;

public interface TransactionService {
    Response restockInventory(TransactionRequest transactionRequest);
    Response sell(TransactionRequest transactionRequest);
    Response returnToSupplier(TransactionRequest transactionRequest);
    Response getAllTransactions(int page, int size, String searchText);
    Response getTransactionById(Long id);
    Response getAllTransactionByMonthAndYear(int month, int year);
    Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus);
}