package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.staff.Staff;
import seedu.address.model.staff.UniqueStaffList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameStaff comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueStaffList staffs;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        staffs = new UniqueStaffList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Staffs in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the staff list with {@code staffs}.
     * {@code staffs} must not contain duplicate staffs.
     */
    public void setStaffs(List<Staff> staffs) {
        this.staffs.setStaffs(staffs);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setStaffs(newData.getStaffList());
    }

    //// staff-level operations

    /**
     * Returns true if a person with the same identity as {@code staff} exists in the address book.
     */
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return staffs.contains(staff);
    }

    /**
     * Adds a staff to the address book.
     * The staff must not already exist in the address book.
     */
    public void addStaff(Staff p) {
        staffs.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setStaff(Staff target, Staff editedStaff) {
        requireNonNull(editedStaff);

        staffs.setStaff(target, editedStaff);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeStaff(Staff key) {
        staffs.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return staffs.asUnmodifiableObservableList().size() + " staffs";
        // TODO: refine later
    }

    @Override
    public ObservableList<Staff> getStaffList() {
        return staffs.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && staffs.equals(((AddressBook) other).staffs));
    }

    @Override
    public int hashCode() {
        return staffs.hashCode();
    }
}
