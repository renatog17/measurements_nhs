package com.nhst.medicoes.clock;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class AppTime {

    private final MutableClock mutableClock;

    public AppTime(MutableClock mutableClock) {
        this.mutableClock = mutableClock;
    }

    public LocalDateTime nowDateTime() {
        return LocalDateTime.now(mutableClock);
    }

    public LocalDate nowDate() {
        return LocalDate.now(mutableClock);
    }
}
