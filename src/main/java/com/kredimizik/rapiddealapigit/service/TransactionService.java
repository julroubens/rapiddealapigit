package com.kredimizik.rapiddealapigit.service;

import com.kredimizik.rapiddealapigit.domain.Client;
import com.kredimizik.rapiddealapigit.domain.Shipment;
import com.kredimizik.rapiddealapigit.domain.Transaction;
import com.kredimizik.rapiddealapigit.model.TransactionDTO;
import com.kredimizik.rapiddealapigit.repos.ClientRepository;
import com.kredimizik.rapiddealapigit.repos.ShipmentRepository;
import com.kredimizik.rapiddealapigit.repos.TransactionRepository;
import com.kredimizik.rapiddealapigit.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final ShipmentRepository shipmentRepository;

    public TransactionService(final TransactionRepository transactionRepository,
            final ClientRepository clientRepository, final ShipmentRepository shipmentRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public List<TransactionDTO> findAll() {
        final List<Transaction> transactions = transactionRepository.findAll(Sort.by("transactionId"));
        return transactions.stream()
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .toList();
    }

    public TransactionDTO get(final Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TransactionDTO transactionDTO) {
        final Transaction transaction = new Transaction();
        mapToEntity(transactionDTO, transaction);
        return transactionRepository.save(transaction).getTransactionId();
    }

    public void update(final Long transactionId, final TransactionDTO transactionDTO) {
        final Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transactionDTO, transaction);
        transactionRepository.save(transaction);
    }

    public void delete(final Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    private TransactionDTO mapToDTO(final Transaction transaction,
            final TransactionDTO transactionDTO) {
        transactionDTO.setTransactionId(transaction.getTransactionId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDept(transaction.getDept());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setPaymentMethod(transaction.getPaymentMethod());
        transactionDTO.setStatus(transaction.getStatus());
        transactionDTO.setClient(transaction.getClient() == null ? null : transaction.getClient().getClientId());
        transactionDTO.setShipment(transaction.getShipment() == null ? null : transaction.getShipment().getShipmentId());
        return transactionDTO;
    }

    private Transaction mapToEntity(final TransactionDTO transactionDTO,
            final Transaction transaction) {
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDept(transactionDTO.getDept());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setPaymentMethod(transactionDTO.getPaymentMethod());
        transaction.setStatus(transactionDTO.getStatus());
        final Client client = transactionDTO.getClient() == null ? null : clientRepository.findById(transactionDTO.getClient())
                .orElseThrow(() -> new NotFoundException("client not found"));
        transaction.setClient(client);
        final Shipment shipment = transactionDTO.getShipment() == null ? null : shipmentRepository.findById(transactionDTO.getShipment())
                .orElseThrow(() -> new NotFoundException("shipment not found"));
        transaction.setShipment(shipment);
        return transaction;
    }

}
