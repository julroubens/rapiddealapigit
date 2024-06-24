package com.kredimizik.rapiddealapigit.repos;

import com.kredimizik.rapiddealapigit.domain.Box;
import com.kredimizik.rapiddealapigit.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoxRepository extends JpaRepository<Box, Long> {

    Box findFirstByOrder(Order order);

}
