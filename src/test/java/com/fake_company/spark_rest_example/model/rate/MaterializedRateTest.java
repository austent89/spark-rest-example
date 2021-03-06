package com.fake_company.spark_rest_example.model.rate;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MaterializedRateTest {


    @Test
    public void test_within_bounds_success() {
        var materializedRate = new MaterializedRate(
                LocalTime.parse("0600", DateTimeFormatter.ofPattern("HHMM")),
                LocalTime.parse("1100", DateTimeFormatter.ofPattern("HHMM")),
                Lists.newArrayList(DayOfWeek.FRIDAY), 1000);
        final var start = ZonedDateTime.parse("2016-01-01T07:00:00Z");
        final var end = ZonedDateTime.parse("2016-01-01T10:00:00Z");
        assertTrue(materializedRate.isWithinRate(start, end));
    }

    @Test
    public void test_within_bounds_failure_wrong_day() {
        var materializedRate = new MaterializedRate(
                LocalTime.parse("0600", DateTimeFormatter.ofPattern("HHMM")),
                LocalTime.parse("1100", DateTimeFormatter.ofPattern("HHMM")),
                Lists.newArrayList(DayOfWeek.FRIDAY), 1000);
        final var start = ZonedDateTime.parse("2016-02-01T07:00:00Z");
        final var end = ZonedDateTime.parse("2016-02-01T10:00:00Z");
        assertFalse(materializedRate.isWithinRate(start, end));
    }

    @Test
    public void test_within_bounds_failure_starts_before_rate_does() {
        var materializedRate = new MaterializedRate(
                LocalTime.parse("0600", DateTimeFormatter.ofPattern("HHMM")),
                LocalTime.parse("1100", DateTimeFormatter.ofPattern("HHMM")),
                Lists.newArrayList(DayOfWeek.FRIDAY), 1000);
        final var start = ZonedDateTime.parse("2016-01-01T05:00:00Z");
        final var end = ZonedDateTime.parse("2016-01-01T10:00:00Z");
        assertFalse(materializedRate.isWithinRate(start, end));
    }

    @Test
    public void test_within_bounds_failure_starts_after() {
        var materializedRate = new MaterializedRate(
                LocalTime.parse("0600", DateTimeFormatter.ofPattern("HHMM")),
                LocalTime.parse("1100", DateTimeFormatter.ofPattern("HHMM")),
                Lists.newArrayList(DayOfWeek.FRIDAY), 1000);
        final var start = ZonedDateTime.parse("2016-01-01T12:00:00Z");
        final var end = ZonedDateTime.parse("2016-01-01T13:00:00Z");
        assertFalse(materializedRate.isWithinRate(start, end));
    }

}
