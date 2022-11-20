package com.inovex.main.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.IncomingProduct;
import com.inovex.main.entity.Organization;
import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.entity.PurchaseProduct;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.repository.PrimaryInventoryRepo;
import com.inovex.main.repository.PurchaseOrderRepo;
import com.inovex.main.service.PurchaseOrderService;

@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    PurchaseOrderRepo purchaseOrderrepo;
    @Autowired
    PrimaryInventoryRepo primaryRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<PurchaseProduct> findById(Long id) {
        // TODO Auto-generated method stub
        return purchaseOrderrepo.findById(id);
    }

    @Override
    public List<PurchaseProduct> findAll() {
        // TODO Auto-generated method stub
        return purchaseOrderrepo.findAll();
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
        if (request.getSession().getAttribute("orgId") != null) {
            long orgId = (long) request.getSession().getAttribute("orgId");
            purchaseOrderrepo.deleteFromOrg(orgId, id);
            purchaseOrderrepo.deleteById(id);
        }
    }

    @Override
    public PurchaseProduct update(PurchaseProduct purchaseOrder, Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(HttpServletRequest request, long supplierId, long totalPrice, String purchaseNumber, long discount,
            long grandTotal, String purchaseDate, String purchaseNote, String producList) {
        List<IncomingProduct> prList = new ArrayList<IncomingProduct>();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(id);
            if (org.isPresent()) {
                Set<PurchaseProduct> list = org.get().getPurchaseProducts();
                if (producList.equals("")) {
                } else {
                    PurchaseProduct purchaseOrder = new PurchaseProduct();
                    long totalRecievePrice = 0;
                    String[] namesList = producList.split("###");
                    for (int i = 0; i < namesList.length; i++) {
                        IncomingProduct ip = new IncomingProduct();
                        String[] namesList1 = namesList[i].split(",");

                        ip.setProductId1(Long.parseLong(namesList1[0]));
                        ip.setReceivedQty(namesList1[2]);
                        ip.setProductIdUnitprice(Long.parseLong(namesList1[3]));
                        ip.setPurchasePrice(Long.parseLong(namesList1[4]));
                        ip.setDiscountType(namesList1[5]);
                        ip.setDiscount(namesList1[6]);
                        ip.setGrandPrice(namesList1[7]);
                        ip.setProductBatchId(Long.parseLong(namesList1[8]));
                        totalRecievePrice += Long.parseLong(namesList1[4]);
                        ip.setSupplierId(supplierId);

                        ip.setActive(true);
                        ip.setCreatedAt(new Date());
                        ip.setUpdatedAt(new Date());
                        ip.setCreatedBy(1);
                        prList.add(ip);
                        insertPrimaryInventory(namesList1);
                    }
                    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date stDate = null;
                    try {
                        stDate = df2.parse(purchaseDate);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    purchaseOrder.setSupplierId(supplierId);
                    purchaseOrder.setPurchaseNumber(purchaseNumber);
                    purchaseOrder.setPurchaseNote(purchaseNote);
                    purchaseOrder.setTotalPrice(totalPrice);
                    purchaseOrder.setDiscount(discount);
                    purchaseOrder.setGrandTotal(grandTotal);
                    purchaseOrder.setPurchaseDate(stDate);
                    purchaseOrder.setTotalRecievePrice(totalRecievePrice);
                    purchaseOrder.setActive(true);
                    purchaseOrder.setCreatedAt(new Date());
                    purchaseOrder.setUpdatedAt(new Date());
                    purchaseOrder.setCreatedBy(1);
                    purchaseOrder.setIncomingProduct(prList);
                    purchaseOrderrepo.save(purchaseOrder);
                    list.add(purchaseOrder);
                    org.get().setPurchaseProducts(list);
                    orgRepo.save(org.get());
                }
            }

        }

    }

    private void insertPrimaryInventory(String[] namesList1) {

        Optional<PrimaryInventory> si = primaryRepo.findByProductId(Long.parseLong(namesList1[0]));
        if (si.isPresent()) {

            long prevRecieveQuantity = si.get().getReceivedInventory();
            long prevOnhandQuantity = si.get().getOnHand();
            long newInQuantity = Long.parseLong(namesList1[2]);

            long receive = prevRecieveQuantity + newInQuantity;
            long onhand = prevOnhandQuantity + newInQuantity;
            si.get().setReceivedInventory(receive);
            si.get().setOnHand(onhand);
            si.get().setActive(true);
            si.get().setUpdatedAt(new Date());
            si.get().setSafetyStock(si.get().getSafetyStock());
            // entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
            si.get().setCreatedBy(1);
            primaryRepo.save(si.get());
        } else {
            PrimaryInventory pi = new PrimaryInventory();
            pi.setProductId(Long.parseLong(namesList1[0]));
            pi.setCreatedAt(new Date());
            pi.setUpdatedAt(new Date());
            pi.setOnHand(Long.parseLong(namesList1[2]));
            pi.setReceivedInventory(Long.parseLong(namesList1[2]));
            pi.setMinimumQty(0l);
            pi.setStartingInventory(Long.parseLong(namesList1[2]));
            pi.setActive(true);
            primaryRepo.save(pi);
        }
    }

    @Override
    public Optional<String> getTotalPurchase() {
        // TODO Auto-generated method stub
        return purchaseOrderrepo.getTotalPurchase();
    }

    @Override
    public Optional<String> getTotalPurchaseToday() {
        // TODO Auto-generated method stub
        return purchaseOrderrepo.getTotalPurchaseToday();
    }

    @Override
    public Optional<String> getTotalPurchaseMonth() {
        // TODO Auto-generated method stub
        return purchaseOrderrepo.getTotalPurchaseMonth();
    }

    @Override
    public PurchaseProduct save(PurchaseProduct entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        PurchaseProduct purchaseProduct = new PurchaseProduct();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<PurchaseProduct> list = new HashSet<PurchaseProduct>();
            if (org.isPresent()) {
                list = org.get().getPurchaseProducts();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                purchaseProduct = purchaseOrderrepo.save(entity);
                list.add(purchaseProduct);
                org.get().setPurchaseProducts(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public Optional<PurchaseProduct> findByPurchaseNumber(String purchaseNumber) {
        // TODO Auto-generated method stub
        return purchaseOrderrepo.findByPurchaseNumber(purchaseNumber);
    }

}
