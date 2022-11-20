package com.inovex.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.inovex.main.entity.Product;

public interface ProductService {
    Optional<Product> findById(Long id);

    List<Product> findAll();

    Product getProduct(Long id);

    Product save(Product product, HttpServletRequest request);

    void deleteById(long id,HttpServletRequest request);

    Product update(Product product, Long id, HttpServletRequest request);

    List<Object[]> getProductDetails(long disId);

    public List<Product> getproductById(long productCategory);

    public ArrayList<Object> getPagination(int start, int length, String searchParam);

    public Long getCountWithSearchParm(String searchParam);
    
    //public Product getProduct1(Long id);
    
   // public List<Product> findAllByProductId(Long productId);

}
