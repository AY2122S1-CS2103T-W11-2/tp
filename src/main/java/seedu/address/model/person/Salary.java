package seedu.address.model.person;

/**
 * Represents a staff's salary.
 */
public class Salary {
    private float salary;

    /**
     * Constructs an {@code Salary}.
     *
     * @param salary A valid salary.
     */
    public Salary(float salary) {
        this.salary = (float) ((float) Math.round(salary * 100) / 100.0);
    }

    public float getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return Float.toString(salary);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Salary // instanceof handles nulls
                && salary == ((Salary) other).getSalary()); // state check
    }

    @Override
    public int hashCode() {
        return Float.toString(salary).hashCode();
    }
}
