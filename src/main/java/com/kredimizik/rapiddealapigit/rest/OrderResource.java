package com.kredimizik.rapiddealapigit.rest;

import com.kredimizik.rapiddealapigit.model.OrderDTO;
import com.kredimizik.rapiddealapigit.service.OrderService;
import com.kredimizik.rapiddealapigit.util.ReferencedException;
import com.kredimizik.rapiddealapigit.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderResource {

    private final OrderService orderService;

    public OrderResource(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{oderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable(name = "oderId") final Long oderId) {
        return ResponseEntity.ok(orderService.get(oderId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOrder(@RequestBody @Valid final OrderDTO orderDTO) {
        final Long createdOderId = orderService.create(orderDTO);
        return new ResponseEntity<>(createdOderId, HttpStatus.CREATED);
    }

    @PutMapping("/{oderId}")
    public ResponseEntity<Long> updateOrder(@PathVariable(name = "oderId") final Long oderId,
            @RequestBody @Valid final OrderDTO orderDTO) {
        orderService.update(oderId, orderDTO);
        return ResponseEntity.ok(oderId);
    }

    @DeleteMapping("/{oderId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOrder(@PathVariable(name = "oderId") final Long oderId) {
        final ReferencedWarning referencedWarning = orderService.getReferencedWarning(oderId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        orderService.delete(oderId);
        return ResponseEntity.noContent().build();
    }

}
