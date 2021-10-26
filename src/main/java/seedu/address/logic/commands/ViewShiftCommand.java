package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_DAY_SHIFT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DASH_TIME;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Period;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Shift;
import seedu.address.model.person.predicates.PersonIsWorkingPredicate;
import seedu.address.storage.RoleReqStorage;


/**
 * Class representing the view schedule command
 * which views the schedule by Person.
 */
public class ViewShiftCommand extends Command {

    public static final String COMMAND_WORD = "viewShift";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": find the staff working at the specified shift\n\n"
            + "Parameters:\n"
            + PREFIX_DASH_DAY_SHIFT + " day-slot_number\n"
            + PREFIX_DASH_TIME + " day-time" + " (time is in format HH:mm)\n\n"
            + "Examples:\n"
            + COMMAND_WORD + " " + PREFIX_DASH_DAY_SHIFT + " monday-0\n"
            + COMMAND_WORD + " " + PREFIX_DASH_DAY_SHIFT + " TUESDAY-1\n"
            + COMMAND_WORD + " " + PREFIX_DASH_TIME + " wednesday-11:00\n\n";

    public static final int INVALID_SLOT_NUMBER = -1;
    public static final int INVALID_SLOT_NUMBER_INDICATING_EMPTY_PREFIXES = -2;
    private static final String NO_STAFF_WORKING = "There is currently no staff working at the specified shift.";
    private static final String STAFF_LIST_EMPTY = "There are no staffs in the staff list, please add some first!";

    private final DayOfWeek dayOfWeek;
    private final int slotNum;
    private final LocalTime time;
    private final PersonIsWorkingPredicate isWorkingPredicate;
    private final Period periodToLookAt;

    private String defaultMessage = "Staff working on shift: ";
    private int[] finalRoleReqCheck = new int[]{};
    private String finalRoleReqMessage;

    /**
     * Constructs a ViewShiftCommand object.
     *
     * @param dayOfWeek The dayOfWeek that will be checked
     * @param slotNum The slot number that will be checked
     * @param time The time that will be checked
     */
    public ViewShiftCommand(DayOfWeek dayOfWeek, int slotNum, LocalTime time, Period period) {
        this.dayOfWeek = dayOfWeek;
        this.slotNum = slotNum;
        this.time = time;
        this.periodToLookAt = period;
        this.isWorkingPredicate = new PersonIsWorkingPredicate(dayOfWeek, slotNum, time, period);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> staffs = model.getUnFilteredPersonList();
        if (staffs.size() == 0) {
            throw new CommandException(STAFF_LIST_EMPTY);
        }
        model.updateFilteredPersonList(isWorkingPredicate);
        setRoleReqMessage(model);
        staffs = model.getFilteredPersonList();

        // Assumes either time is null, or dayOfWeek and slotNum is null (as passed in by ViewShiftCommandParser)
        if (time != null && dayOfWeek != null) {
            this.defaultMessage += String.format("%s-%s\n", dayOfWeek, time);
            return executeViewShiftByTime(staffs);
        } else if (slotNum != INVALID_SLOT_NUMBER && dayOfWeek != null) {
            this.defaultMessage += String.format("%s-%s\n", dayOfWeek, slotNum);
            return executeViewShiftBySlot(staffs);
        } else {
            throw new CommandException(HELP_MESSAGE);
        }
    }

    /**
     * Executes ViewShift by slot number, and finds the staff working at that day of the week and slot number
     *
     * @param staffs The staff list that will be checked
     * @return The staff working at that day of the week and slot number
     */
    public CommandResult executeViewShiftBySlot(ObservableList<Person> staffs) {
        StringBuilder result = new StringBuilder();
        int counter = 1;
        for (Person p : staffs) {
            boolean hasShift = p.isWorking(dayOfWeek, slotNum, periodToLookAt);
            if (hasShift) {
                result.append(counter).append(". ").append(p.getName()).append("\n");
                counter++;
            }
        }
        if (counter == 0) {
            return new CommandResult(NO_STAFF_WORKING);
        } else {
            return new CommandResult(defaultMessage + result.toString() + finalRoleReqMessage);
        }
    }

    /**
     * Executes ViewShift by slot number, and finds the staff working at that day of the week and time
     *
     * @param staffs The staff list that will be checked
     * @return The staff working at that day of the week and time
     */
    public CommandResult executeViewShiftByTime(ObservableList<Person> staffs) {
        String result = Shift.filterListByShift(staffs, dayOfWeek, time, periodToLookAt);
        if (result.equals("")) {
            return new CommandResult(NO_STAFF_WORKING);
        } else if (slotNum == INVALID_SLOT_NUMBER_INDICATING_EMPTY_PREFIXES) {
            return new CommandResult(HELP_MESSAGE + getWorkingStaffByTime(staffs));
        } else {
            return new CommandResult(defaultMessage + result + finalRoleReqMessage);
        }
    }

    public String getWorkingStaffByTime(ObservableList<Person> staffs) {
        StringBuilder result = new StringBuilder();
        int counter = 1;
        for (Person p : staffs) {
            boolean hasShift = p.isWorking(dayOfWeek, time, periodToLookAt);
            if (hasShift) {
                result.append(counter).append(". ").append(p.getName()).append("\n");
                counter++;
            }
        }
        return "Currently working:\n" + result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewShiftCommand
                && (this.dayOfWeek == null || this.dayOfWeek.equals(((ViewShiftCommand) other).dayOfWeek))
                && (this.slotNum != INVALID_SLOT_NUMBER || this.slotNum == ((ViewShiftCommand) other).slotNum)
                && (this.time == null || this.time.equals(((ViewShiftCommand) other).time)));
    }

    private boolean checkRoleReq(Model model) {
        int[] roleReqCheck = new int[]{0, 0, 0}; // bartender, floor, kitchen
        ObservableList<Person> staffWorking = model.getFilteredPersonList();

        for (Person p : staffWorking) {
            Set<Role> pRoles = p.getRoles();
            for (Role r : pRoles) {
                if (r.getValue().equals("bartender")) {
                    roleReqCheck[0] += 1;
                } else if (r.getValue().equals("floor")) {
                    roleReqCheck[1] += 1;
                } else if (r.getValue().equals("kitchen")) {
                    roleReqCheck[2] += 1;
                }
            }
        }

        finalRoleReqCheck = roleReqCheck;
        if (roleReqCheck[0] < RoleReqStorage.getMinNumBartender()) {
            return false;
        } else if (roleReqCheck[1] < RoleReqStorage.getMinNumFloor()) {
            return false;
        } else {
            return roleReqCheck[2] >= RoleReqStorage.getMinNumKitchen();
        }
    }

    private void setRoleReqMessage(Model model) {
        String roleReqMessage = checkRoleReq(model)
                ? ""
                : "\n\nThere is a manpower shortage! You are supposed to have:\n"
                + RoleReqStorage.getRoleReqs() + "\n\n"
                + "But you currently have:\n"
                + "Bartender: " + finalRoleReqCheck[0] + "\n"
                + "Floor: " + finalRoleReqCheck[1] + "\n"
                + "Kitchen: " + finalRoleReqCheck[2] + "\n";
        finalRoleReqMessage = roleReqMessage;
    }
}
