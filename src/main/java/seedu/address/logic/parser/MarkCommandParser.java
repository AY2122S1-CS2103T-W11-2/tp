package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_SHIFT;
import static seedu.address.logic.parser.ParserUtil.parsePeriod;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Period;
import seedu.address.model.person.PersonContainsFieldsPredicate;


public class MarkCommandParser implements Parser<MarkCommand> {

    private static final ParseException NO_FIELD_EXCEPTION = new ParseException(MarkCommand.MESSAGE_USAGE);

    @Override
    public MarkCommand parse(String userInput) throws ParseException {
        //created to test if there are any identifiers
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DASH_NAME, PREFIX_DASH_PHONE,
                        PREFIX_DASH_INDEX, PREFIX_DAY_SHIFT,
                        PREFIX_DASH_EMAIL, PREFIX_DASH_ADDRESS, PREFIX_DASH_TAG,
                        PREFIX_DASH_STATUS, PREFIX_DASH_ROLE, PREFIX_DASH_SALARY);
        //created to test if there are
        List<String> periods = argMultimap.getAllValues(PREFIX_DAY_SHIFT);
        if ((periods.size() != 1 && periods.size() != 2)) {
            throw NO_FIELD_EXCEPTION;
        }
        Period period = parsePeriod(periods);

        PersonContainsFieldsPredicate predicate = ParserUtil.testByAllFields(argMultimap);
        //checks for index
        if (argMultimap.getValue(PREFIX_DASH_INDEX).isPresent()) {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DASH_INDEX).get());
            return new MarkCommand(index, period, predicate);
        }
        //checks for empty
        if (predicate.isEmpty()) {
            throw NO_FIELD_EXCEPTION;
        }

        return new MarkCommand(predicate, period);

    }



}
