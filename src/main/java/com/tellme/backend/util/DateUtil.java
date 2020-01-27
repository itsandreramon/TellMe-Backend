package com.tellme.backend.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static ZonedDateTime convertStringToDate(String timestamp) {
        try {
            String date = ZonedDateTime.parse(timestamp)
                            .truncatedTo(ChronoUnit.MILLIS)
                            .format(DateTimeFormatter.ISO_ZONED_DATE_TIME);

            return ZonedDateTime.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
