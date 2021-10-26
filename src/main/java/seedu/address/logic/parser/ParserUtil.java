package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.TimeUtil.TIME_FORMATTER;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.person.Shift.isValidDayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Period;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Status;
import seedu.address.model.person.predicates.PersonContainsFieldsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>}.
     */
    public static Set<Role> parseRoles(Collection<String> roles) throws ParseException {
        requireNonNull(roles);
        final Set<Role> roleSet = new HashSet<>();
        for (String roleName : roles) {
            roleSet.add(parseRole(roleName));
        }
        return roleSet;
    }

    /**
     * Parses a {@code String salary} into a {@code Salary}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code salary} is invalid.
     */
    public static Salary parseSalary(String salary) throws ParseException {
        requireNonNull(salary);
        String trimmedSalary = salary.trim();
        if (!Salary.isValidSalary(trimmedSalary)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Salary(trimmedSalary);
    }

    /**
     * Parses a {@code String status} into an {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatuses(List<String> status) throws ParseException {
        int length = status.size();
        if (length == 0) {
            return Status.NO_STATUS;
        }
        String statusLast = status.get(status.size() - 1);
        String trimmedStatus = statusLast.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return Status.translateStringToStatus(trimmedStatus);
    }

    /**
     * Parses a {@code String dayOfWeek} into an {@code DayOfWeek}.
     * Leading and trailing whitespaces will be trimmed.
     * This parser is not case sensitive.
     *
     * @throws ParseException if the given {@code dayOfWeek} is invalid.
     */
    public static String parseDayOfWeekAndSlot(String shiftDay) throws ParseException {
        String messageConstraints = "Valid input format: dayOfWeek-slotNumber:" + "List of valid dayOfWeek: "
                + "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday. (Not case-sensitive)\n"
                + "List of valid slotNumber: 1, 2.";
        requireNonNull(shiftDay);
        String trimmedStr = shiftDay.trim().toLowerCase();
        String[] strings = trimmedStr.split("-");
        if (strings.length != 2) {
            throw new ParseException(messageConstraints);
        }
        switch (strings[0]) {
        case "monday":
        case "tuesday":
        case "wednesday":
        case "thursday":
        case "friday":
        case "saturday":
        case "sunday":
            break;
        default: throw new ParseException(messageConstraints);
        }
        switch (strings[1]) {
        case "0":
        case "1":
            break;
        default: throw new ParseException(messageConstraints);
        }
        return trimmedStr;
    }

    /**
     * Parses a dayOfWeek-time.
     * Leading and trailing whitespaces will be trimmed.
     * This parser is not case sensitive.
     *
     * @throws ParseException if the given {@code dayOfWeek} is invalid.
     */
    public static String parseDayOfWeekAndTime(String shiftDay) throws ParseException {
        String messageConstraints = "Valid input format: dayOfWeek-time:" + "List of valid dayOfWeek: "
                + "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday. (Not case-sensitive)\n"
                + "valid time formats: HH:mm in 24-hour format, such as 13:00";
        requireNonNull(shiftDay);
        String trimmedStr = shiftDay.trim().toLowerCase();
        String[] strings = trimmedStr.split("-");
        if (strings.length != 2) {
            throw new ParseException(messageConstraints);
        }
        if (!isValidDayOfWeek(strings[0])) {
            throw new ParseException(messageConstraints);
        }

        try {
            LocalTime.parse(strings[1], DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new ParseException(messageConstraints);
        }

        return trimmedStr;
    }


    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        try {
            return Status.translateStringToStatus(trimmedStatus);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }

    }

    /**
     * Parses a {@code String role} into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        try {
            return Role.translateStringToRole(trimmedRole);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code Collection<String> periods} to a {@code period} from the earliest date to the
     * latest date in the collection.
     * @throws ParseException When the input does not have the correct format.
     */
    public static Period parsePeriod(Collection<String> periods) throws ParseException {
        LocalDate start = LocalDate.MAX;
        LocalDate end = LocalDate.MIN;
        try {
            for (String periodName : periods) {
                if (start.isAfter(LocalDate.parse(periodName))) {
                    start = LocalDate.parse(periodName);
                }
                if (end.isBefore(LocalDate.parse(periodName))) {
                    end = LocalDate.parse(periodName);
                }
            }
        } catch (DateTimeParseException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_DATE_PARSED);
        }

        return new Period(start, end);
    }

    /**
     * Parsers {@code String shiftTimes} to a {@code LocalTime[]} which contains the a start time and end time.
     * @param shiftTimes The input string.
     * @return A LocalTime array containing start time and end time of the shift.
     * @throws ParseException throws when the input does not have the correct format.
     */
    public static LocalTime[] parseShiftTime(String shiftTimes) throws ParseException {
        LocalTime startTime;
        LocalTime endTime;
        String[] separatedShiftTimes = shiftTimes.split("-");
        if (separatedShiftTimes.length != 2) {
            throw new ParseException(Messages.MESSAGE_INVALID_SHIFT_TIME);
        }
        try {
            startTime = LocalTime.parse(separatedShiftTimes[0], TIME_FORMATTER);
            endTime = LocalTime.parse(separatedShiftTimes[1], TIME_FORMATTER);
        } catch (DateTimeParseException ite) {
            throw new ParseException(Messages.MESSAGE_INVALID_TIME);
        }
        return new LocalTime[]{startTime, endTime};
    }



    /**
     * Parses {@code args} into {@code PersonContainsFieldsPredicate} which tests a person for all
     * of the qualifiers of the predicate.
     * @throws ParseException Throws parse exception when the input is not something needed.
     */
    public static PersonContainsFieldsPredicate testByAllFields(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);
        PersonContainsFieldsPredicate predicate = new PersonContainsFieldsPredicate();
        predicate.addFieldToTest(argMultimap.getValue(PREFIX_DASH_NAME), ParserUtil::parseName);
        predicate.addFieldToTest(argMultimap.getValue(PREFIX_DASH_PHONE), ParserUtil::parsePhone);
        predicate.addFieldToTest(argMultimap.getValue(PREFIX_DASH_EMAIL), ParserUtil::parseEmail);
        predicate.addFieldToTest(argMultimap.getValue(PREFIX_DASH_ADDRESS), ParserUtil::parseAddress);
        predicate.addFieldToTest(argMultimap.getValue(PREFIX_DASH_TAG), ParserUtil::parseTag);
        try {
            predicate.addFieldToTest(argMultimap.getValue(PREFIX_DASH_ROLE), Role::translateStringToRole);
            predicate.addFieldToTest(argMultimap.getValue(PREFIX_DASH_SALARY), Salary::new);
            predicate.addFieldToTest(argMultimap.getValue(PREFIX_DASH_STATUS), Status::translateStringToStatus);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(iae.getMessage());
        }
        return predicate;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    public static Set<String> parseRoleRequirements(Collection<String> roles) throws ParseException {
        requireNonNull(roles);
        final Set<String> roleSet = new HashSet<>();
        for (String roleReq : roles) {
            roleReq = roleReq.trim().replace(PREFIX_ROLE.toString(), "");
            if (!isValidRoleRequirement(roleReq)) {
                throw SetRoleReqCommandParser.DEFAULT_ERROR;
            }
            roleSet.add(roleReq);
        }
        return roleSet;
    }

    private static boolean isValidRoleRequirement(String roleReq) {
        String[] roleReqSplit = roleReq.split("-");

        if (roleReqSplit.length != 2) {
            return false;
        }

        if (!roleReqSplit[0].equals("bartender") && !roleReqSplit[0].equals("floor")
                && !roleReqSplit[0].equals("kitchen")) {
            return false;
        }

        try {
            Integer.parseInt(roleReqSplit[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
