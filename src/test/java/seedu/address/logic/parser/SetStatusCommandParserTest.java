package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SetStatusCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Project;
import seedu.address.testutil.PersonBuilder;

public class SetStatusCommandParserTest {
    private static final String VALID_PAYMENT_PAID = "paid";
    private static final String VALID_DEADLINE = "07 Mar 2025 1800";
    private static final String VALID_PROGRESS_COMPLETE = "complete";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStatusCommand.MESSAGE_USAGE);

    private final SetStatusCommandParser parser = new SetStatusCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser,
                "proj/project-x pay/" + VALID_PAYMENT_PAID
                        + " by/" + VALID_DEADLINE
                        + " prog/" + VALID_PROGRESS_COMPLETE,
                MESSAGE_INVALID_FORMAT);

        // no project specified
        assertParseFailure(parser, "1 pay/" + VALID_PAYMENT_PAID, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1 proj/project-x", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 proj/project-x", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 proj/project-x", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string proj/project-x", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/string proj/project-x", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid payment value
        assertParseFailure(parser, "1 proj/project-x pay/invalid", Project.MESSAGE_PAYMENT_CONSTRAINTS);

        // invalid progress value
        assertParseFailure(parser, "1 proj/project-x prog/invalid", Project.MESSAGE_PROGRESS_CONSTRAINTS);

        // invalid deadline format
        assertParseFailure(parser, "1 proj/project-x by/invalid", Project.MESSAGE_DEADLINE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // duplicate payment prefix
        assertParseFailure(parser, "1 proj/project-x pay/paid pay/unpaid",
                "Multiple values specified for the following single-valued field(s): pay/");

        // duplicate progress prefix
        assertParseFailure(parser, "1 proj/project-x prog/complete prog/incomplete",
                "Multiple values specified for the following single-valued field(s): prog/");

        // duplicate deadline prefix
        assertParseFailure(parser, "1 proj/project-x by/01 Jan 2025 1200 by/02 Feb 2025 1300",
                "Multiple values specified for the following single-valued field(s): by/");
    }

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException, CommandException {
        String userInput = "1 proj/" + VALID_PROJECT_X
                + " pay/" + VALID_PAYMENT_PAID
                + " by/" + VALID_DEADLINE
                + " prog/" + VALID_PROGRESS_COMPLETE;

        // Setup test model with a person having a project
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person personWithProject = new PersonBuilder().withProjects(VALID_PROJECT_X).build();
        model.addPerson(personWithProject);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        // Parse and execute the command
        SetStatusCommand command = parser.parse(userInput);
        CommandResult result = command.execute(model);

        // Verify the person was updated correctly
        Person editedPerson = model.getFilteredPersonList().get(index.getZeroBased());
        Project updatedProject = editedPerson.getProjects().stream()
                .filter(p -> p.hasSameName(VALID_PROJECT_X))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_ABSENT_PROJECT));

        // Verify the command result message
        assertEquals(String.format(SetStatusCommand.MESSAGE_SUCCESS, updatedProject),
                result.getFeedbackToUser());

        // Verify the project fields were updated
        assertEquals("Paid", updatedProject.getPaymentString());
        assertEquals("Complete", updatedProject.getProgressString());
        assertEquals("07 Mar 2025 1800", updatedProject.getDeadlineString());
    }

    @Test
    public void parse_someFieldsSpecified_success() throws ParseException, CommandException {
        String userInput = "1 proj/" + VALID_PROJECT_X
                + " pay/" + VALID_PAYMENT_PAID
                + " prog/" + VALID_PROGRESS_COMPLETE;

        // Setup test model with a person having a project
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person personWithProject = new PersonBuilder().withProjects(VALID_PROJECT_X).build();
        model.addPerson(personWithProject);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        // Parse and execute the command
        SetStatusCommand command = parser.parse(userInput);
        CommandResult result = command.execute(model);

        // Verify the person was updated correctly
        Person editedPerson = model.getFilteredPersonList().get(index.getZeroBased());
        Project updatedProject = editedPerson.getProjects().stream()
                .filter(p -> p.hasSameName(VALID_PROJECT_X))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_ABSENT_PROJECT));

        // Verify the command result message
        assertEquals(String.format(SetStatusCommand.MESSAGE_SUCCESS, updatedProject),
                result.getFeedbackToUser());

        // Verify the project fields were updated
        assertEquals("Paid", updatedProject.getPaymentString());
        assertEquals("Complete", updatedProject.getProgressString());
    }
}
