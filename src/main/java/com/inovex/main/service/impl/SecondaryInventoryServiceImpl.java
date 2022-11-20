package com.inovex.main.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inovex.main.entity.SecondaryInventory;
import com.inovex.main.json.response.SecondaryProductReport;
import com.inovex.main.repository.SecondaryInventoryRepo;
import com.inovex.main.service.SecondaryInventoryService;

@Service
@Transactional
public class SecondaryInventoryServiceImpl implements SecondaryInventoryService {

    @Autowired
    SecondaryInventoryRepo secondaryinventoryRepo;

    @Override
    public SecondaryInventory save(SecondaryInventory secondaryInventory) {

        return secondaryinventoryRepo.save(secondaryInventory);
    }

    @Override
    public List<SecondaryInventory> getAll() {

        return secondaryinventoryRepo.findAll();
    }

    @Override
    public String findProductId(String productId) {

        return secondaryinventoryRepo.findProductId(productId);
    }

    @Override
    public int updatercvandOnhand(String productId, String receivedInventory, String onHand) {

        return secondaryinventoryRepo.updatercvandOnhand(productId, receivedInventory, onHand);
    }

    @Override
    public int updatercvandOnhandAndShipped(String productId, String receivedInventory, String onHand,
            String shippedInventory) {

        return secondaryinventoryRepo.updatercvandOnhandAndShipped(productId, receivedInventory, onHand,
                shippedInventory);
    }

    @Override
    public List<SecondaryInventory> recvAndonhand(String productId) {

        return secondaryinventoryRepo.recvAndonhand(productId);
    }

    @Override
    public long getProductQuantity(long productId) {
        // TODO Auto-generated method stub
        return secondaryinventoryRepo.getProductQuantity(productId);
    }

    @Override
    public int updateShippedandOnhandAfterOrderProceed(long productId, long onHand, long shippedInventory) {
        // TODO Auto-generated method stub
        return secondaryinventoryRepo.updateShippedandOnhandAfterOrderProceed(productId, onHand, shippedInventory);
    }

    /**
     * Get the Secondary Inventory Product List By custom Secondary report Param
     *
     * @param secondaryProductReport
     * @return List<SecondaryInventoryProductList>
     */
    @Override
    public List<SecondaryInventory> getSeconDaryInventoryByCusotomParam(SecondaryProductReport secondaryProductReport) {
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
    public Optional<SecondaryInventory> findByProductId(long productId) {
        // TODO Auto-generated method stub
        return secondaryinventoryRepo.findByProductId(productId);
    }

    @Override
    public Optional<SecondaryInventory> findByProductIdAndDistributor(long productId, long distId) {
        // TODO Auto-generated method stub
        return secondaryinventoryRepo.findByProductIdAndDistributor(productId, distId);
    }

	@Override
	public List<SecondaryInventory> getSecondaryInventoryCurrentStoctBySingleDealer(long dealerId) {
		// TODO Auto-generated method stub
		return secondaryinventoryRepo.findAllSecondaryInventoryBySingleDealer(dealerId);
	}

}
