package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_Y;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.SetStatusDescriptor;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.SetStatusDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SetStatusCommand.
 */
public class SetStatusCommandTest {

    private static final String VALID_DEADLINE = "07 Mar 2025 1800";
    private static final String VALID_PAYMENT_PAID = "paid";
    private static final String VALID_PROGRESS_COMPLETE = "complete";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws ParseException {
        // Setup test person with a project
        Person personWithProject = new PersonBuilder().withProjects(VALID_PROJECT_X).build();
        model.addPerson(personWithProject);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        // Create descriptor with all fields
        SetStatusDescriptor descriptor = new SetStatusDescriptorBuilder()
                .withPayment(VALID_PAYMENT_PAID)
                .withDeadline(VALID_DEADLINE)
                .withProgress(VALID_PROGRESS_COMPLETE)
                .build();

        // Expected edited person
        Person editedPerson = new PersonBuilder(personWithProject)
                .withProjects(VALID_PROJECT_X)
                .build();

        SetStatusCommand command = new SetStatusCommand(index, VALID_PROJECT_X, descriptor);
        String expectedMessage = String.format(SetStatusCommand.MESSAGE_SUCCESS,
                editedPerson.getProjects().iterator().next());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithProject, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyPaymentSpecified_success() throws ParseException {
        // Setup test person with a project
        Person personWithProject = new PersonBuilder().withProjects(VALID_PROJECT_X).build();
        model.addPerson(personWithProject);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        // Create descriptor with only payment
        SetStatusDescriptor descriptor = new SetStatusDescriptorBuilder()
                .withPayment(VALID_PAYMENT_PAID)
                .build();

        // Expected edited person
        Person editedPerson = new PersonBuilder(personWithProject)
                .withProjects(VALID_PROJECT_X)
                .build();

        SetStatusCommand command = new SetStatusCommand(index, VALID_PROJECT_X, descriptor);
        String expectedMessage = String.format(SetStatusCommand.MESSAGE_SUCCESS,
                editedPerson.getProjects().iterator().next());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithProject, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyDeadlineSpecified_success() throws ParseException {
        // Setup test person with a project
        Person personWithProject = new PersonBuilder().withProjects(VALID_PROJECT_X).build();
        model.addPerson(personWithProject);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        // Create descriptor with only deadline
        SetStatusDescriptor descriptor = new SetStatusDescriptorBuilder()
                .withDeadline(VALID_DEADLINE)
                .build();

        // Expected edited person
        Person editedPerson = new PersonBuilder(personWithProject)
                .withProjects(VALID_PROJECT_X)
                .build();

        SetStatusCommand command = new SetStatusCommand(index, VALID_PROJECT_X, descriptor);
        String expectedMessage = String.format(SetStatusCommand.MESSAGE_SUCCESS,
                editedPerson.getProjects().iterator().next());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithProject, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyProgressSpecified_success() throws ParseException {
        // Setup test person with a project
        Person personWithProject = new PersonBuilder().withProjects(VALID_PROJECT_X).build();
        model.addPerson(personWithProject);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        // Create descriptor with only progress
        SetStatusDescriptor descriptor = new SetStatusDescriptorBuilder()
                .withProgress(VALID_PROGRESS_COMPLETE)
                .build();

        // Expected edited person
        Person editedPerson = new PersonBuilder(personWithProject)
                .withProjects(VALID_PROJECT_X)
                .build();

        SetStatusCommand command = new SetStatusCommand(index, VALID_PROJECT_X, descriptor);
        String expectedMessage = String.format(SetStatusCommand.MESSAGE_SUCCESS,
                editedPerson.getProjects().iterator().next());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithProject, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() throws ParseException {
        // Setup test person with a project
        Person personWithProject = new PersonBuilder().withProjects(VALID_PROJECT_X).build();
        model.addPerson(personWithProject);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        // Create descriptor with some fields
        SetStatusDescriptor descriptor = new SetStatusDescriptorBuilder()
                .withProgress(VALID_PROGRESS_COMPLETE)
                .withDeadline(VALID_DEADLINE)
                .build();

        // Expected edited person
        Person editedPerson = new PersonBuilder(personWithProject)
                .withProjects(VALID_PROJECT_X)
                .build();

        SetStatusCommand command = new SetStatusCommand(index, VALID_PROJECT_X, descriptor);
        String expectedMessage = String.format(SetStatusCommand.MESSAGE_SUCCESS,
                editedPerson.getProjects().iterator().next());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithProject, editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SetStatusDescriptor descriptor = new SetStatusDescriptorBuilder().build();
        SetStatusCommand command = new SetStatusCommand(outOfBoundIndex, VALID_PROJECT_X, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nonExistentProject_throwsCommandException() {
        // Setup test person with a different project
        Person personWithProject = new PersonBuilder().withProjects(VALID_PROJECT_Y).build();
        model.addPerson(personWithProject);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        SetStatusDescriptor descriptor = new SetStatusDescriptorBuilder().build();
        SetStatusCommand command = new SetStatusCommand(index, VALID_PROJECT_X, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_ABSENT_PROJECT);
    }

}
