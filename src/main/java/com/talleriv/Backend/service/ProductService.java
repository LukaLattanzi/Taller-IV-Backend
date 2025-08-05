package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.ProductDTO;
import com.talleriv.Backend.dto.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Response saveProduct(ProductDTO productDTO, MultipartFile imageFile);
    Response updateProduct(ProductDTO productDTO, MultipartFile imageFile);
    Response getAllProducts();
    Response getProductById(Long id);
    Response deleteProduct(Long id);
}