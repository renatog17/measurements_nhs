package com.nhst.medicoes.clock;

import java.time.*;

public class MutableClock extends Clock {

    private Instant currentInstant;
    private final ZoneId zone;

    public MutableClock(Instant initialInstant, ZoneId zone) {
        this.currentInstant = initialInstant;
        this.zone = zone;
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new MutableClock(currentInstant, zone);
    }

    @Override
    public Instant instant() {
        return currentInstant;
    }

    public void setInstant(Instant instant) {
        this.currentInstant = instant;
    }

    public void advance(Duration duration) {
        this.currentInstant = this.currentInstant.plus(duration);
    }

    public void back(Duration duration) {
        this.currentInstant = this.currentInstant.minus(duration);
    }
}