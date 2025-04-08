package seedu.address.testutil;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.SetStatusDescriptor;

/**
 * A utility class to help with building SetStatusDescriptor objects.
 */
public class SetStatusDescriptorBuilder {
    private SetStatusDescriptor descriptor;

    public SetStatusDescriptorBuilder() {
        descriptor = new SetStatusDescriptor();
    }

    /**
     * Sets the {@code isPaid} of the {@code SetStatusDescriptor} that we are building.
     */
    public SetStatusDescriptorBuilder withPayment(String payment) throws ParseException {
        descriptor.setIsPaid(ParserUtil.parsePayment(payment));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code SetStatusDescriptor} that we are building.
     */
    public SetStatusDescriptorBuilder withDeadline(String deadline) throws ParseException {
        descriptor.setDeadline(ParserUtil.parseDeadline(deadline));
        return this;
    }

    /**
     * Sets the {@code isComplete} of the {@code SetStatusDescriptor} that we are building.
     */
    public SetStatusDescriptorBuilder withProgress(String progress) throws ParseException {
        descriptor.setIsComplete(ParserUtil.parseProgress(progress));
        return this;
    }

    public SetStatusDescriptor build() {
        return descriptor;
    }
}
