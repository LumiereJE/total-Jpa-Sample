package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {


}
