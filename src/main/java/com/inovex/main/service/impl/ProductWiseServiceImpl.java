package com.inovex.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Campaign;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ProductMapping;
import com.inovex.main.entity.ProductModel;
import com.inovex.main.entity.ProductWiseCampaign;
import com.inovex.main.json.response.ProductWiseCampaignResponse;
import com.inovex.main.repository.CampaignRepository;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.ProductMappingRepo;
import com.inovex.main.repository.ProductModelRepo;
import com.inovex.main.repository.ProductWiseCampaignRepository;
import com.inovex.main.service.ProductWiseCampaignService;

@Service
@Transactional
public class ProductWiseServiceImpl implements ProductWiseCampaignService {
    @Autowired
    ProductWiseCampaignRepository productWiseCampRepo;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    CampaignRepository campRepo;
    @Autowired
    ProductModelRepo proRepo;
    @Autowired
    ProductMappingRepo produtMapppingRepo;

    @Override
    public Optional<ProductWiseCampaign> findById(long id) {
        // TODO Auto-generated method stub
        return productWiseCampRepo.findById(id);
    }

    @Override
    public List<ProductWiseCampaign> findAll() {
        // TODO Auto-generated method stub
        return productWiseCampRepo.findAll();
    }

    @Override
    public ProductWiseCampaign getCampaign(long id) {
        // TODO Auto-generated method stub
        Optional<ProductWiseCampaign> campaign = productWiseCampRepo.findById(id);
        if (campaign.isPresent()) {
            return campaign.get();
        }
        throw new NotFoundException();
    }

    @Override
    public void deleteById(long id) {
        // TODO Auto-generated method stub
        productWiseCampRepo.deleteById(id);

    }

    @Override
    public ProductWiseCampaign save(ProductWiseCampaign entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        ProductWiseCampaign prdcamp = new ProductWiseCampaign();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<ProductWiseCampaign> prdcampList = new HashSet<ProductWiseCampaign>();
            if (org.isPresent()) {
                prdcampList = org.get().getProductWiseCampaigns();
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                entity = productWiseCampRepo.save(entity);
                prdcampList.add(entity);
                org.get().setProductWiseCampaigns(prdcampList);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("org is null");
        }

        return prdcamp;
    }

    @Override
    public Optional<ProductWiseCampaign> findByProductId(long productId) {
        // TODO Auto-generated method stub
        return productWiseCampRepo.findByProductId(productId);
    }

//    @Override
//    public Set<ProductWiseCampaignResponse> getAvailableCashBack(HttpServletRequest request) {
//        Set<ProductWiseCampaign> campaignList = new HashSet<ProductWiseCampaign>();
//        Set<ProductWiseCampaignResponse> cashBackcampaignList = new HashSet<ProductWiseCampaignResponse>();
//
//        if (request.getSession().getAttribute("orgId") != null) {
//            long orgId = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(orgId);
//            if (org.isPresent()) {
//                campaignList = org.get().getProductWiseCampaigns();
//                for (ProductWiseCampaign cbc : campaignList) {
//                    ProductWiseCampaignResponse cbcRes = new ProductWiseCampaignResponse();
//                    Optional<Campaign> camp = campRepo.findById(cbc.getCampaignId());
//                    if (camp.isPresent()) {
//                        if (camp.get().getStatus() == 1) {
//                            Optional<ProductModel> pro = proRepo.findById(cbc.getProductId());
//                            if (pro.isPresent()) {
//                                cbcRes.setProductName(pro.get().getProductName());
//                            } else {
//                                cbcRes.setProductName("Not Found");
//                            }
//
//                            cbcRes.setCampaignName(camp.get().getCampaignName());
//                            cbcRes.setCampaignId(cbc.getId());
//                            cbcRes.setId(cbc.getId());
//                            if (cbc.getOfferType().equals("value")) {
//                                cbcRes.setDiscountType(cbc.getDiscountType());
//                                cbcRes.setDiscountAmount(cbc.getDiscountAmount());
//                                cbcRes.setDiscountOn(cbc.getDiscountOn());
//                                cbcRes.setFreeProduct("n/a");
//                                cbcRes.setFreeItemQuantity(0);
//                            } else {
//                                cbcRes.setDiscountType("n/a");
//                                cbcRes.setDiscountAmount("n/a");
//                                cbcRes.setDiscountOn("n/a");
//                                cbcRes.setFreeProduct(Long.toString(cbc.getFreeProductId()));
//                                cbcRes.setFreeItemQuantity(cbc.getFreeItemQuantity());
//                            }
//
//                            cbcRes.setCampaignStartDate(camp.get().getStartTime());
//                            cbcRes.setCampaignEndDate(camp.get().getExpiredDate());
//                            cashBackcampaignList.add(cbcRes);
//
//                        }
//
//                    }
//
//                }
//            }
//        }
//        return cashBackcampaignList;
//    }
    
    @Override
    public Set<ProductWiseCampaignResponse> getAvailableCashBack(HttpServletRequest request) {
    	
    	Set<ProductWiseCampaign> campaignList = new HashSet<ProductWiseCampaign>();
    	
    	Set<ProductWiseCampaignResponse> productWisecampaignList = new HashSet<ProductWiseCampaignResponse>();
    	if (request.getSession().getAttribute("orgId")!= null) {
    		long orgId = (long) request.getSession().getAttribute("orgId");
    		Optional<Organization> org = orgRepo.findById(orgId);
    		if (org.isPresent()) {
    			//System.out.println(orgId);
				campaignList = org.get().getProductWiseCampaigns();
				
				for (ProductWiseCampaign pwc : campaignList) {
					//System.out.println("hello");
					ProductWiseCampaignResponse pwcRes = new ProductWiseCampaignResponse();
					Optional<Campaign> cam= campRepo.findById(pwc.getCampaignId());
					if(cam.isPresent()) {
						Date currentDate = new Date();
						//System.out.println(currentDate);
						///if (cam.get().getStartTime().compareTo(currentDate)<0 && cam.get().getExpiredDate().compareTo(currentDate)>0) {
							//System.out.println("yes campaign running");
							if (cam.get().getStatus() == 1 && cam.get().getExpiredDate().compareTo(currentDate)>0) {
								
								Optional<ProductMapping> product = produtMapppingRepo.findById(pwc.getProductId());

								if (product.isPresent()) {
									pwcRes.setProductName(product.get().getProductName());
									//System.out.println("yes");
								} else {
									pwcRes.setProductName("Not Found");

								}
								pwcRes.setCampaignName(cam.get().getCampaignName());
								pwcRes.setCampaignId(pwc.getId());
								pwcRes.setId(pwc.getId());
								if (pwc.getOfferType().equals("value")) {
									pwcRes.setDiscountType(pwc.getDiscountType());
									pwcRes.setDiscountAmount(pwc.getDiscountAmount());
									pwcRes.setDiscountOn(pwc.getDiscountOn());
									pwcRes.setFreeProduct("n/a");
									pwcRes.setRequiredQuantity(0);
									pwcRes.setFreeItemQuantity(0);
								} else {
									pwcRes.setDiscountType("n/a");
									pwcRes.setDiscountAmount("n/a");
									pwcRes.setDiscountOn("n/a");
									pwcRes.setRequiredQuantity(pwc.getRequiredQuantity());
									pwcRes.setFreeProduct(Long.toString(pwc.getFreeProductId()));
	                               pwcRes.setFreeItemQuantity(pwc.getFreeItemQuantity());

								}
								pwcRes.setCampaignStartDate(cam.get().getStartTime());
								pwcRes.setCampaignEndDate(cam.get().getExpiredDate());
								
								productWisecampaignList.add(pwcRes);
								
							}

						
						else {
							//System.out.println("no campaign not running");
						}
						
					}
				}
			}
			
		}
    	
        return productWisecampaignList;
    }

    @Override
    public List<ProductWiseCampaign> findAllByProductId(Long productId) {
        // TODO Auto-generated method stub
        List<ProductWiseCampaign> offer1 = new ArrayList<>();
        List<ProductWiseCampaign> offer = productWiseCampRepo.findAllByProductId(productId);
        for (ProductWiseCampaign productWiseCampaign : offer) {
            Optional<Campaign> cam = campRepo.findById(productWiseCampaign.getCampaignId());
            if (cam.isPresent()) {
                if (cam.get().getStatus() == 1) {
                    ProductWiseCampaign pc = new ProductWiseCampaign();
                    pc.setCampaignId(productWiseCampaign.getCampaignId());
                    pc.setProductId(productWiseCampaign.getProductId());
                    pc.setDiscountAmount(productWiseCampaign.getDiscountAmount());
                    pc.setDiscountOn(productWiseCampaign.getDiscountOn());
                    pc.setDiscountType(productWiseCampaign.getDiscountType());
                    pc.setOfferType(productWiseCampaign.getOfferType());
                    pc.setFreeItem(productWiseCampaign.getFreeItem());
                    pc.setFreeProductId(productWiseCampaign.getFreeProductId());
                    pc.setFreeItemQuantity(productWiseCampaign.getFreeItemQuantity());
                    pc.setQuantity(productWiseCampaign.getQuantity());
                    offer1.add(pc);
                }
            }
        }
        return offer1;
    }

    @Override
    public void deleteById(long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                productWiseCampRepo.deleteFromOrg(orgid, id);
                productWiseCampRepo.deleteById(id);

            } else {
                System.out.println("org not found");

            }

        }
    }

    @Override
    public ProductWiseCampaign update(ProductWiseCampaign productWiseCampaign, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        Optional<ProductWiseCampaign> productWiseCampaignUpdate = productWiseCampRepo.findById(id);
        productWiseCampaignUpdate.get().setCampaignId(productWiseCampaign.getCampaignId());
        productWiseCampaignUpdate.get().setProductId(productWiseCampaign.getProductId());
        productWiseCampaignUpdate.get().setOfferType(productWiseCampaign.getOfferType());
        productWiseCampaignUpdate.get().setDiscountType(productWiseCampaign.getDiscountType());
        productWiseCampaignUpdate.get().setDiscountAmount(productWiseCampaign.getDiscountAmount());
        productWiseCampaignUpdate.get().setDiscountOn(productWiseCampaign.getDiscountOn());
        productWiseCampaignUpdate.get().setRequiredQuantity(productWiseCampaign.getRequiredQuantity());// ());
        productWiseCampaignUpdate.get().setFreeItemQuantity(productWiseCampaign.getFreeItemQuantity());
        productWiseCampaignUpdate.get().setUpdatedAt(new Date());
        productWiseCampaignUpdate.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
        productWiseCampRepo.save(productWiseCampaignUpdate.get());
        return productWiseCampaignUpdate.get();

    }

    @Override
    public List<ProductWiseCampaign> saveListOfCampaign(List<ProductWiseCampaign> entities,
            HttpServletRequest request) {
        // TODO Auto-generated method stub
        List<ProductWiseCampaign> prdCampList = new ArrayList<ProductWiseCampaign>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<ProductWiseCampaign> prdcampListSet = new HashSet<ProductWiseCampaign>();
            if (org.isPresent()) {
                prdcampListSet = org.get().getProductWiseCampaigns();
                prdCampList = productWiseCampRepo.saveAll(entities);
                prdcampListSet.addAll(prdCampList);
                org.get().setProductWiseCampaigns(prdcampListSet);
                orgRepo.save(org.get());
            }
        }
        return prdCampList;
    }
}
