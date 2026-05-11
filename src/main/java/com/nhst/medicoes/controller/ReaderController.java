package com.nhst.medicoes.controller;


import com.nhst.medicoes.controller.dto.reader.ReaderFilter;
import com.nhst.medicoes.controller.dto.reader.ReaderResponse;
import com.nhst.medicoes.domain.Reader;
import com.nhst.medicoes.controller.dto.reader.CreateReaderRequest;
import com.nhst.medicoes.service.ReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public Page<ReaderResponse> findAll(
            ReaderFilter filter,
            @PageableDefault(size = 10, sort = "name")
            Pageable pageable
    ){
        return readerService.findAll(filter, pageable);
    }
}
