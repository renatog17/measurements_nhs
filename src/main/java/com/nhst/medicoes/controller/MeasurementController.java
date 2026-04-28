//package com.nhst.medicoes.controller;
//
//import com.nhst.medicoes.domain.dto.MeasurementInputDTO;
//import com.nhst.medicoes.service.MeasurementService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/measurements")
//@RequiredArgsConstructor
//public class MeasurementController {
//
//    private final MeasurementService measurementService;
//
//    @PostMapping("/batch")
//    public ResponseEntity<Void> receiveBatch(
//            @RequestBody List<MeasurementInputDTO> inputs
//    ) {
//        measurementService.processBatch(inputs);
//        return ResponseEntity.ok().build();
//    }
//}