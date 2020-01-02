package com.tellme.backend.exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String date) {
        super("Date is not in ISO_ZONED_DATE_TIME format: " + date + ". The date must be formatted like this: '2011-12-03T10:15:30+01:00[Europe/Paris]'");
    }
}
