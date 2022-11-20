package com.inovex.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Distributor;
import com.inovex.main.entity.DistributorRequisition;
import com.inovex.main.entity.DistributorRequisitionAccounceApprove;
import com.inovex.main.entity.DistributorRequisitionDelivered;
import com.inovex.main.entity.DistributorRequisitionFreeProduct;
import com.inovex.main.entity.DistributorRequisitionOperationApprove;
import com.inovex.main.entity.DistributorRequisitionProduct;
import com.inovex.main.entity.DistributorRequisitionReceive;
import com.inovex.main.entity.DistributorRequisitionResponse;
import com.inovex.main.entity.DistributorRequisitionSupplyChainApprove;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.entity.ProductWiseCampaign;
import com.inovex.main.entity.SecondaryInventory;
import com.inovex.main.json.response.CashBackCampaignResponse;
import com.inovex.main.repository.CampaignRepository;
import com.inovex.main.repository.CashBackCampaignRepo;
import com.inovex.main.repository.DistributorRepo;
import com.inovex.main.repository.DistributorRequisitionRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.PrimaryInventoryRepo;
import com.inovex.main.repository.ProductWiseCampaignRepository;
import com.inovex.main.repository.SecondaryInventoryRepo;
import com.inovex.main.service.CashBackCampaignService;
import com.inovex.main.service.DistributorRequisitionService;
import com.inovex.main.service.UserService;

@Service
@Transactional
public class DistributorRequisitionServiceImpl implements DistributorRequisitionService {

    @Autowired
    DistributorRequisitionRepo dealerRequisitionRepo;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    CampaignRepository campRepo;
    @Autowired
    ProductWiseCampaignRepository productWiseCampRepo;
    @Autowired
    CashBackCampaignRepo cashBackCampaignRepo;
    @Autowired
    DistributorRepo distributorRepo;
    @Autowired
    CashBackCampaignService campaignService;
    @Autowired
    DistributorRequisitionRepo distributorRequisitionRepo;
    @Autowired
    SecondaryInventoryRepo secondaryRepo;
    @Autowired
    PrimaryInventoryRepo primaryInventoryRepo;
    
    @Autowired
    UserService userService;

    @Override
    public Optional<DistributorRequisition> findById(Long id) {
        // TODO Auto-generated method stub
        return dealerRequisitionRepo.findById(id);
    }

    @Override
    public List<DistributorRequisition> findAll() {
        // TODO Auto-generated method stub
        return dealerRequisitionRepo.findAll();
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            dealerRequisitionRepo.deleteFromOrg(orgId, id);
            dealerRequisitionRepo.deleteById(id);
        }

    }

    @Override
    public DistributorRequisition update(DistributorRequisition entity, Long id, HttpServletRequest request) {
        Optional<DistributorRequisition> distReq = dealerRequisitionRepo.findById(id);
        long netTotal = 0;
        long netDiscount = 0;
        long netGrandTotal = 0;
        if (distReq.isPresent()) {
            Set<DistributorRequisitionProduct> requiredProduct = entity.getDistributorRequiredProduct();
            for (DistributorRequisitionProduct distProduct : requiredProduct) {
                netTotal += Long.parseLong(distProduct.getTotalPrice());
                netDiscount += Long.parseLong(distProduct.getDiscount());
                netGrandTotal += Long.parseLong(distProduct.getGrandTotal());
            }
            Set<CashBackCampaignResponse> cashBackcampaignList = campaignService.getAvailableCashBack(request);
            for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                    if (cbc.getDiscountType().equals("BDT")) {
                        netDiscount += Long.parseLong(cbc.getDiscountAmount());
                        netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                    } else {
                        long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                        netDiscount += discount;
                        netGrandTotal -= discount;
                    }

                }
            }
            distReq.get().setTotalPrice(Long.toString(netTotal));
            distReq.get().setStatus(entity.getStatus());
            distReq.get().setGrandTotal(Long.toString(netGrandTotal));
            distReq.get().setNetDiscount(Long.toString(netDiscount));
            distReq.get().setUpdatedAt(new Date());
            distReq.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
            distReq.get().setChildren(entity.getDistributorRequiredProduct());
            distReq.get().setFreeProduct(entity.getFreeProductlist());
            dealerRequisitionRepo.save(distReq.get());
        }
        return distReq.get();
    }

    @Override
    public DistributorRequisition save(DistributorRequisition entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        DistributorRequisition distributor = new DistributorRequisition();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
            if (org.isPresent()) {
                list = org.get().getDistributorRequisition();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                distributor = dealerRequisitionRepo.save(entity);
                list.add(distributor);
                org.get().setDistributorRequisition(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return distributor;
    }

    @Override
    public List<DistributorRequisitionResponse> findAllResponse(HttpServletRequest request) {
        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();

            if (org.isPresent()) {
                /*
                 * list = org.get().getDistributorRequisition().stream() .filter(p ->
                 * p.getStatus().equals("1") || p.getStatus().equals("0"))
                 * .collect(Collectors.toSet());
                 */
                list = org.get().getDistributorRequisition();
                for (DistributorRequisition distributorRequisition : list) {
                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
                    res.setId(distributorRequisition.getId());
                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
                    if (dis.isPresent()) {
                        res.setDistName(dis.get().getDistributorName());
                    } else {
                        res.setDistName("Not Found");
                    }
                    /*
                     * res.setTotalPrice(distributorRequisition.getTotalPrice());
                     * res.setNetDiscount(distributorRequisition.getNetDiscount());
                     * res.setGrandTotal(distributorRequisition.getGrandTotal());
                     */
                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
                    if (distributorRequisition.getStatus().equals("0")) {
                        res.setTotalPrice(distributorRequisition.getTotalPrice());
                        res.setNetDiscount(distributorRequisition.getNetDiscount());
                        res.setGrandTotal(distributorRequisition.getGrandTotal());
                        res.setStatus("Requisition_Placed");
                    }
                    if (distributorRequisition.getStatus().equals("1")) {
                        res.setStatus("Confirmed_By_Distributor");
                        res.setTotalPrice(distributorRequisition.getTotalPrice());
                        res.setNetDiscount(distributorRequisition.getNetDiscount());
                        res.setGrandTotal(distributorRequisition.getGrandTotal());
                    }
                    if (distributorRequisition.getStatus().equals("2")) {
                        res.setStatus("Approved_By_SupplyChain");
                        res.setTotalPrice(distributorRequisition.getTotalPriceOfSupplyChain());
                        res.setNetDiscount(distributorRequisition.getNetDiscountOfSupplyChain());
                        res.setGrandTotal(distributorRequisition.getGrandTotalOfSupplyChain());
                    }
                    if (distributorRequisition.getStatus().equals("3")) {
                        res.setStatus("Approved_By_Accounce");
                        res.setTotalPrice(distributorRequisition.getTotalPriceOfAccounce());
                        res.setNetDiscount(distributorRequisition.getNetDiscountOfAccounce());
                        res.setGrandTotal(distributorRequisition.getGrandTotalOfAccounce());
                    }
                    if (distributorRequisition.getStatus().equals("4")) {
                        res.setStatus("Approved_By_Operation");
                        res.setTotalPrice(distributorRequisition.getTotalPriceOfOperation());
                        res.setNetDiscount(distributorRequisition.getNetDiscountOfOperation());
                        res.setGrandTotal(distributorRequisition.getGrandTotalOfOperation());
                    }
                    if (distributorRequisition.getStatus().equals("5")) {
                        res.setStatus("Delivered");
                        res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
                        res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
                        res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
                    }
                    if (distributorRequisition.getStatus().equals("6")) {
                        res.setStatus("Received");
                        res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
                        res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
                        res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
                    }
                    responeList.add(res);
                }
            }
        }
        return responeList;
    }

    @Override
    public List<DistributorRequisitionResponse> findAllConfrimRequiResponse(HttpServletRequest request) {
        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
            if (org.isPresent()) {
                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("1"))
                        .collect(Collectors.toSet());
                for (DistributorRequisition distributorRequisition : list) {
                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
                    res.setId(distributorRequisition.getId());
                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
                    if (dis.isPresent()) {
                        res.setDistName(dis.get().getDistributorName());
                    } else {
                        res.setDistName("Not Found");
                    }
                    res.setTotalPrice(distributorRequisition.getTotalPrice());
                    res.setNetDiscount(distributorRequisition.getNetDiscount());
                    res.setGrandTotal(distributorRequisition.getGrandTotal());
                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
                    if (distributorRequisition.getStatus().equals("1")) {
                        res.setStatus("Confirmed_By_Distributor");
                    }

                    responeList.add(res);
                }
            }
        }
        return responeList;
    }

    @Override
    public List<DistributorRequisitionResponse> findAllProcessedRequisitionResponse(HttpServletRequest request) {
        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
            if (org.isPresent()) {
                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("2"))
                        .collect(Collectors.toSet());
                for (DistributorRequisition distributorRequisition : list) {
                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
                    res.setId(distributorRequisition.getId());
                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
                    if (dis.isPresent()) {
                        res.setDistName(dis.get().getDistributorName());
                    } else {
                        res.setDistName("Not Found");
                    }
                    res.setTotalPrice(distributorRequisition.getTotalPriceOfSupplyChain());
                    res.setNetDiscount(distributorRequisition.getNetDiscountOfSupplyChain());
                    res.setGrandTotal(distributorRequisition.getGrandTotalOfSupplyChain());
                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
                    if (distributorRequisition.getStatus().equals("2")) {
                        res.setStatus("Approved_By_SupplyChain");
                    }

                    responeList.add(res);
                }
            }
        }
        return responeList;
    }
    
    //for forward  310 line number

    @Override
    public DistributorRequisition update(Long reqId, String dept) {
        // TODO Auto-generated method stub
        Optional<DistributorRequisition> distReq = dealerRequisitionRepo.findById(reqId);
        if (distReq.isPresent()) {
            if (dept.equals("supplychain")) {
                distReq.get().setStatus("1");
            }
            if (dept.equals("account")) {
                distReq.get().setStatus("2");
            }
            if (dept.equals("operation")) {
                distReq.get().setStatus("3");
            }
            return dealerRequisitionRepo.save(distReq.get());

        } else {
            return null;
        }

    }

    @Override
    public List<DistributorRequisitionResponse> findAllApprovedRequisitionResponse(HttpServletRequest request) {
        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
            if (org.isPresent()) {
                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("3"))
                        .collect(Collectors.toSet());
                for (DistributorRequisition distributorRequisition : list) {
                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
                    res.setId(distributorRequisition.getId());
                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
                    if (dis.isPresent()) {
                        res.setDistName(dis.get().getDistributorName());
                    } else {
                        res.setDistName("Not Found");
                    }
                    res.setTotalPrice(distributorRequisition.getTotalPriceOfAccounce());
                    res.setNetDiscount(distributorRequisition.getNetDiscountOfAccounce());
                    res.setGrandTotal(distributorRequisition.getGrandTotalOfAccounce());
                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
                    if (distributorRequisition.getStatus().equals("3")) {
                        res.setStatus("Approved_By_Accounce");
                    }

                    responeList.add(res);
                }
            }
        }
        return responeList;
    }

    @Override
    public List<DistributorRequisitionResponse> findAllVerifiedRequisitionResponse(HttpServletRequest request) {
        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
            if (org.isPresent()) {
                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("4"))
                        .collect(Collectors.toSet());
                for (DistributorRequisition distributorRequisition : list) {
                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
                    res.setId(distributorRequisition.getId());
                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
                    if (dis.isPresent()) {
                        res.setDistName(dis.get().getDistributorName());
                    } else {
                        res.setDistName("Not Found");
                    }
                    res.setTotalPrice(distributorRequisition.getTotalPriceOfOperation());
                    res.setNetDiscount(distributorRequisition.getNetDiscountOfOperation());
                    res.setGrandTotal(distributorRequisition.getGrandTotalOfOperation());
                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
                    if (distributorRequisition.getStatus().equals("4")) {
                        res.setStatus("Approved_By_Operation");
                    }

                    responeList.add(res);
                }
            }
        }
        return responeList;
    }

    @Override
    public List<DistributorRequisitionResponse> findAllDeliveredRequisitionResponse(HttpServletRequest request) {
        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
            if (org.isPresent()) {
                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("5"))
                        .collect(Collectors.toSet());
                for (DistributorRequisition distributorRequisition : list) {
                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();

                    res.setId(distributorRequisition.getId());
                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
                    if (dis.isPresent()) {
                        res.setDistName(dis.get().getDistributorName());
                    } else {
                        res.setDistName("Not Found");
                    }
                    res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
                    res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
                    res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
                    if (distributorRequisition.getStatus().equals("5")) {
                        res.setStatus("Delivered");
                    }

                    responeList.add(res);
                }
            }
        }
        return responeList;
    }

    @Override
    public List<DistributorRequisitionResponse> findAllReceivedRequisitionResponse(HttpServletRequest request) {
        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
            if (org.isPresent()) {
                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("6"))
                        .collect(Collectors.toSet());
                for (DistributorRequisition distributorRequisition : list) {
                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();

                    res.setId(distributorRequisition.getId());
                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
                    if (dis.isPresent()) {
                        res.setDistName(dis.get().getDistributorName());
                    } else {
                        res.setDistName("Not Found");
                    }
                    res.setTotalPrice(distributorRequisition.getTotalPriceOfDelivery());
                    res.setNetDiscount(distributorRequisition.getNetDiscountOfDelivery());
                    res.setGrandTotal(distributorRequisition.getGrandTotalOfDelivery());
                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
                    if (distributorRequisition.getStatus().equals("6")) {
                        res.setStatus("Received");
                    }

                    responeList.add(res);
                }
            }
        }
        return responeList;
    }
    
    
    
    
    //460 number line for confirmed

    @Override
    public DistributorRequisition updateByOtherOperation(DistributorRequisition entity, Long id,
            HttpServletRequest request) {
        DistributorRequisition dis = new DistributorRequisition();
        Set<DistributorRequisitionFreeProduct> freeProductlist = new HashSet<>();
        long netTotal = 0;
        long netDiscount = 0;
        long netGrandTotal = 0;
        Optional<DistributorRequisition> distReq = dealerRequisitionRepo.findById(id);
        if (distReq.isPresent()) {
            distReq.get().setStatus(entity.getStatus());
            distReq.get().setUpdatedAt(new Date());
            distReq.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
            if (entity.getStatus().equals("2")) {
                distReq.get().setSupplyChainApprove(entity.getRequisitionSupplyChainApprove());
                Set<DistributorRequisitionSupplyChainApprove> requiredProduct = entity
                        .getRequisitionSupplyChainApprove();
                for (DistributorRequisitionSupplyChainApprove distProduct : requiredProduct) {
                    netTotal += Long.parseLong(distProduct.getTotalPrice());
                    netDiscount += Long.parseLong(distProduct.getDiscount());
                    netGrandTotal += Long.parseLong(distProduct.getGrandTotal());
                    List<ProductWiseCampaign> prdcamp = productWiseCampRepo
                            .findAllByProductId(distProduct.getProductId());
                    for (ProductWiseCampaign offer : prdcamp) {
                        if (offer.getOfferType().equals("quantity")) {
                            if (distProduct.getReceivedQty() >= offer.getRequiredQuantity()) {
                                DistributorRequisitionFreeProduct fp = new DistributorRequisitionFreeProduct();
                                fp.setProductName(offer.getFreeItem());
                                fp.setFreeItemId(offer.getFreeProductId());
                                fp.setReceivedQty(Long.parseLong(offer.getQuantity()));
                                freeProductlist.add(fp);
                            }
                        }
                    }
                }
                Set<CashBackCampaignResponse> cashBackcampaignList = campaignService.getAvailableCashBack(request);
                for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                    if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                        if (cbc.getDiscountType().equals("BDT")) {
                            netDiscount += Long.parseLong(cbc.getDiscountAmount());
                            netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                        } else {
                            long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                            netDiscount += discount;
                            netGrandTotal -= discount;
                        }

                    }
                }
                distReq.get().setGrandTotalOfSupplyChain(Long.toString(netGrandTotal));
                distReq.get().setNetDiscountOfSupplyChain(Long.toString(netDiscount));
                distReq.get().setTotalPriceOfSupplyChain(Long.toString(netTotal));
            }
            if (entity.getStatus().equals("3")) {
                distReq.get().setAccounceApprove(entity.getRequisitionAccounceApprove());
                Set<DistributorRequisitionAccounceApprove> requiredProduct = entity.getRequisitionAccounceApprove();
                for (DistributorRequisitionAccounceApprove distProduct : requiredProduct) {
                    netTotal += Long.parseLong(distProduct.getTotalPrice());
                    netDiscount += Long.parseLong(distProduct.getDiscount());
                    netGrandTotal += Long.parseLong(distProduct.getGrandTotal());
                    List<ProductWiseCampaign> prdcamp = productWiseCampRepo
                            .findAllByProductId(distProduct.getProductId());
                    for (ProductWiseCampaign offer : prdcamp) {
                        if (offer.getOfferType().equals("quantity")) {
                            if (distProduct.getReceivedQty() >= offer.getRequiredQuantity()) {
                                DistributorRequisitionFreeProduct fp = new DistributorRequisitionFreeProduct();
                                fp.setProductName(offer.getFreeItem());
                                fp.setFreeItemId(offer.getFreeProductId());
                                fp.setReceivedQty(Long.parseLong(offer.getQuantity()));
                                freeProductlist.add(fp);
                            }
                        }
                    }
                }
                Set<CashBackCampaignResponse> cashBackcampaignList = campaignService.getAvailableCashBack(request);
                for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                    if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                        if (cbc.getDiscountType().equals("BDT")) {
                            netDiscount += Long.parseLong(cbc.getDiscountAmount());
                            netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                        } else {
                            long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                            netDiscount += discount;
                            netGrandTotal -= discount;
                        }

                    }
                }
                distReq.get().setGrandTotalOfAccounce(Long.toString(netGrandTotal));
                distReq.get().setNetDiscountOfAccounce(Long.toString(netDiscount));
                distReq.get().setTotalPriceOfAccounce(Long.toString(netTotal));
            }
            if (entity.getStatus().equals("4")) {
                distReq.get().setOperationApprove(entity.getRequisitionOperationApprove());
                Set<DistributorRequisitionOperationApprove> requiredProduct = entity.getRequisitionOperationApprove();
                for (DistributorRequisitionOperationApprove distProduct : requiredProduct) {
                    netTotal += Long.parseLong(distProduct.getTotalPrice());
                    netDiscount += Long.parseLong(distProduct.getDiscount());
                    netGrandTotal += Long.parseLong(distProduct.getGrandTotal());
                    List<ProductWiseCampaign> prdcamp = productWiseCampRepo
                            .findAllByProductId(distProduct.getProductId());
                    for (ProductWiseCampaign offer : prdcamp) {
                        if (offer.getOfferType().equals("quantity")) {
                            if (distProduct.getReceivedQty() >= offer.getRequiredQuantity()) {
                                DistributorRequisitionFreeProduct fp = new DistributorRequisitionFreeProduct();
                                fp.setProductName(offer.getFreeItem());
                                fp.setFreeItemId(offer.getFreeProductId());
                                fp.setReceivedQty(Long.parseLong(offer.getQuantity()));
                                freeProductlist.add(fp);
                            }
                        }
                    }
                }
                Set<CashBackCampaignResponse> cashBackcampaignList = campaignService.getAvailableCashBack(request);
                for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                    if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                        if (cbc.getDiscountType().equals("BDT")) {
                            netDiscount += Long.parseLong(cbc.getDiscountAmount());
                            netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                        } else {
                            long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                            netDiscount += discount;
                            netGrandTotal -= discount;
                        }

                    }
                }
                distReq.get().setGrandTotalOfOperation(Long.toString(netGrandTotal));
                distReq.get().setNetDiscountOfOperation(Long.toString(netDiscount));
                distReq.get().setTotalPriceOfOperation(Long.toString(netTotal));
            }
            if (entity.getStatus().equals("5")) {
                distReq.get().setDeliveryStatus(entity.getDeliveryStatus());
                distReq.get().setDelivered(entity.getRequisitionDelivered());
                
                Set<DistributorRequisitionDelivered> requiredProduct = entity.getRequisitionDelivered();
                for (DistributorRequisitionDelivered distProduct : requiredProduct) {
                    netTotal += Long.parseLong(distProduct.getTotalPrice());
                    netDiscount += Long.parseLong(distProduct.getDiscount());
                    netGrandTotal += Long.parseLong(distProduct.getGrandTotal());
                    
                    List<ProductWiseCampaign> prdcamp = productWiseCampRepo
                            .findAllByProductId(distProduct.getProductId());
                    for (ProductWiseCampaign offer : prdcamp) {
                        if (offer.getOfferType().equals("quantity")) {
                            if (distProduct.getReceivedQty() >= offer.getRequiredQuantity()) {
                                DistributorRequisitionFreeProduct fp = new DistributorRequisitionFreeProduct();
                                fp.setProductName(offer.getFreeItem());
                                fp.setFreeItemId(offer.getFreeProductId());
                                fp.setReceivedQty(Long.parseLong(offer.getQuantity()));
                                freeProductlist.add(fp);
                                updatePrimaryInventoryForFreeProduct(fp);
                                updateSecondaryInventoryForFreeProduct(fp);
                            }
                        }
                    }
                    
                    updatePrimaryInventory(distProduct);
                    
                }
                Set<CashBackCampaignResponse> cashBackcampaignList = campaignService.getAvailableCashBack(request);
                for (CashBackCampaignResponse cbc : cashBackcampaignList) {
                    if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
                        if (cbc.getDiscountType().equals("BDT")) {
                            netDiscount += Long.parseLong(cbc.getDiscountAmount());
                            netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
                        } else {
                            long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
                            netDiscount += discount;
                            netGrandTotal -= discount;
                        }

                    }
                }
               
                distReq.get().setGrandTotalOfDelivery(Long.toString(netGrandTotal));
//              distReq.get().setNetDiscountOfOperation(Long.toString(netDiscount));
                distReq.get().setNetDiscountOfDelivery(Long.toString(netDiscount));
                distReq.get().setTotalPriceOfDelivery(Long.toString(netTotal));
            }
            distReq.get().setFreeProduct(freeProductlist);
            dis = dealerRequisitionRepo.save(distReq.get());
            
        }

        return dis;
    }
    private void updatePrimaryInventory(DistributorRequisitionDelivered drd) {
		long productId = drd.getProductId();
		Optional<PrimaryInventory> primaryInventory = primaryInventoryRepo.findByProductId(productId);
		if (primaryInventory.isPresent()) {
			long onHand = primaryInventory.get().getOnHand();
			long shippedInventory = primaryInventory.get().getShippedInventory();
			long  onHandNow = onHand - drd.getDeliveredQuantity();
			long shippedInventoryNow = shippedInventory + drd.getDeliveredQuantity();
			
			primaryInventory.get().setOnHand(onHandNow);
			primaryInventory.get().setShippedInventory(shippedInventoryNow);
			primaryInventoryRepo.save(primaryInventory.get());
					
			
		} else {
			System.out.println("Primary Inventory for these product not found");

		}
	}
    
    private void updatePrimaryInventoryForFreeProduct(DistributorRequisitionFreeProduct drfp) {
		
    	long freeProductId = drfp.getFreeItemId();
    	Optional<PrimaryInventory> primaryInventory = primaryInventoryRepo.findByProductId(freeProductId);
    	if (primaryInventory.isPresent()) {
    		long onHand = primaryInventory.get().getOnHand();
    		long shippedInventory = primaryInventory.get().getShippedInventory();
    		
    		long onHandNow = onHand - drfp.getReceivedQty();
    		long shippedInventoryNow = shippedInventory + drfp.getReceivedQty();
    		
    		primaryInventory.get().setOnHand(onHandNow);
    		primaryInventory.get().setShippedInventory(shippedInventoryNow);
    		
    		primaryInventoryRepo.save(primaryInventory.get());
			
		} else {
			System.out.println("Primary Inventory for these product not found");
		}
    	
	}
    
    private void updateSecondaryInventoryForFreeProduct(DistributorRequisitionFreeProduct drfp) {
    	long freeProductId = drfp.getFreeItemId();
    	Optional<SecondaryInventory> secondaryInventory = secondaryRepo.findByProductId(freeProductId);
    	if (secondaryInventory.isPresent()) {
    		
    		long onHand = secondaryInventory.get().getOnHand();
    		long receivedInventory = secondaryInventory.get().getReceivedInventory();
    		
    		long onHandNow = onHand + drfp.getReceivedQty();
    		long receivedInventoryNow = receivedInventory + drfp.getReceivedQty();
    		
    		secondaryInventory.get().setOnHand(onHandNow);
    		secondaryInventory.get().setReceivedInventory(receivedInventoryNow);
    		
    		secondaryRepo.save(secondaryInventory.get());
			
		} else {
			System.out.println("Primary Secondary for these product not found");
		}
		
	}
    
    @Override
    public DistributorRequisition update1(DistributorRequisition entity, Long id, HttpServletRequest request) {

        Optional<DistributorRequisition> distReq = dealerRequisitionRepo.findById(id);
        if (distReq.isPresent()) {
            distReq.get().setProductRecieve(entity.getDistributorRequiredReceive());
            UpdateSecondaryStock(distReq.get().getDealerId(), entity.getDistributorRequiredReceive(), request);
            distReq.get().setStatus("6");
            dealerRequisitionRepo.save(distReq.get());
        }
        return distReq.get();
    }

    private void UpdateSecondaryStock(Long dealerId, Set<DistributorRequisitionReceive> distributorRequiredReceive,
            HttpServletRequest request) {
        for (DistributorRequisitionReceive distributorRequisitionReceive : distributorRequiredReceive) {

            Optional<SecondaryInventory> si = secondaryRepo
                    .findByProductIdAndDistributor(distributorRequisitionReceive.getProductId(), dealerId);
            if (si.isPresent()) {
                long prevRecieveQuantity = si.get().getReceivedInventory();
                long prevOnhandQuantity = si.get().getOnHand();
                long newInQuantity = distributorRequisitionReceive.getToReceive();//getReceivedQty();
                long receive = prevRecieveQuantity + newInQuantity;
                long onhand = prevOnhandQuantity + newInQuantity;
                si.get().setReceivedInventory(receive);
                si.get().setOnHand(onhand);
                si.get().setCreatedAt(new Date());
                si.get().setActive(true);
                si.get().setUpdatedAt(new Date());
                si.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
                secondaryRepo.save(si.get());
//                updatePrimaryStock(distributorRequisitionReceive.getProductId(),
//                        distributorRequisitionReceive.getReceivedQty());

            } else {
                SecondaryInventory si1 = new SecondaryInventory();
                si1.setProductId(distributorRequisitionReceive.getProductId());
                si1.setDistributorId(dealerId);
                si1.setStartingInventory(distributorRequisitionReceive.getToReceive());//getReceivedQty());
                si1.setReceivedInventory(distributorRequisitionReceive.getToReceive());
                si1.setOnHand(distributorRequisitionReceive.getToReceive());
                si1.setSafetyStock(50l);
                si1.setMinimumQty(50l);
                si1.setCreatedAt(new Date());
                si1.setActive(true);
                si1.setUpdatedAt(new Date());
                si1.setCreatedBy((long) request.getSession().getAttribute("userId"));
                secondaryRepo.save(si1);
            }
        }

    }

//    private void updatePrimaryStock(Long productId, Long newInQuantity) {
//        Optional<PrimaryInventory> pi = primaryInventoryRepo.findByProductId(productId);
//        if (pi.isPresent()) {
//            long prevRecieveQuantity = pi.get().getReceivedInventory();
//            long prevOnhandQuantity = pi.get().getOnHand();
//            long prevShippedQuantity = pi.get().getShippedInventory();
//            long receive = prevRecieveQuantity - newInQuantity;
//            long onhand = prevOnhandQuantity - newInQuantity;
//            long shipped = prevShippedQuantity + newInQuantity;
//            pi.get().setOnHand(onhand);
//            pi.get().setReceivedInventory(receive);
//            pi.get().setShippedInventory(shipped);
//            primaryInventoryRepo.save(pi.get());
//        }
//
//    }

	@Override
	public List<DistributorRequisition> findDistReqByDealerId(HttpServletRequest request,Long id) {
		// TODO Auto-generated method stub
		return dealerRequisitionRepo.findByDealerId(id);
	}
}
