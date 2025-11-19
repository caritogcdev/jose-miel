package com.josemiel.nicho_nativo_romeral.web.controllers;

import com.josemiel.nicho_nativo_romeral.app.services.TourService;
// import com.josemiel.nicho_nativo_romeral.web.dto.response.TourResponse;
// import com.josemiel.nicho_nativo_romeral.web.dto.response.TourSessionResponse;
import com.josemiel.nicho_nativo_romeral.web.mappers.TourMapper;
import com.josemiel.nicho_nativo_romeral.web.mappers.TourSessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class ToursController {
    private final TourService service;
    private final TourMapper tourMapper;
    private final TourSessionMapper sessionMapper;

    @GetMapping
    public ResponseEntity<?> listTours() {
        var list = service.listTours().stream().map(tourMapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{tourId}/sessions")
    public ResponseEntity<?> listSessions(
            @PathVariable Integer tourId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        var list = service.listSessions(tourId, from, to).stream()
                .map(sessionMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
