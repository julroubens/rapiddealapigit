package com.kredimizik.rapiddealapigit.service;

import com.kredimizik.rapiddealapigit.domain.Box;
import com.kredimizik.rapiddealapigit.domain.Order;
import com.kredimizik.rapiddealapigit.domain.Shipment;
import com.kredimizik.rapiddealapigit.model.BoxDTO;
import com.kredimizik.rapiddealapigit.repos.BoxRepository;
import com.kredimizik.rapiddealapigit.repos.OrderRepository;
import com.kredimizik.rapiddealapigit.repos.ShipmentRepository;
import com.kredimizik.rapiddealapigit.util.NotFoundException;
import com.kredimizik.rapiddealapigit.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BoxService {

    private final BoxRepository boxRepository;
    private final OrderRepository orderRepository;
    private final ShipmentRepository shipmentRepository;

    public BoxService(final BoxRepository boxRepository, final OrderRepository orderRepository,
            final ShipmentRepository shipmentRepository) {
        this.boxRepository = boxRepository;
        this.orderRepository = orderRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public List<BoxDTO> findAll() {
        final List<Box> boxes = boxRepository.findAll(Sort.by("packegeId"));
        return boxes.stream()
                .map(box -> mapToDTO(box, new BoxDTO()))
                .toList();
    }

    public BoxDTO get(final Long packegeId) {
        return boxRepository.findById(packegeId)
                .map(box -> mapToDTO(box, new BoxDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BoxDTO boxDTO) {
        final Box box = new Box();
        mapToEntity(boxDTO, box);
        return boxRepository.save(box).getPackegeId();
    }

    public void update(final Long packegeId, final BoxDTO boxDTO) {
        final Box box = boxRepository.findById(packegeId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(boxDTO, box);
        boxRepository.save(box);
    }

    public void delete(final Long packegeId) {
        boxRepository.deleteById(packegeId);
    }

    private BoxDTO mapToDTO(final Box box, final BoxDTO boxDTO) {
        boxDTO.setPackegeId(box.getPackegeId());
        boxDTO.setDescription(box.getDescription());
        boxDTO.setWeight(box.getWeight());
        boxDTO.setDimentions(box.getDimentions());
        boxDTO.setOrder(box.getOrder() == null ? null : box.getOrder().getOderId());
        return boxDTO;
    }

    private Box mapToEntity(final BoxDTO boxDTO, final Box box) {
        box.setDescription(boxDTO.getDescription());
        box.setWeight(boxDTO.getWeight());
        box.setDimentions(boxDTO.getDimentions());
        final Order order = boxDTO.getOrder() == null ? null : orderRepository.findById(boxDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        box.setOrder(order);
        return box;
    }

    public ReferencedWarning getReferencedWarning(final Long packegeId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Box box = boxRepository.findById(packegeId)
                .orElseThrow(NotFoundException::new);
        final Shipment boxShipment = shipmentRepository.findFirstByBox(box);
        if (boxShipment != null) {
            referencedWarning.setKey("box.shipment.box.referenced");
            referencedWarning.addParam(boxShipment.getShipmentId());
            return referencedWarning;
        }
        return null;
    }

}
