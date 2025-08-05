package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.Response;
import com.talleriv.Backend.dto.SupplierDTO;

public interface SupplierService {
    Response addSupplier(SupplierDTO supplierDTO);
    Response updateSupplier(Long id, SupplierDTO supplierDTO);
    Response getAllSuppliers();
    Response getSupplierById(Long id);
    Response deleteSupplier(Long id);
}