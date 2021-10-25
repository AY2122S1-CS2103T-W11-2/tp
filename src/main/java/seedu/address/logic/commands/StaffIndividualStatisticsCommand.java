package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_TAG;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Period;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.PersonContainsFieldsPredicate;

/**
 * A class representing the command to obtain the working
 * hours and overall salary for each staff.
 */
public class StaffIndividualStatisticsCommand extends Command {

    public static final String COMMAND_WORD = "istaff";
    public static final String DEFAULT_EXECUTION = "Staff to show:\n%1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":"
            + "Command to obtain salary statistics of staff\n"
            + "Used by looking up the staff to display by field.\n"
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
    private static final String INDIVIDUAL_STAFF_PRINT = "Stats for %1$s:\n"
            + "Total work hours: %2$s\n"
            + "Total salary: %3$s";


    private final PersonContainsFieldsPredicate predicate;
    private final int index;

    /**
     * Constructor for the lookup command to display staff statistics.
     * Staff statistics displayed are total work hours for the month.
     *
     * @param predicate The {@code PersonContainsFieldsPredicate} that decides
     *                  which staff to display the statistics of.
     */
    public StaffIndividualStatisticsCommand(PersonContainsFieldsPredicate predicate) {
        this.predicate = predicate;
        this.index = -1;
    }

    /**
     * Constructor for the lookup command to display staff statistics by index.
     *
     * @param predicate The predicate to test the staff with.
     * @param index The index of the staff to get.
     */
    public StaffIndividualStatisticsCommand(PersonContainsFieldsPredicate predicate, Index index) {
        this.predicate = predicate;
        this.index = index.getZeroBased();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (index != -1) {
            return executeIndex(model);
        }
        List<Person> staffs = model.getUnFilteredPersonList().filtered(predicate);
        return new CommandResult(String.format(DEFAULT_EXECUTION, result(staffs)));

    }


    private CommandResult executeIndex(Model model) throws CommandException {
        if (index >= model.getFilteredPersonList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person staff = model.getFilteredPersonListByIndex(index);
        return new CommandResult(staffSummary(staff));
    }


    private String result(List<Person> staffs) {
        StringBuilder sb = new StringBuilder();
        for (Person staff : staffs) {
            sb.append(staffSummary(staff));
            sb.append("\n");
        }
        return sb.toString().trim();

    }

    private String staffSummary(Person staff) {
        Period period = Period.getPeriodFromDateOverMonth(LocalDate.now());
        long workHours = staff
                .getTotalWorkingHour(period);
        double totalSalary = staff.getSalaryToBePaid(period);
        return String.format(INDIVIDUAL_STAFF_PRINT, staff.getName(), workHours, totalSalary);
    }

}
