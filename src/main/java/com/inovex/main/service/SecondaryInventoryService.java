package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import com.inovex.main.entity.SecondaryInventory;
import com.inovex.main.json.response.SecondaryProductReport;

public interface SecondaryInventoryService {
    Optional<SecondaryInventory> findByProductId(long productId);

    Optional<SecondaryInventory> findByProductIdAndDistributor(long productId, long distId);

    public SecondaryInventory save(SecondaryInventory secondaryInventory);

    public List<SecondaryInventory> getAll();

    public String findProductId(String productId);

    int updatercvandOnhand(String productId, String receivedInventory, String onHand);

    int updatercvandOnhandAndShipped(String productId, String receivedInventory, String onHand,
            String shippedInventory);

    public List<SecondaryInventory> recvAndonhand(String productId);

    public long getProductQuantity(long productId);

    int updateShippedandOnhandAfterOrderProceed(long productId, long onHand, long shippedInventory);

    List<SecondaryInventory> getSeconDaryInventoryByCusotomParam(SecondaryProductReport secondaryProductReport);
    
    List<SecondaryInventory> getSecondaryInventoryCurrentStoctBySingleDealer(long dealerId);
    //public long getProductQuantity1(long productId) ;
}
