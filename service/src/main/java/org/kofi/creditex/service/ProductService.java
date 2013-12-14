package org.kofi.creditex.service;

import org.kofi.creditex.model.Product;

import java.util.List;

public interface ProductService {
    Product GetProductById(long id);
    List<Product> GetProductsByActive(boolean active);
    int CreateProductByForm(Product productForm);
    boolean SetProductIsActive(long id, boolean active);

}
