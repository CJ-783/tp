package seedu.address.model.tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * Represents a Project in the address book.
 * Project is a type of Tag (Extends Tag).
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Project extends Tag {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline should be in the format 'dd MMM uuuu HHmm' (e.g 01 Apr 2026 2359) and must be a valid "
                    + "date and time.";
    public static final String MESSAGE_PROGRESS_CONSTRAINTS =
            "Progress should be either be 'Complete' or 'Incomplete'";
    public static final String MESSAGE_PAYMENT_CONSTRAINTS =
            "Payment should be either be 'Paid' or 'Unpaid'";
    public static final String DATETIME_FORMAT = "dd MMM uuuu HHmm";
    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern(DATETIME_FORMAT).toFormatter(Locale.ENGLISH);

    private final boolean isComplete;
    private final boolean isPaid;
    private final LocalDateTime deadline;


    /**
     * Constructs a {@code Project}.
     * @param tagName A valid tag name.
     * @param isComplete progress status.
     * @param isPaid payment status.
     * @param deadline deadline in dd MMM uuuu HHmm format.
     */
    private Project(String tagName, boolean isComplete, boolean isPaid, LocalDateTime deadline) {
        super(tagName);
        this.isComplete = isComplete;
        this.isPaid = isPaid;
        this.deadline = deadline;
    }

    /**
     * Constructs a {@code Project} using String representation of Project attributes.
     * Used by JsonAdaptedProject.java
     *
     * @param tagName A valid tag name.
     * @param isComplete Complete or Incomplete.
     * @param isPaid Paid or Unpaid.
     * @param deadline deadline in dd MMM uuuu HHmm format.
     */
    public Project(String tagName, String isComplete, String isPaid, String deadline) {
        super(tagName);
        this.isComplete = (isComplete.equalsIgnoreCase("complete"));
        this.isPaid = (isPaid.equalsIgnoreCase("paid"));
        this.deadline = dateTimeStringToLocalDateTime(deadline);
    }

    /**
     * Constructs a {@code Project}.
     *
     * @param tagName A valid tag name.
     */
    public Project(String tagName) {
        super(tagName);
        this.isComplete = false;
        this.isPaid = false;
        this.deadline = LocalDateTime.now().plusDays(1); // Set the deadline to 1 day from creation.
    }

    /**
     * Creates and returns a new {@code Project} with updated details.
     */
    public Project createEditedProject(SetStatusDescriptor setStatusDescriptor) {
        boolean newIsComplete = setStatusDescriptor.isComplete().orElse(this.isComplete);
        boolean newIsPaid = setStatusDescriptor.isPaid().orElse(this.isPaid);
        LocalDateTime newDeadline = setStatusDescriptor.deadline().orElse(this.deadline);
        return new Project(this.tagName, newIsComplete, newIsPaid, newDeadline);
    }

    /**
     * Converts String to LocalDateTime based on DATETIME_FORMAT
     * @param dateTime String representation of datetime
     */
    public static LocalDateTime dateTimeStringToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime.trim(), formatter);
    }

    /**
     * Get the progress status as a String
     * 'Complete' if true, 'Incomplete' if false
     */
    public String getProgressString() {
        return this.isComplete ? "Complete" : "Incomplete";
    }

    /**
     * Get the payment status as a String.
     * Returns 'Paid' if true, 'Unpaid' if false.
     */
    public String getPaymentString() {
        return this.isPaid ? "Paid" : "Unpaid";
    }

    /**
     * Get the deadline as String.
     */
    public String getDeadlineString() {
        return this.deadline.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Project otherProject) {
            return this.tagName.equals(otherProject.tagName);
        }
        return false;
    }

    /**
     * Returns a String representation of the Project
     */
    @Override
    public String toString() {
        return '[' + getTagName() + " | Deadline: " + getDeadlineString() + "hrs | " + getProgressString()
                + " | " + getPaymentString() + ']';
    }
}
