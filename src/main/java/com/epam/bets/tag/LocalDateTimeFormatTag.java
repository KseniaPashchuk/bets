package com.epam.bets.tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class provides custom function for using on jsp pages, that format {@link LocalDateTime} object.
 *
 * @author Pashchuk Ksenia
 */

public class LocalDateTimeFormatTag {

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
