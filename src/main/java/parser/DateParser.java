package parser;

import duke.DukeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateParser {
    private static final List<DateTimeFormatter> DATE_TIME_FORMATS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-M-d HHmm"),
            DateTimeFormatter.ofPattern("yyyy-M-d HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/M/d HHmm"),
            DateTimeFormatter.ofPattern("yyyy/M/d HH:mm"),
            DateTimeFormatter.ofPattern("d-M-yyyy HHmm"),
            DateTimeFormatter.ofPattern("d-M-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")
    );

    private static final List<DateTimeFormatter> DATE_FORMATS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("yyyy-M-d"),
            DateTimeFormatter.ofPattern("yyyy/M/d"),
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy")
    );

    public static String formatDateInput(String dateInput) throws DukeException {
        try {
            LocalDateTime dateTime = parseDateTime(dateInput);
            return dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mma"));
        } catch (DukeException tryOnlyDate) {
            //If dateTime is invalid then try parsing it as only date
            try {
                LocalDate date = parseDate(dateInput);
                //if no time is given, assume deadline time to be 23:59
                return date.atTime(23, 59).format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mma"));
            } catch (DukeException e) {
                throw new DukeException("Invalid date/time input format. Please use a valid format, e.g., dd-MM-yyyy HH:mm or dd-MM-yyyy");
            }
        }
    }

    public static LocalDate parseDate(String dateInput) throws DukeException {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateInput, formatter);
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new DukeException("Invalid date format. Please input a valid format. e.g. dd-MM-yyyy");
    }

    public static LocalDateTime parseDateTime(String dateInput) throws DukeException {
        for (DateTimeFormatter formatter : DATE_TIME_FORMATS) {
            try {
                return LocalDateTime.parse(dateInput, formatter);
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new DukeException("Invalid date-time format. Please input a valid format. e.g. dd-MM-yyyy HH:mm");
    }
}
