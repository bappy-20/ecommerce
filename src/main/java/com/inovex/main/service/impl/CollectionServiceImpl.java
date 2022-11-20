package com.inovex.main.service.impl;

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

import com.inovex.main.entity.CollectionModel;
import com.inovex.main.entity.OrderHistory;
import com.inovex.main.entity.Organization;
import com.inovex.main.repository.CollectionRepo;
import com.inovex.main.repository.OrderHistoryRepo;
import com.inovex.main.repository.OrganizationRepository;
import com.inovex.main.service.CollectionService;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionRepo collectionRepo;
    @Autowired
    OrderHistoryRepo orderRepo;
    @Autowired
    OrganizationRepository orgRepo;

    @Override
    public Optional<CollectionModel> findById(Long id) {
        // TODO Auto-generated method stub
        return collectionRepo.findById(id);
    }

    @Override
    public List<CollectionModel> findAll() {
        // TODO Auto-generated method stub
        return collectionRepo.findAll();
    }

    @Override
    public List<CollectionModel> saveAll(List<CollectionModel> entities) {
        // TODO Auto-generated method stub
        return collectionRepo.saveAll(entities);
    }

    @Override
    public void deleteById(Long id, HttpServletRequest request) {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                collectionRepo.deleteFromOrg(orgid, id);
                collectionRepo.deleteById(id);
            } else {
                System.out.println("org not found");

            }
        }
    }

    @Override
    public List<CollectionModel> findCollectionDetails(String orderId) {
        // TODO Auto-generated method stub
        return collectionRepo.findCollectionDetails(orderId);
    }

    @Override
    public long getReceivedAmount(String orderId) {
        // TODO Auto-generated method stub
        Optional<String> receivedAmount1 = collectionRepo.getReceivedAmount(orderId);
        long receivedAmount = 0;
        if (receivedAmount1.isPresent()) {
            receivedAmount = Long.parseLong(receivedAmount1.get());
        }
        return receivedAmount;
    }

    @Override
    public CollectionModel save(CollectionModel entity, HttpServletRequest request) {
        // TODO Auto-generated method stub
        CollectionModel collectionModel = new CollectionModel();
        if (request.getSession().getAttribute("orgId") != null) {
            long id = (long) request.getSession().getAttribute("orgId");

            Optional<Organization> org = orgRepo.findById(id);
            Set<CollectionModel> list = new HashSet<CollectionModel>();
            if (org.isPresent()) {
                list = org.get().getCollectionModels();
                entity.setCreatedAt(new Date());
                entity.setActive(true);
                entity.setUpdatedAt(new Date());
                entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
                collectionModel = collectionRepo.save(entity);
                list.add(collectionModel);
                org.get().setCollectionModels(list);
                orgRepo.save(org.get());
            }

        } else {
            System.out.println("Org is Null");
        }
        return null;
    }

    @Override
    public CollectionModel save(CollectionModel entity, long orgId) {
        // TODO Auto-generated method stub
        Optional<Organization> org = orgRepo.findById(orgId);
        CollectionModel entity1 = new CollectionModel();
        if (org.isPresent()) {
            Set<CollectionModel> clList = new HashSet<>();
            clList = org.get().getCollectionModels();

            Optional<OrderHistory> order = orderRepo.findByOrderId(entity.getOrderId());
            if (order.isPresent()) {
                if (entity.getDueAmount() <= 0) {
                    order.get().setDueAmount(0);
                    order.get().setPaymentStatus("Paid");
                    order.get().setCollectedAmount(order.get().getTotal());
                    order.get().setOrderStatus("Delivered");
                    order.get().setDeliveryDate(new Date());
                    orderRepo.save(order.get());
                    entity.setCreatedAt(new Date());
                    entity.setUpdatedAt(new Date());
                    entity.setCollectionDate(new Date());
                    entity.setCreatedBy(1);
                    entity.setActive(true);
                    entity1 = collectionRepo.save(entity);
                } else {
                    Optional<String> receivedAmount1 = collectionRepo.getReceivedAmount(entity.getOrderId());
                    long receivedAmount = 0;
                    if (receivedAmount1.isPresent()) {
                        receivedAmount = Long.parseLong(receivedAmount1.get());
                    }
                    order.get().setDueAmount(entity.getDueAmount());
                    order.get().setPaymentStatus("Non-Paid");
                    if (receivedAmount == 0) {
                        order.get().setCollectedAmount(entity.getRecieveAmount());
                    } else {
                        order.get().setCollectedAmount(receivedAmount + entity.getRecieveAmount());
                    }
                    order.get().setOrderStatus("Delivered");
                    order.get().setDeliveryDate(new Date());
                    orderRepo.save(order.get());
                    entity.setCreatedAt(new Date());
                    entity.setUpdatedAt(new Date());
                    entity.setCreatedBy(1);
                    entity.setCollectionDate(new Date());
                    entity.setActive(true);
                    entity1 = collectionRepo.save(entity);
                }
                clList.add(entity1);
                org.get().setCollectionModels(clList);
                orgRepo.save(org.get());
            }
        }
        return entity1;
    }

    @Override
    public List<Object> findAllByDateRange(HttpServletRequest request, String date1, String date2) {
        // TODO Auto-generated method stub
        List<Object> list = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                System.out.println(date1);
                System.out.println(date2);
                list = collectionRepo.findAllFromDateRange(date1, date2);
            }
        }
        return list;

    }

    @Override
    public List<Object> findtotalSumByDateRange(HttpServletRequest request, String date1, String date2) {
        // TODO Auto-generated method stub
        List<Object> list = new ArrayList<>();
        if (request.getSession().getAttribute("orgId") != null) {
            long orgid = (long) request.getSession().getAttribute("orgId");
            Optional<Organization> org = orgRepo.findById(orgid);
            if (org.isPresent()) {
                list = collectionRepo.findTotalSumFromDateRange(date1, date2);
            }
        }
        return list;
    }

}
