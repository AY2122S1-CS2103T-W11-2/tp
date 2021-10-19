package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_DAY_SHIFT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_TIME;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;
import static seedu.address.logic.parser.ParserUtil.parseDayOfWeekAndSlot;
import static seedu.address.logic.parser.ParserUtil.parseDayOfWeekAndTime;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.viewShiftCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Class representing the find schedule command parser.
 */
public class viewShiftCommandParser implements Parser<viewShiftCommand> {

    public static final String INVALID_FIND_SCHEDULE_COMMAND =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, seedu.address.logic.commands.viewShiftCommand.HELP_MESSAGE);
    public static final ParseException INVALID_FIND_SCHEDULE_COMMAND_EXCEPTION =
            new ParseException(INVALID_FIND_SCHEDULE_COMMAND);

    @Override
    public viewShiftCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DASH_DAY_SHIFT, PREFIX_DASH_TIME);
        checkPrefixes(argMultimap); // throws an error if prefixes are inputted wrong

        int slotNum = seedu.address.logic.commands.viewShiftCommand.INVALID_SLOT_NUMBER;
        DayOfWeek dayOfWeek = null; // should not be null when viewShiftCommand object is created
        LocalTime time = null;

        try {
            // remove the prefix, then parse
            if (argMultimap.getValue(PREFIX_DASH_TIME).isPresent()) {
                String trimmedArgs = args.replace(PREFIX_DASH_TIME.toString(), "").trim();
                String parsedArg = parseDayOfWeekAndTime(trimmedArgs);
                String[] parsedArgArray = parsedArg.split("-");
                dayOfWeek = DayOfWeek.valueOf(parsedArgArray[0].toUpperCase());
                time = LocalTime.parse(parsedArgArray[1], DateTimeFormatter.ofPattern("HH:mm"));
                // slotNum will remain null
            }

            if (argMultimap.getValue(PREFIX_DASH_DAY_SHIFT).isPresent()) {
                String trimmedArgs = args.replace(PREFIX_DASH_DAY_SHIFT.toString(), "").trim();
                String parsedArg = parseDayOfWeekAndSlot(trimmedArgs); // returns [day]-[slot]
                String[] parsedArgArray = parsedArg.split("-");
                dayOfWeek = DayOfWeek.valueOf(parsedArgArray[0].toUpperCase());
                slotNum = Integer.parseInt(parsedArgArray[1]);
                // time remains as INVALID_SLOT_NUMBER
            }

        } catch (ParseException pe) {
            throw INVALID_FIND_SCHEDULE_COMMAND_EXCEPTION;
        }

        return new viewShiftCommand(dayOfWeek, slotNum, time);
    }

    private void checkPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        // Exactly one of PREFIX_DASH_DAY_SHIFT or PREFIX_DASH_TIME must exist
        if (!arePrefixesPresent(argMultimap, PREFIX_DASH_DAY_SHIFT)
                && !arePrefixesPresent(argMultimap, PREFIX_DASH_TIME)) {
            throw INVALID_FIND_SCHEDULE_COMMAND_EXCEPTION;
        }
        if (arePrefixesPresent(argMultimap, PREFIX_DASH_TIME)
                && arePrefixesPresent(argMultimap, PREFIX_DASH_DAY_SHIFT)) {
            throw INVALID_FIND_SCHEDULE_COMMAND_EXCEPTION;
        }
    }
}
