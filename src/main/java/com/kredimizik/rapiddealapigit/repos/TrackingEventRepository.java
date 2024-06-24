package com.kredimizik.rapiddealapigit.repos;

import com.kredimizik.rapiddealapigit.domain.Shipment;
import com.kredimizik.rapiddealapigit.domain.TrackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {

    TrackingEvent findFirstByShipment(Shipment shipment);

}
