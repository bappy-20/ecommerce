package com.inovex.main.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.MeasurementUnit;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.repository.MeasurementUnitRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.PriceRepo;
import com.inovex.main.repository.PriceUpdateRepo;
import com.inovex.main.repository.PrimaryInventoryRepo;
import com.inovex.main.repository.ProductMappingRepo;
import com.inovex.main.repository.SecondaryInventoryRepo;
import com.inovex.main.service.ProductMappingService;

@Service
@Transactional
public class ProductMappingServiceImpl implements ProductMappingService {

    @Autowired
    ProductMappingRepo productRepo;
    @Autowired
    SecondaryInventoryRepo secondaryInventoryRepo;
    @Autowired
    PriceRepo priceRepo;
    @Autowired
    PriceUpdateRepo priceUpdateRepo;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    MeasurementUnitRepo measurementUnitRepo;
    @Autowired
    PrimaryInventoryRepo primaryInventoryRepo;

    @Override
    public Optional<ProductMapping> findById(Long id) {
        // TODO Auto-generated method stub
        return productRepo.findById(id);
    }

    @Override
    public List<ProductMapping> findAll() {
        // TODO Auto-generated method stub
        return productRepo.findAll();
    }

    @Override
    public ProductMapping getProduct(Long id) {
        // TODO Auto-generated method stub
        Optional<ProductMapping> product = productRepo.findById(id);

        return product.get();

    }

//    @Override
//    public ProductMapping save(ProductMapping product1, HttpServletRequest request) {
//        ProductMapping product = new ProductMapping();
//        if (request.getSession().getAttribute("orgId") != null) {
//            long id = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(id);
//            Set<ProductMapping> list = new HashSet<ProductMapping>();
//            if (org.isPresent()) {
//                list = org.get().getProductMapping();
//                product1.setActive(true);
//                String measureUnit = "";
//                Optional<MeasurementUnit> mesure = measurementUnitRepo
//                        .findById(Long.parseLong(product1.getMesuringType()));
//                if (mesure.isPresent()) {
//                    measureUnit = mesure.get().getName();
//                } else {
//                    measureUnit = "Unknown";
//                }
//                product1.setProductName(product1.getProductName() + " " + product1.getMesuringQuantity() + measureUnit);
//                product1.setCreatedAt(new Date());
//                product1.setUpdatedAt(new Date());
//                product1.setCreatedBy((long) request.getSession().getAttribute("userId"));
//                product = productRepo.save(product1);
//                list.add(product);
//                org.get().setProductMapping(list);
//                orgRepo.save(org.get());
//            }
//
//        }
//        return product;
//    }
    
    @Override
    public ProductMapping save(ProductMapping product1, HttpServletRequest request) {
    	Optional<ProductMapping> productMapping = productRepo.findByProductId(product1.getProductId());
    			if (productMapping.isPresent()) {
					System.out.println("This product is already mapped");
				} else {
					
					 ProductMapping product = new ProductMapping();
				        if (request.getSession().getAttribute("orgId") != null) {
				            long id = (long) request.getSession().getAttribute("orgId");
				            Optional<Organization> org = orgRepo.findById(id);
				            Set<ProductMapping> list = new HashSet<ProductMapping>();
				            if (org.isPresent()) {
				                list = org.get().getProductMapping();
				                product1.setActive(true);
				                String measureUnit = "";
				                Optional<MeasurementUnit> mesure = measurementUnitRepo
				                        .findById(Long.parseLong(product1.getMesuringType()));
				                if (mesure.isPresent()) {
				                    measureUnit = mesure.get().getName();
				                } else {
				                    measureUnit = "Unknown";
				                }
				                product1.setProductName(product1.getProductName() + " " + product1.getMesuringQuantity() +" "+ measureUnit);
				                product1.setCreatedAt(new Date());
				                product1.setUpdatedAt(new Date());
				                product1.setCreatedBy((long) request.getSession().getAttribute("userId"));
				                product = productRepo.save(product1);
				                list.add(product);
				                org.get().setProductMapping(list);
				                orgRepo.save(org.get());
				                updatePrimaryStock(product1);
				            }

				        }
				        return product;

				}
				return null;   	
       
    }
    
    private void updatePrimaryStock(ProductMapping productMapping2) {
    	
    	Optional<PrimaryInventory> primaryInventory = primaryInventoryRepo.findByProductId(productMapping2.getId());
    	
    	if (primaryInventory.isPresent()) {
    		long receiveInventory = primaryInventory.get().getReceivedInventory();
    		long onHand =primaryInventory.get().getOnHand();
    		long receiveNow = receiveInventory + productMapping2.getStartingStock();
    		long onHandNow = onHand+productMapping2.getStartingStock();
    		primaryInventory.get().setReceivedInventory(receiveNow);   		
    		primaryInventory.get().setOnHand(onHandNow);
    		primaryInventory.get().setUpdatedAt(new Date());
    		primaryInventoryRepo.save(primaryInventory.get());    		
			
		} else {
			PrimaryInventory pi = new PrimaryInventory();
			//pi.setProductId(productMapping2.getProductId());
			pi.setProductId(productMapping2.getId());
			pi.setStartingInventory(productMapping2.getStartingStock());
			pi.setSafetyStock(productMapping2.getSafetyStock());
			pi.setReceivedInventory(productMapping2.getStartingStock());
			pi.setShippedInventory(0l);
			pi.setMinimumQty(10l);
			pi.setOnHand(productMapping2.getStartingStock());
			pi.setActive(true);
			pi.setCreatedBy(1);
			pi.setCreatedAt(new Date());
			primaryInventoryRepo.save(pi);		
		}
    	
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
    	 if (request.getSession().getAttribute("orgId") != null) {
             long orgid = (long) request.getSession().getAttribute("orgId");

             Optional<Organization> org = orgRepo.findById(orgid);
             if (org.isPresent()) {
            	 deletePrimaryStock(id);
            	 productRepo.deleteFromOrg(orgid, id);
            	 productRepo.deleteById(id);
            	 
             }
         } else {
             System.out.println("org not found");
         }
       
    }
    private void deletePrimaryStock(Long id) {
    	Optional<ProductMapping> productMapping = productRepo.findById(id);
    	if (productMapping.isPresent()) {
    		Optional<PrimaryInventory> primaryInventory = primaryInventoryRepo.findByProductId(
    				productMapping.get().getProductId());
    		if (primaryInventory.isPresent()) {
    			primaryInventoryRepo.delete(primaryInventory.get());
			} else {
				System.out.println("Primary Inventory not found");
			}    				
			
		} else {
			System.out.println("Product mapping not found");
		}
		
	}

    @Override
    public ProductMapping update(ProductMapping product, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub

        Optional<ProductMapping> productUpdate = productRepo.findById(id);
        productUpdate.get().setProductId(product.getProductId());
        productUpdate.get().setProductName(product.getProductName()+" "+product.getMesuringQuantity());
        productUpdate.get().setSku(product.getSku());
        productUpdate.get().setBrandId(product.getBrandId());
        productUpdate.get().setProductCategory(product.getProductCategory());
        productUpdate.get().setProductSubCategory(product.getProductSubCategory());        
        productUpdate.get().setMesuringType(product.getMesuringType());
        productUpdate.get().setMesuringQuantity(product.getMesuringQuantity());
        productUpdate.get().setStartingStock(product.getStartingStock());
        productUpdate.get().setSafetyStock(product.getSafetyStock());
        productUpdate.get().setSupplierId(product.getSupplierId());
        productUpdate.get().setCompanyId(product.getCompanyId());
        productUpdate.get().setBarCode(product.getBarCode());
        productUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        ProductMapping pro1 = productRepo.save(productUpdate.get());
        return pro1;
    }

    @Override
    public List<ProductMapping> getproductById(long productCategory) {
        // TODO Auto-generated method stub
        return productRepo.getproductById(productCategory);
    }

    @Override
    public Optional<ProductMapping> findByProductId(Long productId) {
        // TODO Auto-generated method stub
        return productRepo.findByProductId(productId);
    }

	@Override
	public Long getProductId(Long id) {
		// TODO Auto-generated method stub
		 Optional<ProductMapping> product = productRepo.findById(id);

	        return product.get().getProductId();
	}

}
