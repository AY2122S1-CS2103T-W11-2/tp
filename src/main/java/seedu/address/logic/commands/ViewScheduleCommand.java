package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_TAG;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.PersonContainsFieldsPredicate;

/**
 * Class representing the view schedule command
 * which views the schedule by Person.
 */
public class ViewScheduleCommand extends Command {

    public static final String DEFAULT_MESSAGE = "Schedule viewed of staff: %1$s\n";
    public static final String COMMAND_WORD = "viewSchedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the schedules of the staff that have the"
            + "input parameters.\n\n"
            + "Parameters:\n"
            + "[" + PREFIX_DASH_NAME + "NAME] "
            + "[" + PREFIX_DASH_INDEX + "INDEX] "
            + "[" + PREFIX_DASH_PHONE + "PHONE] "
            + "[" + PREFIX_DASH_EMAIL + "EMAIL] "
            + "[" + PREFIX_DASH_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DASH_SALARY + "SALARY] "
            + "[" + PREFIX_DASH_STATUS + "STATUS] "
            + "[" + PREFIX_DASH_ROLE + "ROLE]... "
            + "[" + PREFIX_DASH_TAG + "TAG]...\n\n"
            + "Example:\n" + COMMAND_WORD + " "
            + PREFIX_DASH_PHONE + "91234567 "
            + PREFIX_DASH_EMAIL + "johndoe@example.com";
    private static final String NAME_NOT_IN_LIST_ERROR = "Name used not in dataset.";

    private final PersonContainsFieldsPredicate predicate;
    private final int index;

    public ViewScheduleCommand(PersonContainsFieldsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
        this.index = -1;
    }

    public ViewScheduleCommand(PersonContainsFieldsPredicate predicate, Index index) {
        requireAllNonNull(predicate, index);
        this.index = index.getZeroBased();
        this.predicate = predicate;

    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (index != -1) {

        }


        ObservableList<Person> staffs = model.getUnFilteredPersonList()
                .filtered(this.predicate);
        if (staffs.size() == 0) {
            throw new CommandException(NAME_NOT_IN_LIST_ERROR);
        }

        String result = getResult(staffs);
        return new CommandResult(result);
    }

    private String getResult(List<Person> staffs) {
        String result = "";
        for (Person staff : staffs) {
            result += String.format(DEFAULT_MESSAGE, staff.getName());
            result += staff.getSchedule().toViewScheduleString();
        }
        return result;
    }


    private CommandResult executeIndex(Model model) throws CommandException {
        assert index != -1;
        if (index >= model.getFilteredPersonList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person result = model.getFilteredPersonList().get(index);

        return new CommandResult(getResult(List.of(result)));

    }

}
