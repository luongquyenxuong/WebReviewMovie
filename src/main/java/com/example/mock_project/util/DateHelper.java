package com.example.mock_project.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateHelper {

    public static String format(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return formatter.format(date);
    }

    public static Date convert(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return formatter.parse(date);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static String currentDateTimeString() {
        LocalDate date = LocalDate.now();  // Create a date object
        LocalTime time = LocalTime.now();  // Create a date object
        return date.toString() + " " + time.toString();

    }

    public static Date currentDateTime() {

        String dateTimeString =currentDateTimeString();

        return convert(dateTimeString);

    }
}
