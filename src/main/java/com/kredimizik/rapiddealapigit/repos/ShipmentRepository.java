package com.kredimizik.rapiddealapigit.repos;

import com.kredimizik.rapiddealapigit.domain.Box;
import com.kredimizik.rapiddealapigit.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Shipment findFirstByBox(Box box);

}
