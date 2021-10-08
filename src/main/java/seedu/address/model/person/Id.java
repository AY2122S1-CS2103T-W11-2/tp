package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Staff's id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStaffId(String)}
 */
public class Id {
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The Id must be a eight digit string which only contains number.
     */
    private static final String VALIDATION_REGEX = "^\\d{8}$";

    private final String staffId;

    /**
     * Constructs a {@code Id}.
     *
     * @param staffId A valid staffId.
     */
    public Id(String staffId) {
        requireNonNull(staffId);
        checkArgument(isValidStaffId(staffId), MESSAGE_CONSTRAINTS);
        this.staffId = staffId;
    }

    public static Id generateUniqueId() {
        return new Id("");
    }
    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidStaffId(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return staffId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Id // instanceof handles nulls
                && staffId.equals(((Id) other).staffId)); // state check
    }

    @Override
    public int hashCode() {
        return staffId.hashCode();
    }
}
