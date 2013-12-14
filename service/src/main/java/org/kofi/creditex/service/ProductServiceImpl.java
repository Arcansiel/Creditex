package org.kofi.creditex.service;

import org.kofi.creditex.model.Product;
import org.kofi.creditex.model.QProduct;
import org.kofi.creditex.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product GetProductById(long id) {
        return productRepository.findOne(id);
    }

    @Override
    public List<Product> GetProductsByActive(boolean active) {
        List<Product> list = new ArrayList<Product>();
        for(Product p:productRepository.findAll(
                QProduct.product.active.eq(active),
                QProduct.product.name.asc()
        )){
            list.add(p);
        }
        return list;
    }

    @Override
    public int CreateProductByForm(Product productForm) {
        assert productForm != null;
        productForm.setName(productForm.getName().trim());
        if(productRepository.count(
                QProduct.product.name.eq(productForm.getName())
        ) > 0){
            return -1;//already exists
        }
        productRepository.save(productForm);
        return 0;
    }

    @Override
    public boolean SetProductIsActive(long id, boolean active) {
        Product product = productRepository.findOne(id);
        boolean r;
        if(r = (product != null)){
            if(product.isActive() != active){
                product.setActive(active);
                productRepository.save(product);
            }
        }
        return r;
    }


}
