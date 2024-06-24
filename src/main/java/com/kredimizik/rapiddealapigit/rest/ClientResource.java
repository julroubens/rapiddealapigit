package com.kredimizik.rapiddealapigit.rest;

import com.kredimizik.rapiddealapigit.model.ClientDTO;
import com.kredimizik.rapiddealapigit.service.ClientService;
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
@RequestMapping(value = "/api/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientResource {

    private final ClientService clientService;

    public ClientResource(final ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClient(
            @PathVariable(name = "clientId") final Long clientId) {
        return ResponseEntity.ok(clientService.get(clientId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createClient(@RequestBody @Valid final ClientDTO clientDTO) {
        final Long createdClientId = clientService.create(clientDTO);
        return new ResponseEntity<>(createdClientId, HttpStatus.CREATED);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Long> updateClient(@PathVariable(name = "clientId") final Long clientId,
            @RequestBody @Valid final ClientDTO clientDTO) {
        clientService.update(clientId, clientDTO);
        return ResponseEntity.ok(clientId);
    }

    @DeleteMapping("/{clientId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteClient(@PathVariable(name = "clientId") final Long clientId) {
        final ReferencedWarning referencedWarning = clientService.getReferencedWarning(clientId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        clientService.delete(clientId);
        return ResponseEntity.noContent().build();
    }

}
