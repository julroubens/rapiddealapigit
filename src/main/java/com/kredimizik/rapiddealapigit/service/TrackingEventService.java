package com.kredimizik.rapiddealapigit.service;

import com.kredimizik.rapiddealapigit.domain.Shipment;
import com.kredimizik.rapiddealapigit.domain.TrackingEvent;
import com.kredimizik.rapiddealapigit.model.TrackingEventDTO;
import com.kredimizik.rapiddealapigit.repos.ShipmentRepository;
import com.kredimizik.rapiddealapigit.repos.TrackingEventRepository;
import com.kredimizik.rapiddealapigit.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TrackingEventService {

    private final TrackingEventRepository trackingEventRepository;
    private final ShipmentRepository shipmentRepository;

    public TrackingEventService(final TrackingEventRepository trackingEventRepository,
            final ShipmentRepository shipmentRepository) {
        this.trackingEventRepository = trackingEventRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public List<TrackingEventDTO> findAll() {
        final List<TrackingEvent> trackingEvents = trackingEventRepository.findAll(Sort.by("eventId"));
        return trackingEvents.stream()
                .map(trackingEvent -> mapToDTO(trackingEvent, new TrackingEventDTO()))
                .toList();
    }

    public TrackingEventDTO get(final Long eventId) {
        return trackingEventRepository.findById(eventId)
                .map(trackingEvent -> mapToDTO(trackingEvent, new TrackingEventDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TrackingEventDTO trackingEventDTO) {
        final TrackingEvent trackingEvent = new TrackingEvent();
        mapToEntity(trackingEventDTO, trackingEvent);
        return trackingEventRepository.save(trackingEvent).getEventId();
    }

    public void update(final Long eventId, final TrackingEventDTO trackingEventDTO) {
        final TrackingEvent trackingEvent = trackingEventRepository.findById(eventId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(trackingEventDTO, trackingEvent);
        trackingEventRepository.save(trackingEvent);
    }

    public void delete(final Long eventId) {
        trackingEventRepository.deleteById(eventId);
    }

    private TrackingEventDTO mapToDTO(final TrackingEvent trackingEvent,
            final TrackingEventDTO trackingEventDTO) {
        trackingEventDTO.setEventId(trackingEvent.getEventId());
        trackingEventDTO.setEventType(trackingEvent.getEventType());
        trackingEventDTO.setEventDate(trackingEvent.getEventDate());
        trackingEventDTO.setLocation(trackingEvent.getLocation());
        trackingEventDTO.setDescription(trackingEvent.getDescription());
        trackingEventDTO.setShipment(trackingEvent.getShipment() == null ? null : trackingEvent.getShipment().getShipmentId());
        return trackingEventDTO;
    }

    private TrackingEvent mapToEntity(final TrackingEventDTO trackingEventDTO,
            final TrackingEvent trackingEvent) {
        trackingEvent.setEventType(trackingEventDTO.getEventType());
        trackingEvent.setEventDate(trackingEventDTO.getEventDate());
        trackingEvent.setLocation(trackingEventDTO.getLocation());
        trackingEvent.setDescription(trackingEventDTO.getDescription());
        final Shipment shipment = trackingEventDTO.getShipment() == null ? null : shipmentRepository.findById(trackingEventDTO.getShipment())
                .orElseThrow(() -> new NotFoundException("shipment not found"));
        trackingEvent.setShipment(shipment);
        return trackingEvent;
    }

}
