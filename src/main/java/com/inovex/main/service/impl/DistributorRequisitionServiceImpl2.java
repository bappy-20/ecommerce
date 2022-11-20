//package com.inovex.main.service.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.inovex.main.entity.Distributor;
//import com.inovex.main.entity.DistributorRequisition;
//import com.inovex.main.entity.DistributorRequisitionProduct;
//import com.inovex.main.entity.DistributorRequisitionResponse;
//import com.inovex.main.entity.Organization;
//import com.inovex.main.json.response.CashBackCampaignResponse;
//import com.inovex.main.repository.CampaignRepository;
//import com.inovex.main.repository.CashBackCampaignRepo;
//import com.inovex.main.repository.DistributorRepo;
//import com.inovex.main.repository.DistributorRequisitionRepo;
//import com.inovex.main.repository.OrganizationRepository;
//import com.inovex.main.repository.ProductWiseCampaignRepository;
//import com.inovex.main.service.CashBackCampaignService;
//import com.inovex.main.service.DistributorRequisitionService;
//
//@Service
//@Transactional
//public class DistributorRequisitionServiceImpl2 implements DistributorRequisitionService {
//
//    @Autowired
//    DistributorRequisitionRepo dealerRequisitionRepo;
//    @Autowired
//    OrganizationRepository orgRepo;
//    @Autowired
//    CampaignRepository campRepo;
//    @Autowired
//    ProductWiseCampaignRepository productWiseCampRepo;
//    @Autowired
//    CashBackCampaignRepo cashBackCampaignRepo;
//    @Autowired
//    DistributorRepo distributorRepo;
//    @Autowired
//    CashBackCampaignService campaignService;
//
//    @Override
//    public Optional<DistributorRequisition> findById(Long id) {
//        // TODO Auto-generated method stub
//        return dealerRequisitionRepo.findById(id);
//    }
//
//    @Override
//    public List<DistributorRequisition> findAll() {
//        // TODO Auto-generated method stub
//        return dealerRequisitionRepo.findAll();
//    }
//
//    @Override
//    public void deleteById(Long id, HttpServletRequest request) {
//        if (request.getSession().getAttribute("orgId") != null) {
//            long orgId = (long) request.getSession().getAttribute("orgId");
//            dealerRequisitionRepo.deleteFromOrg(orgId, id);
//            dealerRequisitionRepo.deleteById(id);
//        }
//
//    }
//
//    @Override
//    public DistributorRequisition update(DistributorRequisition entity, Long id, HttpServletRequest request) {
//        Optional<DistributorRequisition> distReq = dealerRequisitionRepo.findById(id);
//        long netTotal = 0;
//        long netDiscount = 0;
//        long netGrandTotal = 0;
//        if (distReq.isPresent()) {
//            Set<DistributorRequisitionProduct> requiredProduct = entity.getDistributorRequiredProduct();
//            for (DistributorRequisitionProduct distProduct : requiredProduct) {
//                netTotal += Long.parseLong(distProduct.getTotalPrice());
//                netDiscount += Long.parseLong(distProduct.getDiscount());
//                netGrandTotal += Long.parseLong(distProduct.getGrandTotal());
//            }
//            Set<CashBackCampaignResponse> cashBackcampaignList = campaignService.getAvailableCashBack(request);
//            for (CashBackCampaignResponse cbc : cashBackcampaignList) {
//                if (netTotal >= Long.parseLong(cbc.getRequiredInvoiceAmount())) {
//                    if (cbc.getDiscountType().equals("BDT")) {
//                        netDiscount += Long.parseLong(cbc.getDiscountAmount());
//                        netGrandTotal -= Long.parseLong(cbc.getDiscountAmount());
//                    } else {
//                        long discount = (Long.parseLong(cbc.getDiscountAmount()) * netTotal) / 100;
//                        netDiscount += discount;
//                        netGrandTotal -= discount;
//                    }
//
//                }
//            }
//            distReq.get().setTotalPrice(Long.toString(netTotal));
//            distReq.get().setStatus(entity.getStatus());
//            distReq.get().setGrandTotal(Long.toString(netGrandTotal));
//            distReq.get().setNetDiscount(Long.toString(netDiscount));
//            distReq.get().setUpdatedAt(new Date());
//            distReq.get().setUpdatedBy((long) request.getSession().getAttribute("userId"));
//            distReq.get().setChildren(entity.getDistributorRequiredProduct());
//            dealerRequisitionRepo.save(distReq.get());
//        }
//        return distReq.get();
//    }
//
//    @Override
//    public DistributorRequisition save(DistributorRequisition entity, HttpServletRequest request) {
//        // TODO Auto-generated method stub
//        DistributorRequisition distributor = new DistributorRequisition();
//        if (request.getSession().getAttribute("orgId") != null) {
//            long id = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(id);
//            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
//            if (org.isPresent()) {
//                list = org.get().getDistributorRequisition();
//                entity.setCreatedAt(new Date());
//                // entity.setStatus("Pending");
//                entity.setActive(true);
//                entity.setUpdatedAt(new Date());
//                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
//                distributor = dealerRequisitionRepo.save(entity);
//                list.add(distributor);
//                org.get().setDistributorRequisition(list);
//                orgRepo.save(org.get());
//            }
//
//        } else {
//            System.out.println("Org is Null");
//        }
//        return distributor;
//    }
//
//    @Override
//    public List<DistributorRequisitionResponse> findAllResponse(HttpServletRequest request) {
//        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
//        if (request.getSession().getAttribute("orgId") != null) {
//            long id = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(id);
//            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
//
//            if (org.isPresent()) {
//                list = org.get().getDistributorRequisition().stream()
//                        .filter(p -> p.getStatus().equals("1") || p.getStatus().equals("0")|| p.getStatus().equals("5"))
//                        .collect(Collectors.toSet());
//                for (DistributorRequisition distributorRequisition : list) {
//                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
//                    res.setId(distributorRequisition.getId());
//                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
//                    if (dis.isPresent()) {
//                        res.setDistName(dis.get().getDistributorName());
//                    } else {
//                        res.setDistName("Not Found");
//                    }
//                    res.setTotalPrice(distributorRequisition.getTotalPrice());
//                    res.setNetDiscount(distributorRequisition.getNetDiscount());
//                    res.setGrandTotal(distributorRequisition.getGrandTotal());
//                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
//                   // res.setStatus(distributorRequisition.getStatus());
//                    //res.setStatus("5");
//                    if (distributorRequisition.getStatus().equals("1")) {
//                        res.setStatus("Confirmed");
//                    } else {
//                        res.setStatus("Pending");
//                    }
//
//                    responeList.add(res);
//                }
//            }
//        }
//        return responeList;
//    }
//
//    @Override
//    public List<DistributorRequisitionResponse> findAllConfrimRequiResponse(HttpServletRequest request) {
//        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
//        if (request.getSession().getAttribute("orgId") != null) {
//            long id = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(id);
//            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
//            if (org.isPresent()) {
//                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("1"))
//                        .collect(Collectors.toSet());
//                for (DistributorRequisition distributorRequisition : list) {
//                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
//                    res.setId(distributorRequisition.getId());
//                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
//                    if (dis.isPresent()) {
//                        res.setDistName(dis.get().getDistributorName());
//                    } else {
//                        res.setDistName("Not Found");
//                    }
//                    res.setTotalPrice(distributorRequisition.getTotalPrice());
//                    res.setNetDiscount(distributorRequisition.getNetDiscount());
//                    res.setGrandTotal(distributorRequisition.getGrandTotal());
//                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
//                    if (distributorRequisition.getStatus().equals("1")) {
//                        res.setStatus("Confirmed");
//                    }
//
//                    responeList.add(res);
//                }
//            }
//        }
//        return responeList;
//    }
//
//    @Override
//    public List<DistributorRequisitionResponse> findAllProcessedRequisitionResponse(HttpServletRequest request) {
//        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
//        if (request.getSession().getAttribute("orgId") != null) {
//            long id = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(id);
//            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
//            if (org.isPresent()) {
//                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("2"))
//                        .collect(Collectors.toSet());
//                for (DistributorRequisition distributorRequisition : list) {
//                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
//                    res.setId(distributorRequisition.getId());
//                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
//                    if (dis.isPresent()) {
//                        res.setDistName(dis.get().getDistributorName());
//                    } else {
//                        res.setDistName("Not Found");
//                    }
//                    res.setTotalPrice(distributorRequisition.getTotalPrice());
//                    res.setNetDiscount(distributorRequisition.getNetDiscount());
//                    res.setGrandTotal(distributorRequisition.getGrandTotal());
//                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
//                    if (distributorRequisition.getStatus().equals("2")) {
//                        res.setStatus("Processed");
//                    }
//
//                    responeList.add(res);
//                }
//            }
//        }
//        return responeList;
//    }
//
//    @Override
//    public DistributorRequisition update(Long reqId, String dept) {
//        // TODO Auto-generated method stub
//        Optional<DistributorRequisition> distReq = dealerRequisitionRepo.findById(reqId);
//        if (distReq.isPresent()) {
//            if (dept.equals("supplychain")) {
//                distReq.get().setStatus("1");
//            }
//            if (dept.equals("account")) {
//                distReq.get().setStatus("3");
//            }
//            if (dept.equals("operation")) {
//                distReq.get().setStatus("3");
//            }
//            return dealerRequisitionRepo.save(distReq.get());
//
//        } else {
//            return null;
//        }
//
//    }
//
//    @Override
//    public List<DistributorRequisitionResponse> findAllApprovedRequisitionResponse(HttpServletRequest request) {
//        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
//        if (request.getSession().getAttribute("orgId") != null) {
//            long id = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(id);
//            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
//            if (org.isPresent()) {
//                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("3"))
//                        .collect(Collectors.toSet());
//                for (DistributorRequisition distributorRequisition : list) {
//                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
//                    res.setId(distributorRequisition.getId());
//                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
//                    if (dis.isPresent()) {
//                        res.setDistName(dis.get().getDistributorName());
//                    } else {
//                        res.setDistName("Not Found");
//                    }
//                    res.setTotalPrice(distributorRequisition.getTotalPrice());
//                    res.setNetDiscount(distributorRequisition.getNetDiscount());
//                    res.setGrandTotal(distributorRequisition.getGrandTotal());
//                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
//                    if (distributorRequisition.getStatus().equals("3")) {
//                        res.setStatus("Approved");
//                    }
//
//                    responeList.add(res);
//                }
//            }
//        }
//        return responeList;
//    }
//
//    @Override
//    public List<DistributorRequisitionResponse> findAllVerifiedRequisitionResponse(HttpServletRequest request) {
//        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
//        if (request.getSession().getAttribute("orgId") != null) {
//            long id = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(id);
//            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
//            if (org.isPresent()) {
//                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("4"))
//                        .collect(Collectors.toSet());
//                for (DistributorRequisition distributorRequisition : list) {
//                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
//                    res.setId(distributorRequisition.getId());
//                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
//                    if (dis.isPresent()) {
//                        res.setDistName(dis.get().getDistributorName());
//                    } else {
//                        res.setDistName("Not Found");
//                    }
//                    res.setTotalPrice(distributorRequisition.getTotalPrice());
//                    res.setNetDiscount(distributorRequisition.getNetDiscount());
//                    res.setGrandTotal(distributorRequisition.getGrandTotal());
//                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
//                    if (distributorRequisition.getStatus().equals("4")) {
//                        res.setStatus("Verified");
//                    }
//
//                    responeList.add(res);
//                }
//            }
//        }
//        return responeList;
//    }
//   
//    @Override
//    public List<DistributorRequisitionResponse> findAllDeliveredRequisitionResponse(HttpServletRequest request) {
//        List<DistributorRequisitionResponse> responeList = new ArrayList<>();
//        if (request.getSession().getAttribute("orgId") != null) {
//            long id = (long) request.getSession().getAttribute("orgId");
//            Optional<Organization> org = orgRepo.findById(id);
//            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
//            if (org.isPresent()) {
//            	if (org.get().getDistributorRequisition()!= null) {
//					System.out.println("Org is Not null");
////					list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("5"))
////	                        .collect(Collectors.toSet());
//					
//					list = org.get().getDistributorRequisition();
//	                if (list != null) {
//	                	System.out.println("List is not null");
//	                	 for (DistributorRequisition distributorRequisition : list) {
//	                       DistributorRequisitionResponse res = new DistributorRequisitionResponse();
//	                       res.setId(distributorRequisition.getId());
//	                       Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
//	                       if (dis.isPresent()) {
//	                           res.setDistName(dis.get().getDistributorName());
//	                       } else {
//	                           res.setDistName("Not Found");
//	                       }
//	                       res.setTotalPrice(distributorRequisition.getTotalPrice());
//	                       res.setNetDiscount(distributorRequisition.getNetDiscount());
//	                       res.setGrandTotal(distributorRequisition.getGrandTotal());
//	                       res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
//	                       if (distributorRequisition.getStatus().equals("5")) {
//	                           res.setStatus("Delivered");
//	                       }
//	                       
//	                       responeList.add(res);
//	                   }
//					} 
//	                else {
//						System.out.println("List is null");
//	
//					}
//				}
//            	
//            	else {
//					System.out.println("org is null");
//				}
//            	//.stream().filter(p ->p.getStatus().equals("1")).collect(Collectors.toSet());
////                list = org.get().getDistributorRequisition().stream().filter(p -> p.getStatus().equals("5"))
////                        .collect(Collectors.toSet());
////                if (list != null) {
////                	 for (DistributorRequisition distributorRequisition : list) {
////                       DistributorRequisitionResponse res = new DistributorRequisitionResponse();
////                       res.setId(distributorRequisition.getId());
////                       Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
////                       if (dis.isPresent()) {
////                           res.setDistName(dis.get().getDistributorName());
////                       } else {
////                           res.setDistName("Not Found");
////                       }
////                       res.setTotalPrice(distributorRequisition.getTotalPrice());
////                       res.setNetDiscount(distributorRequisition.getNetDiscount());
////                       res.setGrandTotal(distributorRequisition.getGrandTotal());
////                       res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
////                       if (distributorRequisition.getStatus().equals("5")) {
////                           res.setStatus("Delivered");
////                       }
////   
////                       responeList.add(res);
////                   }
////				} 
////                else {
////					System.out.println("List is null");
////
////				}
////                for (DistributorRequisition distributorRequisition : list) {
////                    DistributorRequisitionResponse res = new DistributorRequisitionResponse();
////                    res.setId(distributorRequisition.getId());
////                    Optional<Distributor> dis = distributorRepo.findById(distributorRequisition.getDealerId());
////                    if (dis.isPresent()) {
////                        res.setDistName(dis.get().getDistributorName());
////                    } else {
////                        res.setDistName("Not Found");
////                    }
////                    res.setTotalPrice(distributorRequisition.getTotalPrice());
////                    res.setNetDiscount(distributorRequisition.getNetDiscount());
////                    res.setGrandTotal(distributorRequisition.getGrandTotal());
////                    res.setRequisitionNumber(distributorRequisition.getRequisitionNumber());
////                    if (distributorRequisition.getStatus().equals("5")) {
////                        res.setStatus("Delivered");
////                    }
////
////                    responeList.add(res);
////                }
//            }
//        }
//        return responeList;
//    }
//
//	@Override
//	public List<DistributorRequisitionResponse> findAllDeliveredRequisitionReceiveResponse(HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
////	@Override
////	public DistributorRequisitionReceive save1(DistributorRequisitionReceive entity, HttpServletRequest request) {
////		// TODO Auto-generated method stub
////		DistributorRequisitionReceive distributor = new DistributorRequisitionReceive();
////      if (request.getSession().getAttribute("orgId") != null) {
////          long id = (long) request.getSession().getAttribute("orgId");
////          Optional<Organization> org = orgRepo.findById(id);
////          Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
////          if (org.isPresent()) {
////              list = org.get().getDistributorRequisition();
////              entity.setCreatedAt(new Date());
////              // entity.setStatus("Pending");
////              entity.setActive(true);
////              entity.setUpdatedAt(new Date());
////              entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
//////              distributor = dealerRequisitionRepo.save(entity);
//////              list.add(distributor);
////              org.get().setDistributorRequisition(list);
////              orgRepo.save(org.get());
////          }
////
////      } else {
////          System.out.println("Org is Null");
////      }
////      return distributor;
////	}
//
////	@Override
////	public DistributorRequisitionReceive save1(DistributorRequisitionReceive entity, HttpServletRequest request) {
////		// TODO Auto-generated method stub
////		DistributorRequisitionReceive distributor = new DistributorRequisitionReceive();
////        if (request.getSession().getAttribute("orgId") != null) {
////            long id = (long) request.getSession().getAttribute("orgId");
////            Optional<Organization> org = orgRepo.findById(id);
////            Set<DistributorRequisition> list = new HashSet<DistributorRequisition>();
////            if (org.isPresent()) {
////                list = org.get().getDistributorRequisition();
////                entity.setCreatedAt(new Date());
////                // entity.setStatus("Pending");
////                entity.setActive(true);
////                entity.setUpdatedAt(new Date());
////                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
////                distributor = dealerRequisitionRepo.save(entity);
////                list.add(distributor);
////                org.get().setDistributorRequisition(list);
////                orgRepo.save(org.get());
////            }
////
////        } else {
////            System.out.println("Org is Null");
////        }
////        return distributor;
////	}
//
//}
