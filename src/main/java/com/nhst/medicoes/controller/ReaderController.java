package com.nhst.medicoes.controller;


import com.nhst.medicoes.domain.Reader;
import com.nhst.medicoes.domain.dto.CreateReaderRequest;
import com.nhst.medicoes.service.ReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateReaderRequest request) {

        Reader reader = readerService.create(request);

        return ResponseEntity
                .created(URI.create("/readers/" + reader.getId()))
                .body(reader);
    }
}
