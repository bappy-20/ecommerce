package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import com.inovex.main.entity.PrimaryInventory;
import com.inovex.main.json.response.SecondaryProductReport;

public interface PrimaryInventoryService {
    Optional<PrimaryInventory> findByProductId(long productId);

    public PrimaryInventory save(PrimaryInventory secondaryInventory);

    public List<PrimaryInventory> getAll();

    public String findProductId(String productId);

    int updatercvandOnhand(String productId, String receivedInventory, String onHand);

    int updatercvandOnhandAndShipped(String productId, String receivedInventory, String onHand,
            String shippedInventory);

    public List<PrimaryInventory> recvAndonhand(String productId);

    public long getProductQuantity(long productId);

    int updateShippedandOnhandAfterOrderProceed(long productId, long onHand, long shippedInventory);

    List<PrimaryInventory> getSeconDaryInventoryByCusotomParam(SecondaryProductReport secondaryProductReport);

}
