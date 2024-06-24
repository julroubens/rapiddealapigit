package com.kredimizik.rapiddealapigit.rest;

import com.kredimizik.rapiddealapigit.model.BoxDTO;
import com.kredimizik.rapiddealapigit.service.BoxService;
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
@RequestMapping(value = "/api/boxes", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoxResource {

    private final BoxService boxService;

    public BoxResource(final BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping
    public ResponseEntity<List<BoxDTO>> getAllBoxes() {
        return ResponseEntity.ok(boxService.findAll());
    }

    @GetMapping("/{packegeId}")
    public ResponseEntity<BoxDTO> getBox(@PathVariable(name = "packegeId") final Long packegeId) {
        return ResponseEntity.ok(boxService.get(packegeId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBox(@RequestBody @Valid final BoxDTO boxDTO) {
        final Long createdPackegeId = boxService.create(boxDTO);
        return new ResponseEntity<>(createdPackegeId, HttpStatus.CREATED);
    }

    @PutMapping("/{packegeId}")
    public ResponseEntity<Long> updateBox(@PathVariable(name = "packegeId") final Long packegeId,
            @RequestBody @Valid final BoxDTO boxDTO) {
        boxService.update(packegeId, boxDTO);
        return ResponseEntity.ok(packegeId);
    }

    @DeleteMapping("/{packegeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBox(@PathVariable(name = "packegeId") final Long packegeId) {
        final ReferencedWarning referencedWarning = boxService.getReferencedWarning(packegeId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        boxService.delete(packegeId);
        return ResponseEntity.noContent().build();
    }

}
