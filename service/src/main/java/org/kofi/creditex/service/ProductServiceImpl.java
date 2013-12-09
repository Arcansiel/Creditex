package org.kofi.creditex.service;

import com.google.common.base.Function;
import org.kofi.creditex.model.Product;
import org.kofi.creditex.web.model.ProductForm;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class ProductServiceImpl implements ProductService{
    Function<Product,ProductForm> productTransform = new Function<Product, ProductForm>() {
        @Nullable
        @Override
        public ProductForm apply(@Nullable Product product) {
            assert product!=null;
            // TODO create implementation
//            return new ProductForm()
//                    .setId()
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };
    @Override
    public Product GetProductById(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ProductForm GetProductFormById(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
