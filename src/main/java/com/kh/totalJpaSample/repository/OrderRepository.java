package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
