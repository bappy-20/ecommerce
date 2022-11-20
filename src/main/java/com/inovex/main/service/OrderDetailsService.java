package com.inovex.main.service;

import java.util.List;
import java.util.Optional;

import com.inovex.main.entity.OrderDetails;

public interface OrderDetailsService {
    Optional<OrderDetails> findById(Long id);

    List<OrderDetails> findAll();

    OrderDetails save(OrderDetails entity);

    OrderDetails update(OrderDetails entity);

    List<OrderDetails> saveAll(List<OrderDetails> entities);

    void deleteById(Long id);

}
