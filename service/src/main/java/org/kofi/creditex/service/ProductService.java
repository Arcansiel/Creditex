package org.kofi.creditex.service;

import org.kofi.creditex.model.Product;
import org.kofi.creditex.web.model.ProductForm;

import java.util.List;

public interface ProductService {
    Product GetProductById(int id);
    ProductForm GetProductFormById(int id);
    List<Product> GetProductsByActive(boolean active);
    List<ProductForm> GetProductFormsByActive(boolean active);
    int CreateProductByForm(ProductForm productForm);
    boolean SetProductIsActive(int id, boolean active);
}
