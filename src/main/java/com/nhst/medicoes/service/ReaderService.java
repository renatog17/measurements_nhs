package com.nhst.medicoes.service;

import com.nhst.medicoes.domain.Reader;
import com.nhst.medicoes.domain.dto.CreateReaderRequest;
import com.nhst.medicoes.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    @Transactional
    public Reader create(CreateReaderRequest request) {

        if (readerRepository.existsByEmployeeCode(request.employeeCode())) {
            throw new RuntimeException("Employee code already exists");
        }

        Reader reader = Reader.builder()
                .name(request.name())
                .employeeCode(request.employeeCode())
                .build();

        Reader saved = readerRepository.save(reader);

        return saved;
    }

    public Reader findById(Long readerId) {
        return readerRepository.findById(readerId).orElseThrow();
    }
}