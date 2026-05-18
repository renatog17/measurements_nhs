package com.nhst.medicoes.clock;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/clock")
@RequiredArgsConstructor
public class ClockController {

    private final MutableClock mutableClock;

    @PostMapping("/advance")
    public LocalDateTime advanceHours(@RequestParam long hours) {

        mutableClock.setInstant(
                mutableClock.instant().plusSeconds(hours * 3600)
        );

        return now();
    }

    @PostMapping("/set")
    public LocalDateTime setDateTime(
            @RequestParam LocalDateTime dateTime
    ) {

        mutableClock.setInstant(
                dateTime.atZone(
                        mutableClock.getZone()
                ).toInstant()
        );

        return now();
    }

    @GetMapping
    public LocalDateTime now() {
        return LocalDateTime.ofInstant(
                mutableClock.instant(),
                mutableClock.getZone()
        );
    }
}