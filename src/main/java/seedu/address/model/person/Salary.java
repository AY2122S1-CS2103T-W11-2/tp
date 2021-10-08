package seedu.address.model.person;

/**
 * Represents a staff's salary.
 */
public class Salary {
    public static final String MESSAGE_CONSTRAINTS =
            "Salary numbers should only contain numbers and one decimal point, " +
                    "and the fraction part should exactly have two digits";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    private static final int DECIMAL_DIGITS = 2;

    private String salaryStr;
    private float salaryValue;

    /**
     * Constructs an {@code Salary}.
     *
     * @param salary A valid salary.
     */
    public Salary(String salary) {
        this.salaryStr = salary;

    }

    /**
     * Gets the string representation of the salary.
     * @return The string representation of the salary.
     */
    public String getSalaryStr() {
        return salaryStr;
    }

    /**
     * Gets the float value of the salary.
     * @return The float representation of the salary.
     */
    public float getSalaryValue() {
        return salaryValue;
    }

    /**
     * Returns true if a given string is a valid salary.
     */
    public static boolean isValidSalary(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Turns the inputString into a string of two decimal digits number.
     * @param inputStr
     * @return
     */
    public static String keepTwoDecimal(String inputStr) {
        int strLength = inputStr.length();
        if (!inputStr.contains(".")) {
            return inputStr + ".00";
        }
        int decimalPointPos = inputStr.indexOf(".");
        //2.12
        if (strLength > decimalPointPos + DECIMAL_DIGITS + 1) {
            return inputStr.substring(decimalPointPos + DECIMAL_DIGITS + 1);
        } else if (strLength < decimalPointPos + DECIMAL_DIGITS + 1) {
            return inputStr + "0";
        } else {
            return inputStr;
        }
    }

    @Override
    public String toString() {
        return salaryStr;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Salary // instanceof handles nulls
                && salaryStr.equals(((Salary) other).salaryStr)
                && salaryValue == ((Salary) other).salaryValue); // state check
    }

    @Override
    public int hashCode() {
        return salaryStr.hashCode();
    }
}
