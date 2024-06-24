package com.kredimizik.rapiddealapigit.rest;

import com.kredimizik.rapiddealapigit.model.TransactionDTO;
import com.kredimizik.rapiddealapigit.service.TransactionService;
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
@RequestMapping(value = "/api/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionResource {

    private final TransactionService transactionService;

    public TransactionResource(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransaction(
            @PathVariable(name = "transactionId") final Long transactionId) {
        return ResponseEntity.ok(transactionService.get(transactionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTransaction(
            @RequestBody @Valid final TransactionDTO transactionDTO) {
        final Long createdTransactionId = transactionService.create(transactionDTO);
        return new ResponseEntity<>(createdTransactionId, HttpStatus.CREATED);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Long> updateTransaction(
            @PathVariable(name = "transactionId") final Long transactionId,
            @RequestBody @Valid final TransactionDTO transactionDTO) {
        transactionService.update(transactionId, transactionDTO);
        return ResponseEntity.ok(transactionId);
    }

    @DeleteMapping("/{transactionId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable(name = "transactionId") final Long transactionId) {
        transactionService.delete(transactionId);
        return ResponseEntity.noContent().build();
    }

}
