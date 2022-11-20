package com.inovex.main.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.IncomingProduct;
import com.inovex.main.entity.SecondaryInventory;
import com.inovex.main.repository.IncominggProductRepo;
import com.inovex.main.repository.SecondaryInventoryRepo;
import com.inovex.main.service.IncomingProductService;

@Service
@Transactional
public class IncomingProductServiceImpl implements IncomingProductService {

    @Autowired
    IncominggProductRepo incomingProductRepo;
    @Autowired
    SecondaryInventoryRepo secondaryRepo;

    @Override
    public Optional<IncomingProduct> findById(Long id) {
        // TODO Auto-generated method stub
        return incomingProductRepo.findById(id);
    }

    @Override
    public List<IncomingProduct> findAll() {
        // TODO Auto-generated method stub
        return incomingProductRepo.findAll();
    }

    @Override
    public IncomingProduct getIncomingProduct(Long id) {
        // TODO Auto-generated method stub
        Optional<IncomingProduct> incomingProduct = incomingProductRepo.findById(id);
        if (incomingProduct.isPresent()) {
            return incomingProduct.get();
        }
        throw new NotFoundException();
    }

    @Override
    public IncomingProduct save(IncomingProduct incomingProduct, HttpServletRequest request) {
        Optional<SecondaryInventory> si = secondaryRepo.findByProductId(incomingProduct.getProductId1());
        if (si.isPresent()) {
            System.out.println("called is si");
            long prevRecieveQuantity = si.get().getReceivedInventory();
            long prevOnhandQuantity = si.get().getOnHand();
            long newInQuantity = Long.parseLong(incomingProduct.getReceivedQty());

            long receive = prevRecieveQuantity + newInQuantity;
            long onhand = prevOnhandQuantity + newInQuantity;
            si.get().setReceivedInventory(receive);
            si.get().setOnHand(onhand);
            si.get().setCreatedAt(new Date());
            si.get().setActive(true);
            si.get().setUpdatedAt(new Date());
            // si.setCreatedBy((long) request.getSession().getAttribute("userId"));
            si.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
            secondaryRepo.save(si.get());
            incomingProduct.setCreatedAt(new Date());
            incomingProduct.setActive(true);
            incomingProduct.setUpdatedAt(new Date());
            // entity.setCreatedBy((long) request.getSession().getAttribute("userId"));
            incomingProduct.setCreatedBy(1);
            return incomingProductRepo.save(incomingProduct);
        } else {
            throw new NotFoundException("Product not found!");
        }

    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

        incomingProductRepo.deleteById(id);

    }

    @Override
    public IncomingProduct update(IncomingProduct incomingProduct, Long id, HttpServletRequest request) {

        Optional<IncomingProduct> incomingProductUpdate = incomingProductRepo.findById(id);
        incomingProductUpdate.get().setProductId1(incomingProduct.getProductId1());
        incomingProductUpdate.get().setPurchasePrice(incomingProduct.getPurchasePrice());
        incomingProductUpdate.get().setProductIdUnitprice(incomingProduct.getProductIdUnitprice());
        long prevInQuantity = Long.parseLong(incomingProductUpdate.get().getReceivedQty());
        incomingProductUpdate.get().setReceivedQty(incomingProduct.getReceivedQty());
        IncomingProduct inPro = incomingProductRepo.save(incomingProductUpdate.get());
        if (inPro != null) {
            Optional<SecondaryInventory> si = secondaryRepo.findByProductId(incomingProduct.getProductId1());
            if (si.isPresent()) {

                long prevRecieveQuantity = si.get().getReceivedInventory();
                long prevOnhandQuantity = si.get().getOnHand();
                long newInQuantity = Long.parseLong(inPro.getReceivedQty());

                long receive = prevRecieveQuantity + newInQuantity - prevInQuantity;
                long onhand = prevOnhandQuantity + newInQuantity - prevInQuantity;
                si.get().setReceivedInventory(receive);
                si.get().setOnHand(onhand);

                si.get().setCreatedBy((long) request.getSession().getAttribute("userId"));
                si.get().setUpdatedAt(new Date());
                secondaryRepo.save(si.get());

            }
            return inPro;
        } else {
            throw new NotFoundException("Product not found!");
        }

    }

    @Override
    public Long countTotalRecieve(long productId) {
        // TODO Auto-generated method stub
        return incomingProductRepo.countTotalRecieve(productId);
    }

    @Override
    public Optional<IncomingProduct> findByProductId(Long productId) {
        // TODO Auto-generated method stub
        return incomingProductRepo.findByProductId1(productId);
    }

}
