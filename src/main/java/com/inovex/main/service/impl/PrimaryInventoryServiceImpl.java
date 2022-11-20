package com.inovex.main.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.json.response.SecondaryProductReport;
import com.inovex.main.repository.PrimaryInventoryRepo;
import com.inovex.main.service.PrimaryInventoryService;

@Service
@Transactional
public class PrimaryInventoryServiceImpl implements PrimaryInventoryService {

    @Autowired
    PrimaryInventoryRepo primaryInventoryRepo;

    @Override
    public PrimaryInventory save(PrimaryInventory secondaryInventory) {

        return primaryInventoryRepo.save(secondaryInventory);
    }

    @Override
    public List<PrimaryInventory> getAll() {

        return primaryInventoryRepo.findAll();
    }

    @Override
    public String findProductId(String productId) {

        return primaryInventoryRepo.findProductId(productId);
    }

    @Override
    public int updatercvandOnhand(String productId, String receivedInventory, String onHand) {

        return primaryInventoryRepo.updatercvandOnhand(productId, receivedInventory, onHand);
    }

    @Override
    public int updatercvandOnhandAndShipped(String productId, String receivedInventory, String onHand,
            String shippedInventory) {

        return primaryInventoryRepo.updatercvandOnhandAndShipped(productId, receivedInventory, onHand,
                shippedInventory);
    }

    @Override
    public List<PrimaryInventory> recvAndonhand(String productId) {

        return primaryInventoryRepo.recvAndonhand(productId);
    }

    @Override
    public long getProductQuantity(long productId) {
        // TODO Auto-generated method stub
        return primaryInventoryRepo.getProductQuantity(productId);
    }

    @Override
    public int updateShippedandOnhandAfterOrderProceed(long productId, long onHand, long shippedInventory) {
        // TODO Auto-generated method stub
        return primaryInventoryRepo.updateShippedandOnhandAfterOrderProceed(productId, onHand, shippedInventory);
    }

    /**
     * Get the Secondary Inventory Product List By custom Secondary report Param
     *
     * @param secondaryProductReport
     * @return List<SecondaryInventoryProductList>
     */
    @Override
    public List<PrimaryInventory> getSeconDaryInventoryByCusotomParam(SecondaryProductReport secondaryProductReport) {
        Date startDate = new Date(-1);
        Date endDate = new Date();
        String distributorId = "";
        String category = "";
        String productId = "";
        try {
            if (secondaryProductReport.getStartDate() != null && secondaryProductReport.getStartDate() != "") {
                startDate = new SimpleDateFormat("dd/MM/yyyy").parse(secondaryProductReport.getStartDate());
            }
            if (secondaryProductReport.getEndDate() != null && secondaryProductReport.getEndDate() != "") {
                endDate = new SimpleDateFormat("dd/MM/yyyy").parse(secondaryProductReport.getEndDate());
            }
            if (secondaryProductReport.getProductCategory() != null
                    && secondaryProductReport.getProductCategory() != "") {
                category = secondaryProductReport.getProductCategory();
            }
            if (secondaryProductReport.getProductId() != null && secondaryProductReport.getProductId() != "") {
                productId = secondaryProductReport.getProductId();
            }
            if (secondaryProductReport.getDistributorId() != null && secondaryProductReport.getDistributorId() != "") {
                distributorId = secondaryProductReport.getDistributorId();
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        if (distributorId == "") {

            if (productId != "" && category != "") {
                // return
                // secondaryinventoryRepo.secondaryInventoryByProductIdAndProductCategory(productId,
                // category,
                // startDate, endDate);
            } else if (productId == "" && category != "") {
                // return secondaryinventoryRepo.secondaryInventoryByCateogoryAndDate(category,
                // startDate, endDate);
            } else if (productId != "" && category == "") {
                // return secondaryinventoryRepo.secondaryInventoryByProductIdAndDate(productId,
                // startDate, endDate);
            }
        } else if (distributorId != "" && distributorId != null) {

            if (productId != "" && category != "") {
                /*
                 * return secondaryinventoryRepo.secondaryInventoryByCustomParam(distributorId,
                 * productId, category, startDate, endDate);
                 */
            } else if (productId == "" && category != "") {
                /*
                 * return
                 * secondaryinventoryRepo.secondaryInventoryByDistributorIdCateogoryAndDate(
                 * distributorId, category, startDate, endDate);
                 */
            } else if (productId != "" && category == "") {
                /*
                 * return
                 * secondaryinventoryRepo.secondaryInventoryByDistributorIdAndProductIdAndDate(
                 * distributorId, productId, startDate, endDate);
                 */
            } else {
                /*
                 * return secondaryinventoryRepo.secondaryInventoryByDistributorIdAndDate(
                 * distributorId, startDate, endDate);
                 */
            }
        }
        /*
         * return secondaryinventoryRepo.secondaryInventoryByDate(startDate, endDate);
         */
        return null;
    }

    @Override
    public Optional<PrimaryInventory> findByProductId(long productId) {
        // TODO Auto-generated method stub
        return primaryInventoryRepo.findByProductId(productId);
    }
}
