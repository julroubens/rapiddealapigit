package com.kredimizik.rapiddealapigit.rest;

import com.kredimizik.rapiddealapigit.model.TrackingEventDTO;
import com.kredimizik.rapiddealapigit.service.TrackingEventService;
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
@RequestMapping(value = "/api/trackingEvents", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrackingEventResource {

    private final TrackingEventService trackingEventService;

    public TrackingEventResource(final TrackingEventService trackingEventService) {
        this.trackingEventService = trackingEventService;
    }

    @GetMapping
    public ResponseEntity<List<TrackingEventDTO>> getAllTrackingEvents() {
        return ResponseEntity.ok(trackingEventService.findAll());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<TrackingEventDTO> getTrackingEvent(
            @PathVariable(name = "eventId") final Long eventId) {
        return ResponseEntity.ok(trackingEventService.get(eventId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTrackingEvent(
            @RequestBody @Valid final TrackingEventDTO trackingEventDTO) {
        final Long createdEventId = trackingEventService.create(trackingEventDTO);
        return new ResponseEntity<>(createdEventId, HttpStatus.CREATED);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Long> updateTrackingEvent(
            @PathVariable(name = "eventId") final Long eventId,
            @RequestBody @Valid final TrackingEventDTO trackingEventDTO) {
        trackingEventService.update(eventId, trackingEventDTO);
        return ResponseEntity.ok(eventId);
    }

    @DeleteMapping("/{eventId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTrackingEvent(
            @PathVariable(name = "eventId") final Long eventId) {
        trackingEventService.delete(eventId);
        return ResponseEntity.noContent().build();
    }

}
