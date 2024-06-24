package com.kredimizik.rapiddealapigit.service;

import com.kredimizik.rapiddealapigit.domain.Box;
import com.kredimizik.rapiddealapigit.domain.Client;
import com.kredimizik.rapiddealapigit.domain.Order;
import com.kredimizik.rapiddealapigit.model.OrderDTO;
import com.kredimizik.rapiddealapigit.repos.BoxRepository;
import com.kredimizik.rapiddealapigit.repos.ClientRepository;
import com.kredimizik.rapiddealapigit.repos.OrderRepository;
import com.kredimizik.rapiddealapigit.util.NotFoundException;
import com.kredimizik.rapiddealapigit.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final BoxRepository boxRepository;

    public OrderService(final OrderRepository orderRepository,
            final ClientRepository clientRepository, final BoxRepository boxRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.boxRepository = boxRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("oderId"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long oderId) {
        return orderRepository.findById(oderId)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getOderId();
    }

    public void update(final Long oderId, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(oderId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long oderId) {
        orderRepository.deleteById(oderId);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setOderId(order.getOderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setClient(order.getClient() == null ? null : order.getClient().getClientId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(orderDTO.getStatus());
        final Client client = orderDTO.getClient() == null ? null : clientRepository.findById(orderDTO.getClient())
                .orElseThrow(() -> new NotFoundException("client not found"));
        order.setClient(client);
        return order;
    }

    public ReferencedWarning getReferencedWarning(final Long oderId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Order order = orderRepository.findById(oderId)
                .orElseThrow(NotFoundException::new);
        final Box orderBox = boxRepository.findFirstByOrder(order);
        if (orderBox != null) {
            referencedWarning.setKey("order.box.order.referenced");
            referencedWarning.addParam(orderBox.getPackegeId());
            return referencedWarning;
        }
        return null;
    }

}
