package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Slot;
import seedu.address.model.person.Status;
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
     * Parses a {@code String role} into an {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRoles(List<String> role) throws ParseException {
        int length = role.size();
        if (length == 0) {
            return Role.NO_ROLE;
        }
        String roleLast = role.get(role.size() - 1);
        String trimmedRole = roleLast.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return Role.translateStringToRole(trimmedRole);
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
    public static DayOfWeek parseDayOfWeek(String dayOfWeek) throws ParseException {
        String messageConstraints = "List of valid dayOfWeek: "
                + "Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday.";;
        requireNonNull(dayOfWeek);
        String trimmedDayOfWeek = dayOfWeek.trim().toLowerCase();
        switch (trimmedDayOfWeek) {
        case "monday": return DayOfWeek.MONDAY;
        case "tuesday": return DayOfWeek.TUESDAY;
        case "wednesday": return DayOfWeek.WEDNESDAY;
        case "thursday": return DayOfWeek.THURSDAY;
        case "friday": return DayOfWeek.FRIDAY;
        case "saturday": return DayOfWeek.SATURDAY;
        case "sunday": return DayOfWeek.SUNDAY;
        default: throw new ParseException(messageConstraints);
        }
    }

    /**
     * Parses a {@code String dayOfWeek} into an {@code DayOfWeek}.
     * Leading and trailing whitespaces will be trimmed.
     * This parser is not case sensitive.
     *
     * @throws ParseException if the given {@code dayOfWeek} is invalid.
     */
    public static Slot parseSlot(String slot) throws ParseException {
        requireNonNull(slot);
        String trimmedSlot = slot.trim();
        if (!Slot.isValidSlot(trimmedSlot)) {
            throw new ParseException(Slot.MESSAGE_CONSTRAINTS);
        }
        //maybe need to assert slot cannot be null
        return Slot.translateStringToSlot(slot);
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
}
