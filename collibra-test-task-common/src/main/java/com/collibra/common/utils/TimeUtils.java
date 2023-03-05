package com.collibra.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimeUtils {

    private TimeUtils() {

    }

    public static LocalDateTime getUTCLocalDateTimeNow() {
        return LocalDateTime.now(ZoneId.of("UTC"));
    }

    public static long getUTCMillisecondsFromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
