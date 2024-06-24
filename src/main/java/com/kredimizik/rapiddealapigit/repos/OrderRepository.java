package com.kredimizik.rapiddealapigit.repos;

import com.kredimizik.rapiddealapigit.domain.Client;
import com.kredimizik.rapiddealapigit.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findFirstByClient(Client client);

}
