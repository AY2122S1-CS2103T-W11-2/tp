package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_SHIFT;


import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Period;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsFieldsPredicate;

/**
 * Class representing the command to remove a mark.
 */
public class RemoveMarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Used to remove the marking of an absentee.\n\n"
            + "Parameters:\n"
            + PREFIX_DASH_INDEX + "INDEX or "
            + PREFIX_DASH_NAME + "NAME "
            + PREFIX_DAY_SHIFT + "DATE "
            + "[" + PREFIX_DAY_SHIFT + "END DATE]\n\n"
            + "Example:\n"
            + COMMAND_WORD + " " + PREFIX_DASH_INDEX + "1"
            + " " + PREFIX_DAY_SHIFT + "2021-11-18\n"
            + COMMAND_WORD + " " + PREFIX_DASH_NAME + "Jace "
            + PREFIX_DAY_SHIFT + "2021-11-11" + " " + PREFIX_DAY_SHIFT + "2021-11-13";

    public static final String NO_STAFF_SATISFIES_QUERY = "No one satisfies the conditions specified";
    public static final String STAFF_UNMARKED = "Staff unmarked:\n%1$s";

    private final PersonContainsFieldsPredicate predicate;
    private final int index;
    private final Period period;

    /**
     * Constructs an {@code RemoveMarkCommand} to indicate that a person who satisfies
     * the {@code PersonContainsFieldsPredicate} has been marked as working
     * in {@code period}.
     */
    public RemoveMarkCommand(PersonContainsFieldsPredicate predicate, Period period) {
        index = -1;
        this.predicate = predicate;
        this.period = period;
    }

    /**
     * Constructs an {@code RemoveMarkCommand} to indicate that a person who satisfies the
     * {@code PersonContainsFieldsPredicate} has been marked as working in
     * {@code period}.
     */
    public RemoveMarkCommand(PersonContainsFieldsPredicate predicate, Index index, Period period) {
        this.index = index.getZeroBased();
        this.predicate = predicate;
        this.period = period;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (index != -1) {
            return executeIndex(model);
        }
        FilteredList<Person> toEdit = model.getUnFilteredPersonList()
                .filtered(this.predicate);
        if (toEdit.size() == 0) {
            throw new CommandException(NO_STAFF_SATISFIES_QUERY);
        }
        for (Person p : toEdit) {
            model.setPerson(p, checkPerson(p));
        }
        List<String> toPrint = toEdit.stream()
                .map(Person::getName)
                .map(Object::toString)
                .collect(Collectors.toList());
        return new CommandResult(String.format(STAFF_UNMARKED, listToString(toPrint)));
    }


    private CommandResult executeIndex(Model model) throws CommandException {
        requireNonNull(model);
        if (index > model.getFilteredPersonList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person toTest = model.getFilteredPersonList().get(index);
        model.setPerson(toTest, checkPerson(toTest));
        return new CommandResult(String.format(STAFF_UNMARKED, toTest.getName()));
    }

    /**
     * Converts a {@code List<String> strings} of strings to the following format.
     * e.g. ["Rudy", "Roxy", "Paul"] would output
     * Rudy
     * Roxy
     * Paul
     * Note that there is no new line at the end.
     */
    public static String listToString(List<String> strings) {
        StringBuilder result = new StringBuilder();
        for (String string : strings) {
            result.append(string);
            result.append("\n");
        }
        return result.toString().trim();
    }

    /**
     * Checks if the staff satisfies the {@code predicate} and
     * has {@code period} to be removed from the unmark command.
     *
     * @throws CommandException When the staff does not satisfy the conditions.
     */
    private Person checkPerson(Person toTest) throws CommandException {
        requireNonNull(toTest);
        //ensures that the staff to unmark satisfies the predicate
        if (!this.predicate.test(toTest)) {
            throw new CommandException(NO_STAFF_SATISFIES_QUERY);
        }
        Person result = toTest.unMark(period);
        //when nothing has changed
        if (result.equals(toTest)) {
            throw new CommandException(NO_STAFF_SATISFIES_QUERY);
        }
        return result;

    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null)
                && (obj instanceof RemoveMarkCommand)
                && ((RemoveMarkCommand) obj).index == index
                && ((RemoveMarkCommand) obj).predicate.equals(predicate)
                && ((RemoveMarkCommand) obj).period.equals(period);
    }

}
