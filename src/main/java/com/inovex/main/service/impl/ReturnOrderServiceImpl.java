package com.inovex.main.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.Organization;
import com.inovex.main.entity.ReturnOrder;
import com.inovex.main.entity.ReturnOrderProduct;
import com.inovex.main.entity.SecondaryInventory;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.ReturnOrderRepo;
import com.inovex.main.repository.SecondaryInventoryRepo;
import com.inovex.main.service.ReturnOrderService;

@Service
@Transactional
public class ReturnOrderServiceImpl implements ReturnOrderService {
    @Autowired
    SecondaryInventoryRepo secondaryRepo;
    @Autowired
    ReturnOrderRepo returnOrderRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<ReturnOrder> findById(Long id) {
        // TODO Auto-generated method stub
        return returnOrderRepo.findById(id);
    }

    @Override
    public List<ReturnOrder> findAll() {
        // TODO Auto-generated method stub
        return returnOrderRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        returnOrderRepo.deleteById(id);

    }

    @Override
    public ReturnOrder update(ReturnOrder returnPurchase, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

//    @Override
//    public void save(long retailId, long salePrice, String orderNumber, String returnDate, String returnNote,
//            String producList) {
//
//        List<ReturnOrderProduct> prList = new ArrayList<ReturnOrderProduct>();
//
//        if (producList.equals("")) {
//        } else {
//            try {
//                ReturnOrder purchaseOrder = new ReturnOrder();
//                String[] namesList = producList.split("###");
//                for (int i = 0; i < namesList.length; i++) {
//                    ReturnOrderProduct ip = new ReturnOrderProduct();
//                    String[] namesList1 = namesList[i].split(",");
//                    System.out.println(" pro:" + namesList1[0]);
//                    ip.setProductId(Long.parseLong(namesList1[0]));
//                    ip.setReceivedQty(namesList1[2]);
//                    ip.setProductIdUnitprice(Long.parseLong(namesList1[3]));
//                    ip.setSalePrice(Long.parseLong(namesList1[4]));
//                    ip.setRetailId(retailId);
//                    ip.setActive(true);
//                    ip.setCreatedAt(new Date());
//                    ip.setUpdatedAt(new Date());
//                    ip.setCreatedBy(1);
//                    prList.add(ip);
//                    insertIntoSecondaryInventory(namesList1);
//                }
//                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
//                Date stDate = null;
//
//                stDate = df2.parse(returnDate);
//
//                purchaseOrder.setRetailId(retailId);
//                purchaseOrder.setOrderNumber(orderNumber);
//                purchaseOrder.setReturnNote(returnNote);
//                purchaseOrder.setReturnDate(stDate);
//                purchaseOrder.setReturnPrice(salePrice);
//                purchaseOrder.setActive(true);
//                purchaseOrder.setCreatedAt(new Date());
//                purchaseOrder.setUpdatedAt(new Date());
//                purchaseOrder.setCreatedBy(1);
//                purchaseOrder.setReturnOrderProduct(prList);
//                returnOrderRepo.save(purchaseOrder);
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//    }

    private void insertIntoSecondaryInventory(String[] namesList1) {
        Optional<SecondaryInventory> si = secondaryRepo.findByProductId(Long.parseLong(namesList1[0]));
        if (si.isPresent()) {

            long prevRecieveQuantity = si.get().getReceivedInventory();
            long prevOnhandQuantity = si.get().getOnHand();
            long newInQuantity = Long.parseLong(namesList1[2]);

            long receive = prevRecieveQuantity + newInQuantity;
            long onhand = prevOnhandQuantity + newInQuantity;
            si.get().setReceivedInventory(receive);
            si.get().setOnHand(onhand);
            // si.get().setCreatedAt(new Date());
            si.get().setActive(true);
            si.get().setUpdatedAt(new Date());
            // entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
            si.get().setCreatedBy(1);
            secondaryRepo.save(si.get());
        }
    }
    private void insertIntoSecondaryInventory2(ReturnOrderProduct returnOrderProduct) {
        Optional<SecondaryInventory> si = secondaryRepo.findByProductId(returnOrderProduct.getProductId());
        if (si.isPresent()) {

            long prevRecieveQuantity = si.get().getReceivedInventory();
            long prevOnhandQuantity = si.get().getOnHand();
            long newInQuantity = Long.parseLong(returnOrderProduct.getReceivedQty());

            long receive = prevRecieveQuantity + newInQuantity;
            long onhand = prevOnhandQuantity + newInQuantity;
            si.get().setReceivedInventory(receive);
            si.get().setOnHand(onhand);
            // si.get().setCreatedAt(new Date());
            si.get().setActive(true);
            si.get().setUpdatedAt(new Date());
            // entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
            si.get().setCreatedBy(1);
            secondaryRepo.save(si.get());
        }
    }

	@Override
	public ReturnOrder save(ReturnOrder entity, HttpServletRequest request) {
		// TODO Auto-generated method stub
		ReturnOrder returnOrder = new ReturnOrder();
		if (request.getSession().getAttribute("orgId")!=null) {
			long id = (long) request.getSession().getAttribute("orgId");
			
			Optional<Organization> org = orgRepo.findById(id);
			Set<ReturnOrder> list = new HashSet<ReturnOrder>();		
			if (org.isPresent()) {
				list = org.get().getReturnOrders();//();
				entity.setCreatedAt(new Date());
				entity.setActive(true);
				entity.setUpdatedAt(new Date());
				entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
				returnOrder = returnOrderRepo.save(entity);
				list.add(returnOrder);
				org.get().setReturnOrders(list);
				orgRepo.save(org.get());
			}
		
		} else {
			System.out.println("Org is Null");
		}
		return null;
	}
	
//	 @Override
//	    public void save1(long retailId, long salePrice, String orderNumber, String returnDate, String returnNote,
//	            String producList, HttpServletRequest request) {
//
//	        List<ReturnOrderProduct> prList = new ArrayList<ReturnOrderProduct>();
//
//	        if (producList.equals("")) {
//	        } else {
//	            try {
//	            	if (request.getSession().getAttribute("orgId")!=null) {
//	        			long id = (long) request.getSession().getAttribute("orgId");
//	        			
//	        			Optional<Organization> org = orgRepo.findById(id);
//	        			Set<ReturnOrder> list = new HashSet<ReturnOrder>();
//	        			if (org.isPresent()) {	        				
//	        				list = org.get().getReturnOrders();
//	        				 ReturnOrder purchaseOrder = new ReturnOrder();
//	     	                String[] namesList = producList.split("###");
//	     	                for (int i = 0; i < namesList.length; i++) {
//	     	                    ReturnOrderProduct ip = new ReturnOrderProduct();
//	     	                    String[] namesList1 = namesList[i].split(",");
//	     	                    System.out.println(" pro:" + namesList1[0]);
//	     	                    ip.setProductId(Long.parseLong(namesList1[0]));
//	     	                    System.out.println("Return Quantity is"+namesList1[3]);
//	     	                    ip.setReceivedQty(namesList1[3]);
//	     	                    ip.setProductIdUnitprice(Long.parseLong(namesList1[4]));
//	     	                    ip.setSalePrice(Long.parseLong(namesList1[5]));
//	     	                    ip.setRetailId(retailId);
//	     	                    ip.setActive(true);
//	     	                    ip.setCreatedAt(new Date());
//	     	                    ip.setUpdatedAt(new Date());
//	     	                    ip.setCreatedBy(1);
//	     	                    prList.add(ip);
//	     	                    insertIntoSecondaryInventory(namesList1);
//	     	                }
//	     	                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
//	     	                Date stDate = null;
//
//	     	                stDate = df2.parse(returnDate);
//
//	     	                purchaseOrder.setRetailId(retailId);
//	     	                purchaseOrder.setOrderNumber(orderNumber);
//	     	                purchaseOrder.setReturnNote(returnNote);
//	     	                purchaseOrder.setReturnDate(stDate);
//	     	                purchaseOrder.setReturnPrice(salePrice);
//	     	                purchaseOrder.setActive(true);
//	     	                purchaseOrder.setCreatedAt(new Date());
//	     	                purchaseOrder.setUpdatedAt(new Date());
//	     	                purchaseOrder.setCreatedBy(1);
//	     	                purchaseOrder.setReturnOrderProduct(prList);
//	     	                purchaseOrder = returnOrderRepo.save(purchaseOrder);
//	     	                list.add(purchaseOrder);
//	     					org.get().setReturnOrders(list);///(list);
//	     					orgRepo.save(org.get());
//	        				
//	        				}
//	        			}
//	               
//
//	            } catch (Exception e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            }
//	        }
//
//	    }
//}
	
	 @Override
	    public void save1(long retailId, long salePrice, String orderNumber, String returnDate, String returnNote,
	            String producList, HttpServletRequest request) {

	        List<ReturnOrderProduct> prList = new ArrayList<ReturnOrderProduct>();

	        if (producList.equals("")) {
	        } else {
	            try {
	            	if (request.getSession().getAttribute("orgId")!=null) {
	        			long id = (long) request.getSession().getAttribute("orgId");
	        			
	        			Optional<Organization> org = orgRepo.findById(id);
	        			Set<ReturnOrder> list = new HashSet<ReturnOrder>();
	        			if (org.isPresent()) {	        				
	        				list = org.get().getReturnOrders();
	        				 ReturnOrder purchaseOrder = new ReturnOrder();
	     	                 String[] namesList = producList.split(",");
	     	                for (int i = 0; i < namesList.length; i++) {
	     	                    ReturnOrderProduct ip = new ReturnOrderProduct();
	     	                    String[] namesList1 = namesList[i].split(",");
	     	                    System.out.println(" pro:" + namesList1[0]);
	     	                    ip.setProductId(Long.parseLong(namesList1[0]));
	     	                    System.out.println("Return Quantity is"+namesList1[3]);
	     	                    ip.setReceivedQty(namesList1[3]);
	     	                    ip.setProductIdUnitprice(Long.parseLong(namesList1[4]));
	     	                    ip.setSalePrice(Long.parseLong(namesList1[5]));
	     	                    ip.setRetailId(retailId);
	     	                    ip.setActive(true);
	     	                    ip.setCreatedAt(new Date());
	     	                    ip.setUpdatedAt(new Date());
	     	                    ip.setCreatedBy(1);
	     	                    prList.add(ip);
	     	                    insertIntoSecondaryInventory(namesList1);
	     	                }
	     	                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
	     	                Date stDate = null;

	     	                stDate = df2.parse(returnDate);

	     	                purchaseOrder.setRetailId(retailId);
	     	                purchaseOrder.setOrderNumber(orderNumber);
	     	                purchaseOrder.setReturnNote(returnNote);
	     	                purchaseOrder.setReturnDate(stDate);
	     	                purchaseOrder.setReturnPrice(salePrice);
	     	                purchaseOrder.setActive(true);
	     	                purchaseOrder.setCreatedAt(new Date());
	     	                purchaseOrder.setUpdatedAt(new Date());
	     	                purchaseOrder.setCreatedBy(1);
	     	                purchaseOrder.setReturnOrderProduct(prList);
	     	                purchaseOrder = returnOrderRepo.save(purchaseOrder);
	     	                list.add(purchaseOrder);
	     					org.get().setReturnOrders(list);
	     					orgRepo.save(org.get());
	        				
	        				}
	        			}
	               

	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }

	    }

	@Override
	public void save2(long retailId, long salePrice, String orderNumber, String returnDate, String returnNote,
			JSONArray producList, HttpServletRequest request) {
		// TODO Auto-generated method stub
        List<ReturnOrderProduct> prList = new ArrayList<ReturnOrderProduct>();
        
        if (producList.equals("")) {
        } else {
        	try {
        		if (request.getSession().getAttribute("orgId")!=null) {
        			long id = (long) request.getSession().getAttribute("orgId");
        			
        			Optional<Organization> org = orgRepo.findById(id);
        			Set<ReturnOrder> list = new HashSet<ReturnOrder>();
        			if (org.isPresent()) {
        				list = org.get().getReturnOrders();
       				 ReturnOrder purchaseOrder = new ReturnOrder();
       				 
       			  for (int i = 0; i < producList.length(); i++) {
                      JSONObject jsonobject = producList.getJSONObject(i);
                      ReturnOrderProduct ip = new ReturnOrderProduct();
                      ip.setProductId(Long.parseLong(jsonobject.getString("productId1")));
                      ip.setReceivedQty(jsonobject.getString("returnQuantity"));
	                    ip.setProductIdUnitprice(Long.parseLong(jsonobject.getString("productIdUnitprice")));
	                    ip.setSalePrice(Long.parseLong(jsonobject.getString("total")));
	                    ip.setRetailId(retailId);
	                    ip.setActive(true);
	                    ip.setCreatedAt(new Date());
	                    ip.setUpdatedAt(new Date());
	                    ip.setCreatedBy(1);
	                    prList.add(ip);
	                    insertIntoSecondaryInventory2(ip);
                      
       			  }
       			 DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
	                Date stDate = null;

	                stDate = df2.parse(returnDate);

	                purchaseOrder.setRetailId(retailId);
	                purchaseOrder.setOrderNumber(orderNumber);
	                purchaseOrder.setReturnNote(returnNote);
	                purchaseOrder.setReturnDate(stDate);
	                purchaseOrder.setReturnPrice(salePrice);
	                purchaseOrder.setActive(true);
	                purchaseOrder.setCreatedAt(new Date());
	                purchaseOrder.setUpdatedAt(new Date());
	                purchaseOrder.setCreatedBy(1);
	                purchaseOrder.setReturnOrderProduct(prList);
	                purchaseOrder = returnOrderRepo.save(purchaseOrder);
	                list.add(purchaseOrder);
					org.get().setReturnOrders(list);///(list);
					orgRepo.save(org.get());
        				
        				}	     
        			}
				
			} catch (Exception e) {
				// TODO: handle exception
				 e.printStackTrace();
			}
        	
        }
        
        

		
	}
}
