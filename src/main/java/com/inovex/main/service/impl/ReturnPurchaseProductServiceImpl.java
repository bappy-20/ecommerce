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
import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.entity.ReturnProduct;
import com.inovex.main.entity.ReturnPurchaseProduct;
import com.inovex.main.entity.SecondaryInventory;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.PrimaryInventoryRepo;
import com.inovex.main.repository.ReturnPurchaseProductRepo;
import com.inovex.main.repository.SecondaryInventoryRepo;
import com.inovex.main.service.ReturnPurchaseProductService;

@Service
@Transactional
public class ReturnPurchaseProductServiceImpl implements ReturnPurchaseProductService {

    @Autowired
    ReturnPurchaseProductRepo returnPurchaseProductRepo;
    @Autowired
    SecondaryInventoryRepo secondaryRepo;
    @Autowired
    OrganizationRepository orgRepo;
    @Autowired
    PrimaryInventoryRepo primaryInventoryRepo;

    @Override
    public Optional<ReturnPurchaseProduct> findById(Long id) {
        // TODO Auto-generated method stub
        return returnPurchaseProductRepo.findById(id);
    }

    @Override
    public List<ReturnPurchaseProduct> findAll() {
        // TODO Auto-generated method stub
        return returnPurchaseProductRepo.findAll();
    }

    @Override
    public ReturnPurchaseProduct save(ReturnPurchaseProduct entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

//    @Override
//    public void deleteById(Long id) {
//        returnPurchaseProductRepo.deleteById(id);
//    }

    @Override
    public ReturnPurchaseProduct update(ReturnPurchaseProduct returnPurchase, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(long supplierId, long totalPrice, String purchaseNumber, String purchaseDate, String purchaseNote,
            JSONArray producList, HttpServletRequest request) {
        List<ReturnProduct> prList = new ArrayList<ReturnProduct>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgId);
            Set<ReturnPurchaseProduct> rp = new HashSet<>();
            if (org.isPresent()) {
                rp = org.get().getReturnPurchaseProducts();
                try {
                    ReturnPurchaseProduct purchaseOrder = new ReturnPurchaseProduct();
                    if (producList != null) {
                        for (int i = 0; i < producList.length(); i++) {
                            JSONObject jsonobject = producList.getJSONObject(i);

                            ReturnProduct ip = new ReturnProduct();

                            ip.setProductId1(jsonobject.getString("productId1"));
                            ip.setReceivedQty(jsonobject.getString("receivedQty"));
                            ip.setProductIdUnitprice(jsonobject.getString("productIdUnitprice"));
                            ip.setPurchasePrice(jsonobject.getString("purchasePrice"));
                            ip.setSupplierId(jsonobject.getString("supplierId"));
                            ip.setReturnQuantity(jsonobject.getString("returnQuantity"));
                            ip.setDeductionAmount(jsonobject.getString("deductionAmount"));
                            ip.setProductName(jsonobject.getString("productName"));
                            ip.setTotal(jsonobject.getString("total"));
                            ip.setActive(true);
                            ip.setCreatedAt(new Date());
                            ip.setUpdatedAt(new Date());
                            ip.setCreatedBy(1);
                            prList.add(ip);
                            updatePrimaryInventory(ip);
                            //insertIntoSecondaryInventory(ip); ///why these method @Bappi
                        }
                    }
                    /*
                     * String[] namesList = producList.split("###"); for (int i = 0; i <
                     * namesList.length; i++) { ReturnProduct ip = new ReturnProduct(); String[]
                     * namesList1 = namesList[i].split(",");
                     * 
                     * ip.setProductId1(Long.parseLong(namesList1[0]));
                     * ip.setReceivedQty(namesList1[2]);
                     * ip.setProductIdUnitprice(Long.parseLong(namesList1[3]));
                     * ip.setPurchasePrice(Long.parseLong(namesList1[4]));
                     * ip.setSupplierId(supplierId); ip.setActive(true); ip.setCreatedAt(new
                     * Date()); ip.setUpdatedAt(new Date()); ip.setCreatedBy(1); prList.add(ip);
                     * insertIntoSecondaryInventory(namesList1); }
                     */

                    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date stDate = null;
                    stDate = df2.parse(purchaseDate);
                    purchaseOrder.setSupplierId(supplierId);
                    purchaseOrder.setPurchaseNumber(purchaseNumber);
                    purchaseOrder.setPurchaseNote(purchaseNote);
                    purchaseOrder.setReturnDate(stDate);
                    purchaseOrder.setReturnPrice(totalPrice);
                    purchaseOrder.setActive(true);
                    purchaseOrder.setCreatedAt(new Date());
                    purchaseOrder.setUpdatedAt(new Date());
                    purchaseOrder.setCreatedBy(1);
                    purchaseOrder.setReturnProduct(prList);
                    ReturnPurchaseProduct returnPro = returnPurchaseProductRepo.save(purchaseOrder);
                    rp.add(returnPro);
                    org.get().setReturnPurchaseProducts(rp);
                    orgRepo.save(org.get());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

    }
    private void updatePrimaryInventory(ReturnProduct rp) {
		Optional<PrimaryInventory> primaryInventory = primaryInventoryRepo.findByProductId(Long.parseLong(rp.getProductId1()));
		if (primaryInventory.isPresent()) {
			long receiveInventory = primaryInventory.get().getReceivedInventory();
    		long onHand =primaryInventory.get().getOnHand();
    		long receiveNow = receiveInventory - Long.parseLong(rp.getReturnQuantity());
    		long onHandNow = onHand-Long.parseLong(rp.getReturnQuantity());
    		primaryInventory.get().setReceivedInventory(receiveNow);   		
    		primaryInventory.get().setOnHand(onHandNow);
    		primaryInventory.get().setUpdatedAt(new Date());
    		primaryInventoryRepo.save(primaryInventory.get());
		} else {
			System.out.println("Primary inventory not found of these product");

		}
	}

    private void insertIntoSecondaryInventory(ReturnProduct returnProduct) {
        Optional<SecondaryInventory> si = secondaryRepo.findByProductId(Long.parseLong(returnProduct.getProductId1()));
        if (si.isPresent()) {
            long prevRecieveQuantity = si.get().getReceivedInventory();
            long prevOnhandQuantity = si.get().getOnHand();
            long newInQuantity = Long.parseLong(returnProduct.getReturnQuantity());

            long receive = prevRecieveQuantity - newInQuantity;
            long onhand = prevOnhandQuantity - newInQuantity;
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
    public Optional<ReturnPurchaseProduct> findByPurchaseNumber(String purchaseNumber) {
        // TODO Auto-generated method stub
        return returnPurchaseProductRepo.findByPurchaseNumber(purchaseNumber);
    }

	@Override
	public void deleteById(Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
	    if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            returnPurchaseProductRepo.deleteFromOrg(orgId, id);
            returnPurchaseProductRepo.deleteById(id);
        }
		
	}

}
