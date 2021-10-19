package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Period;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedRole> roles = new ArrayList<>();
    private final String salary;
    private final String status;
    private final String schedule;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedPeriod> absentDates = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
            @JsonProperty("address") String address, @JsonProperty("role") List<JsonAdaptedRole> roles,
            @JsonProperty("salary") String salary, @JsonProperty("status") String status,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged, @JsonProperty("schedule") String schedule,
            @JsonProperty("absentDates") List<JsonAdaptedPeriod> absentDates) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (roles != null) {
            this.roles.addAll(roles);
        }
        this.salary = salary;
        this.status = status;
        this.schedule = schedule;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (absentDates != null) {
            this.absentDates.addAll(absentDates);
        }


    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        roles.addAll(source.getRoles().stream()
                .map(JsonAdaptedRole::new)
                .collect(Collectors.toList()));
        salary = source.getSalary().toString();
        status = source.getStatus().getValue();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        absentDates.addAll(source.getAbsentDates().stream()
                .map(JsonAdaptedPeriod::new)
                .collect(Collectors.toList()));
        schedule = source.getSchedule().toString();

    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final List<Period> personAbsentPeriods = new ArrayList<>();
        for (JsonAdaptedPeriod period : absentDates) {
            personAbsentPeriods.add(period.toModelType());
        }


        final List<Role> personRoles = new ArrayList<>();
        for (JsonAdaptedRole role : roles) {
            personRoles.add(role.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Role> modelRoles = new HashSet<>(personRoles);

        if (salary == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName()));
        }
        if (!Salary.isValidSalary(salary)) {
            throw new IllegalValueException(Salary.MESSAGE_CONSTRAINTS);
        }
        final Salary modelSalary = new Salary(salary);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = Status.translateStringToStatus(status);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Set<Period> modelPeriods = new HashSet<>(personAbsentPeriods);

        if (schedule == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Schedule.class.getSimpleName()));
        }

        if (!Schedule.isValidSchedule(schedule.trim())) {
            throw new IllegalValueException(Schedule.MESSAGE_CONSTRAINTS);
        }
        Schedule modelSchedule;
        if (schedule.trim().equals("")) {
            modelSchedule = new Schedule();
        } else {
            modelSchedule = new Schedule(schedule.trim());
        }
        Person p = new Person(modelName, modelPhone, modelEmail,
                modelAddress, modelRoles, modelSalary, modelStatus, modelTags, modelPeriods);


        p.setSchedule(modelSchedule);
        return p;
    }

}
