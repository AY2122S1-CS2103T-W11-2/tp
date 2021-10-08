package seedu.address.model.person;

/**
 * This class stands for every possible role for a staff
 */
public enum Role {
    //Can add more later
    MANAGER("manager"), DIRECTOR("director"), HR("hr");

    private String role;

    private Role(String role) {
        this.role = role;
    }

    /**
     * Gets the value of a role.
     *
     * @return The value of a role.
     */
    public String getValue() {
        return role;
    }

    public static String getMessageConstraints() {
        return "This role does not exist";
    }
}
