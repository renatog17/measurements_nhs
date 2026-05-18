package com.nhst.medicoes.clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class ClockConfig {

    @Bean
    public MutableClock mutableClock() {
        return new MutableClock(
                Instant.now(),
                ZoneId.of("America/Sao_Paulo")
        );
    }
}