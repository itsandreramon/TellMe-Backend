/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DateUtils {
  public static ZonedDateTime convertStringToDate(String timestamp) {
    try {
      var date =
          ZonedDateTime.parse(timestamp)
              .truncatedTo(ChronoUnit.MILLIS)
              .format(DateTimeFormatter.ISO_ZONED_DATE_TIME);

      return ZonedDateTime.parse(date);
    } catch (DateTimeParseException e) {
      return null;
    }
  }
}
