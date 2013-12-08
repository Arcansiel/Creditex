package org.kofi.creditex.service;

import org.kofi.creditex.model.Product;
import org.kofi.creditex.web.model.ProductForm;

public interface ProductService {
    Product GetProductById(int id);
    ProductForm GetProductFormById(int id);
}
