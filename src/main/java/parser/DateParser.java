package parser;

import duke.DukeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

/**
 * A date parser class which takes in input from deadline and event commands which requires dates and time input.
 * It will take these input and parse in date format.
 */
//Parse date formatter logic adapted from https://stackoverflow.com/a/55021417
//Parsing multiple date formatter in a loop adapted from https://stackoverflow.com/a/4024604
//Comparing two dates adapted from https://stackoverflow.com/a/58281016 and https://stackoverflow.com/a/27006098
public class DateParser {
    /**
     * A list of acceptable date time formats to be considered valid
     */
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
    /**
     * A list of acceptable date formats to be considered valid
     */
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

    /**
     * Tries to parse the input as date time format, if input format is invalid,
     * Try again by parsing as only date. If input is only date, 23:59 is assumed to be the time of the deadline
     * If input fails again, throw a DukeException.
     * All input in an acceptable valid format will be reformatted as ("MMM d yyyy hh:mma") to be outputted
     *
     * @param dateInput contains the input datetime or only date to be reformatted
     * @return the reformatted input in ("MMM d yyyy hh:mma") format
     * @throws DukeException input is not in an acceptable valid format and cannot be parsed
     */
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

    /**
     * Tries to parse the date input in a LocalDateTime through the list of predefined valid date time formats.
     * Loop through the list, ignore parse exceptions to unmatched format until it matches a valid format in the list.
     * Once a valid format matches, return the parsed input.
     *
     * @param dateInput contains the input datetime
     * @return the parsed input
     * @throws DukeException input is not in an acceptable valid datetime format and cannot be parsed
     */
    public static LocalDateTime parseDateTime(String dateInput) throws DukeException {
        for (DateTimeFormatter formatter : DATE_TIME_FORMATS) {
            try {
                return LocalDateTime.parse(dateInput, formatter);
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new DukeException("Invalid date-time format. Please input a valid format. e.g. dd-MM-yyyy HH:mm");
    }

    /**
     * Tries to parse the date input in a LocalDate through the list of predefined valid date time formats.
     * Loop through the list, ignore parse exceptions to unmatched format until it matches a valid format in the list.
     * Once a valid format matches, return the parsed input.
     *
     * @param dateInput contains the input date
     * @return the parsed input
     * @throws DukeException input is not in an acceptable valid date format and cannot be parsed
     */
    public static LocalDate parseDate(String dateInput) throws DukeException {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateInput, formatter);
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new DukeException("Invalid date format. Please input a valid format. e.g. dd-MM-yyyy");
    }
    /**
     * Check if the start date time is before end date time. This is to be used for
     * event tasks where a start date time (from) and an end date time (to) is required.
     * @param from the start date time of the event task
     * @param to the end date time of the event task
     * @return true if the start date time is before end date time, else return false
     */
    public static boolean isBefore(String from, String to) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy hh:mma");

        LocalDateTime startDateTime = LocalDateTime.parse( from, format) ;
        LocalDateTime endDateTime = LocalDateTime.parse( to, format) ;

        return startDateTime.isBefore(endDateTime);
    }

    /**
     * Check if a task date is upcoming in the next 3 days to remind the user
     * @param date The date that is upcoming such as upcoming due date for deadline tasks
     *             and start date for event tasks.
     * @return true if the date is in the next 3 days, false if not.
     */
    public static boolean isUpcoming(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy hh:mma");

        LocalDateTime formatDate = LocalDateTime.parse(date, format) ;
        long daysBetween = ChronoUnit.DAYS.between(LocalDateTime.now(), formatDate);
        if (daysBetween > 0 && daysBetween <= 3) {
            return true;
        }
        return false;
    }

}