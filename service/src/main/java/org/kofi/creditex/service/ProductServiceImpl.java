package org.kofi.creditex.service;

import com.google.common.base.Function;
import org.kofi.creditex.model.PriorRepayment;
import org.kofi.creditex.model.Product;
import org.kofi.creditex.model.ProductType;
import org.kofi.creditex.model.QProduct;
import org.kofi.creditex.repository.ProductRepository;
import org.kofi.creditex.web.model.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    private Function<Product,ProductForm> productTransform = new Function<Product, ProductForm>() {
        @Nullable
        @Override
        public ProductForm apply(@Nullable Product product) {
            //assert product!=null;
            if(product == null){ return null; }
            return new ProductForm()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setActive(product.isActive())
                    .setType(product.getType().toString())
                    .setPercent(product.getPercent())
                    .setMinCommittee(product.getMinCommittee())
                    .setMinMoney(product.getMinMoney())
                    .setMaxMoney(product.getMaxMoney())
                    .setMinDuration(product.getMinDuration())
                    .setMaxDuration(product.getMaxDuration())
                    .setFinePercent(product.getDebtPercent())
                    .setPriorRepayment(product.getPrior().toString())
                    .setPriorRepaymentPercent(product.getPriorRepaymentPercent());
        }
    };

    private Function<ProductForm,Product> productFormTransform = new Function<ProductForm, Product>() {
        @Nullable
        @Override
        public Product apply(@Nullable ProductForm product) {
            //assert product!=null;
            if(product == null){ return null; }
            return new Product()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setActive(product.isActive())
                    .setType(ProductType.valueOf(product.getType()))
                    .setPercent(product.getPercent())
                    .setMinCommittee(product.getMinCommittee())
                    .setMinMoney(product.getMinMoney())
                    .setMaxMoney(product.getMaxMoney())
                    .setMinDuration(product.getMinDuration())
                    .setMaxDuration(product.getMaxDuration())
                    .setDebtPercent(product.getFinePercent())
                    .setPrior(PriorRepayment.valueOf(product.getPriorRepayment()))
                    .setPriorRepaymentPercent(product.getPriorRepaymentPercent());
        }
    };

    @Override
    public Product GetProductById(int id) {
        return productRepository.findOne(id);
    }

    @Override
    public ProductForm GetProductFormById(int id) {
        return productTransform.apply(GetProductById(id));
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
    public List<ProductForm> GetProductFormsByActive(boolean active) {
        List<ProductForm> list = new ArrayList<ProductForm>();
        for(Product p:productRepository.findAll(
                QProduct.product.active.eq(active),
                QProduct.product.name.asc()
        )){
            list.add(productTransform.apply(p));
        }
        return list;
    }

    @Override
    public int CreateProductByForm(ProductForm productForm) {
        assert productForm != null;
        productForm.setName(productForm.getName().trim());
        if(productRepository.count(
                QProduct.product.name.eq(productForm.getName())
        ) > 0){
            return -1;//already exists
        }
        Product product = productFormTransform.apply(productForm);
        productRepository.save(product);
        return 0;
    }

    @Override
    public boolean SetProductIsActive(int id, boolean active) {
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
