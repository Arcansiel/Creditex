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

    @Autowired
    DayReportService dayReportService;

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

    private int ValidateProductForm(Product product){
        if("".equals(product.getName().trim())){
            return -1;//product name is whitespace
        }
        if(product.getMinMoney() < 0 || product.getMaxMoney() < 0 || product.getMinCommittee() < 0
                || product.getMinMoney() > product.getMaxMoney()){
            return -2;//invalid minMoney, maxMoney or minCommittee values
        }
        if(product.getMinDuration() < 0 || product.getMaxDuration() < 0
                || product.getMinDuration() > product.getMaxDuration()){
            return -3;//invalid minDuration or maxDuration values
        }
        if(product.getPercent() < 0 || product.getDebtPercent() < 0 || product.getPriorRepaymentPercent() < 0){
            return -4;//invalid percent value
        }
        return 0;//valid
    }

    @Override
    public int CreateProductByForm(Product productForm) {
        assert productForm != null;
        assert productForm.getName() != null;
        int err;
        if((err = ValidateProductForm(productForm)) < 0){
            return err;//invalid input data
        }
        if(productRepository.count(
                QProduct.product.name.eq(productForm.getName())
        ) > 0){
            return -5;//already exists
        }
        productForm.setName(productForm.getName().trim());
        productRepository.save(productForm);
        dayReportService.IncProduct();
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
