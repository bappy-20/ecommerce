package com.inovex.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.Price;
import com.inovex.main.entity.PriceUpdateHistory;
import com.inovex.main.entity.Product;
import com.inovex.main.entity.SecondaryInventory;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.PriceRepo;
import com.inovex.main.repository.PriceUpdateRepo;
import com.inovex.main.repository.ProductRepo;
import com.inovex.main.repository.SecondaryInventoryRepo;
import com.inovex.main.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    SecondaryInventoryRepo secondaryInventoryRepo;
    @Autowired
    PriceRepo priceRepo;
    @Autowired
    PriceUpdateRepo priceUpdateRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<Product> findById(Long id) {
        // TODO Auto-generated method stub
        return productRepo.findById(id);
    }

    @Override
    public List<Product> findAll() {
        // TODO Auto-generated method stub
        return productRepo.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        // TODO Auto-generated method stub
        Optional<Product> product = productRepo.findById(id);

        return product.get();

    }

    @Override
    public Product save(Product product1, HttpServletRequest request) {
        Product product = new Product();
        /*
         * if (request.getSession().getAttribute("orgId") != null) { long id = (long)
         * request.getSession().getAttribute("orgId"); Optional<Organization> org =
         * orgRepo.findById(id); Set<Product> list = new HashSet<Product>();
         * 
         * if (org.isPresent()) { list = org.get().getProducts();
         * product1.setActive(true); product1.setCreatedAt(new Date());
         * product1.setUpdatedAt(new Date()); product1.setCreatedBy((long)
         * request.getSession().getAttribute("userId")); product =
         * productRepo.save(product1); if (product.getId() != 0) { SecondaryInventory si
         * = new SecondaryInventory(); si.setProductId(product.getId());
         * si.setStartingInventory(product.getStartingStock());
         * si.setReceivedInventory(product.getStartingStock());
         * si.setShippedInventory(0); si.setMinimumQty(50);
         * si.setOnHand(product.getStartingStock());
         * si.setSafetyStock(product.getSafetyStock()); si.setActive(true);
         * si.setCreatedAt(new Date()); si.setUpdatedAt(new Date());
         * si.setCreatedBy((long) request.getSession().getAttribute("userId"));
         * SecondaryInventory si1 = secondaryInventoryRepo.save(si); Price p1 = null; if
         * (si1 != null) { Optional<Price> p =
         * priceRepo.findByProductId(product.getId()); if (p.isPresent()) {
         * 
         * p.get().setProductPrice(product.getProductPrice());
         * p.get().setMrp(product.getProductMrpPrice());
         * p.get().setDealerPrice(product.getProductPrice()); //
         * p.setRetailPrice(product.getProductMrpPrice()); p.get().setActive(true);
         * p.get().setUpdatedAt(new Date()); p.get().setCreatedBy((long)
         * request.getSession().getAttribute("userId")); p1 = priceRepo.save(p.get()); }
         * else { Price p2 = new Price(); p2.setProductId(product.getId());
         * p2.setProductPrice(product.getProductPrice());
         * p2.setMrp(product.getProductMrpPrice());
         * p2.setDealerPrice(product.getProductPrice()); //
         * p.setRetailPrice(product.getProductMrpPrice()); p2.setActive(true);
         * p2.setCreatedAt(new Date()); p2.setUpdatedAt(new Date());
         * p2.setCreatedBy((long) request.getSession().getAttribute("userId")); p1 =
         * priceRepo.save(p2); }
         * 
         * }
         * 
         * if (p1 != null) { PriceUpdateHistory priceUpdate = new PriceUpdateHistory();
         * priceUpdate.setProductId(product.getId());
         * priceUpdate.setProductPrice(p1.getProductPrice());
         * priceUpdate.setMrp(p1.getMrp()); //
         * priceUpdate.setRetailPrice(p1.getRetailPrice());
         * priceUpdate.setDealerPrice(p1.getDealerPrice()); priceUpdate.setCreatedAt(new
         * Date()); priceUpdate.setCreatedBy((long)
         * request.getSession().getAttribute("userId")); priceUpdate.setUpdatedAt(new
         * Date()); priceUpdateRepo.save(priceUpdate); }
         * 
         * } list.add(product); org.get().setProducts(list); orgRepo.save(org.get()); }
         * 
         * }
         */
        return product;
    }

    @Override
    public void deleteById(long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                productRepo.deleteFromOrg(orgid, id);
                productRepo.findById(id);
            } else {

            }
        }
    }

    @Override
    public List<Object[]> getProductDetails(long disId) {
        // TODO Auto-generated method stub
        return productRepo.getProductDetails(disId);
    }

    @Override
    public Product update(Product product, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub

        Optional<Product> productUpdate = productRepo.findById(id);

        productUpdate.get().setProductName(product.getProductName());
        productUpdate.get().setSku(product.getSku());
        productUpdate.get().setProductLabel(product.getProductLabel());
        productUpdate.get().setProductCategory(product.getProductCategory());
        productUpdate.get().setShortDiscription(product.getShortDiscription());
        productUpdate.get().setMesuringType(product.getMesuringType());
        productUpdate.get().setProductPrice(product.getProductPrice());
        productUpdate.get().setProductMrpPrice(product.getProductMrpPrice());
        long prevStartingStock = productUpdate.get().getStartingStock();
        productUpdate.get().setStartingStock(product.getStartingStock());

        productUpdate.get().setSafetyStock(product.getSafetyStock());
        productUpdate.get().setDiscountType(product.getDiscountType());
        productUpdate.get().setAvailableDiscount(product.getAvailableDiscount());
        productUpdate.get().setDiscount(product.isDiscount());
        productUpdate.get().setAvailableOffer(product.getAvailableOffer());
        productUpdate.get().setOffer(product.isOffer());
        productUpdate.get().setProductImage(product.getProductImage());
        productUpdate.get().setSupplierId(product.getSupplierId());
        productUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        Product pro1 = productRepo.save(productUpdate.get());
        if (pro1.getId() != 0) {
            System.out.println(" pro1.getId() " + pro1.getId());
            Optional<SecondaryInventory> si = secondaryInventoryRepo.findByProductId(pro1.getId());
            if (si.isPresent()) {
                si.get().setProductId(product.getId());

                si.get().setStartingInventory(pro1.getStartingStock());
                long rcvInventory = si.get().getReceivedInventory() - prevStartingStock + pro1.getStartingStock();
                si.get().setReceivedInventory(rcvInventory);
                si.get().setShippedInventory(0l);
                si.get().setMinimumQty(50l);
                long onhan = si.get().getOnHand() - prevStartingStock + pro1.getStartingStock();
                si.get().setOnHand(onhan);

                si.get().setSafetyStock(pro1.getSafetyStock());
                si.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
                secondaryInventoryRepo.save(si.get());
            }
            Price p1 = null;
            Optional<Price> p = priceRepo.findByProductId(pro1.getId());
            if (p.isPresent()) {
                p.get().setProductId(pro1.getId());
                p.get().setProductPrice(product.getProductPrice());
                p.get().setMrp(product.getProductMrpPrice());
                p.get().setDealerPrice(product.getProductPrice());
                // p.get().setRetailPrice(product.getProductMrpPrice());
                p.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
                p1 = priceRepo.save(p.get());
            }
            if (p1 != null) {
                PriceUpdateHistory priceUpdate = new PriceUpdateHistory();
                priceUpdate.setProductId(pro1.getId());
                priceUpdate.setProductPrice(p1.getProductPrice());
                priceUpdate.setMrp(p1.getMrp());
                // priceUpdate.get().setRetailPrice(p1.getRetailPrice());
                priceUpdate.setDealerPrice(p1.getDealerPrice());
                priceUpdate.setCreatedAt(new Date());
                priceUpdate.setCreatedBy((long) request.getSession().getAttribute("userId"));
                priceUpdate.setUpdatedAt(new Date());
                priceUpdateRepo.save(priceUpdate);
            }
            return pro1;
        } else {
            throw new NullPointerException("Product not found!");
        }

    }

    @Override
    public List<Product> getproductById(long productCategory) {
        // TODO Auto-generated method stub
        return productRepo.getproductById(productCategory);
    }

    @Override
    public ArrayList<Object> getPagination(int start, int length, String searchParam) {
        if (searchParam == null || searchParam.isEmpty()) {
            return productRepo.getPagination(start, length);
        } else {
            return productRepo.getPaginationWithSerachParam(searchParam, start, length);
        }
    }

    @Override
    public Long getCountWithSearchParm(String searchParam) {
        if (searchParam == null || searchParam.isEmpty()) {
            return productRepo.count();
        } else {

            return productRepo.countBySearchParam(searchParam);

        }
    }
}
