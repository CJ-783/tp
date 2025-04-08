package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Project;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        // Test all constructors with null parameters
        assertThrows(NullPointerException.class, () -> new Person(null,
                                                                  ALICE.getPhone(),
                                                                  ALICE.getEmail(),
                                                                  ALICE.getTags(),
                                                                  ALICE.getProjects()));
        assertThrows(NullPointerException.class, () -> new Person(ALICE.getName(),
                                                                  null,
                                                                  ALICE.getEmail(),
                                                                  ALICE.getTags(),
                                                                  ALICE.getProjects()));
        assertThrows(NullPointerException.class, () -> new Person(ALICE.getName(),
                                                                  ALICE.getPhone(),
                                                                  null,
                                                                  ALICE.getTags(),
                                                                  ALICE.getProjects()));
        assertThrows(NullPointerException.class, () -> new Person(ALICE.getName(),
                                                                  ALICE.getPhone(),
                                                                  ALICE.getEmail(),
                                                                  null,
                                                                  ALICE.getProjects()));
        assertThrows(NullPointerException.class, () -> new Person(ALICE.getName(),
                                                                  ALICE.getPhone(),
                                                                  ALICE.getEmail(),
                                                                  ALICE.getTags(),
                                                                  null));

        // Test constructor with preferred contact method
        assertThrows(NullPointerException.class, () -> new Person(ALICE.getName(),
                                                                  ALICE.getPhone(),
                                                                  ALICE.getEmail(),
                                                                  ALICE.getTags(),
                                                                  ALICE.getProjects(),
                                                                  null));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> person.getProjects().remove(0));
    }

    @Test
    public void getName() {
        assertEquals(ALICE.getName(), new Name("Alice Pauline"));
    }

    @Test
    public void getPhone() {
        assertEquals(ALICE.getPhone(), new Phone("94351253"));
    }

    @Test
    public void getEmail() {
        assertEquals(ALICE.getEmail(), Optional.of(new Email("alice@example.com")));
    }

    @Test
    public void getEmailString() {
        assertEquals(ALICE.getEmailString(), "alice@example.com");

        // Test with no email
        Person personWithNoEmail = new PersonBuilder(ALICE).withEmail(null).build();
        assertEquals(personWithNoEmail.getEmailString(), "No email");
    }

    @Test
    public void getTags() {
        // Cannot directly compare sets due to implementation details, so we check size and containment
        Set<Tag> aliceTags = ALICE.getTags();
        assertTrue(aliceTags.contains(new Tag("friends")));
        assertEquals(1, aliceTags.size());
    }

    @Test
    public void getProjects() {
        // Cannot directly compare sets due to implementation details, so we check size
        Set<Project> aliceProjects = ALICE.getProjects();
        assertEquals(0, aliceProjects.size());
    }

    @Test
    public void getPreferredContactMethod() {
        assertEquals("Phone", ALICE.getPreferredContactMethod().getPreferredContactMethod());

        // Test with email as preferred contact method
        PreferredContactMethod emailPreferred = new PreferredContactMethod("Email");
        Person personWithEmailPreferred = new Person(ALICE.getName(),
                                                     ALICE.getPhone(),
                                                     ALICE.getEmail(),
                                                     ALICE.getTags(),
                                                     ALICE.getProjects(),
                                                     emailPreferred);
        assertEquals("Email", personWithEmailPreferred.getPreferredContactMethod().getPreferredContactMethod());
    }

    @Test
    public void tagsNotInTagSet() {
        // Create a set of tags that includes one tag ALICE already has and one new tag
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("friends"));
        tagSet.add(new Tag("colleagues"));

        // ALICE should only report "colleagues" as not in her tag set
        List<Tag> tagsNotInAlice = ALICE.tagsNotInTagSet(tagSet);
        assertEquals(1, tagsNotInAlice.size());
        assertEquals("colleagues", tagsNotInAlice.get(0).getTagName());

        // Test with empty set
        assertEquals(0, ALICE.tagsNotInTagSet(Collections.emptySet()).size());

        // Test with null set
        assertThrows(NullPointerException.class, () -> ALICE.tagsNotInTagSet(null));
    }

    @Test
    public void projectsNotInProjectSet() {
        // Create a set of projects
        Set<Project> projectSet = new HashSet<>();
        projectSet.add(new Project("website"));
        projectSet.add(new Project("mobile"));

        // ALICE should report both as not in her project set
        List<Project> projectsNotInAlice = ALICE.projectsNotInProjectSet(projectSet);
        assertEquals(2, projectsNotInAlice.size());

        // Test with empty set
        assertEquals(0, ALICE.projectsNotInProjectSet(Collections.emptySet()).size());

        // Test with null set
        assertThrows(NullPointerException.class, () -> ALICE.projectsNotInProjectSet(null));
    }

    @Test
    public void hasSamePhone() {
        // Same phone -> returns true
        assertTrue(ALICE.hasSamePhone(ALICE.getPhone()));

        // Different phone -> returns false
        assertFalse(ALICE.hasSamePhone(new Phone("91234567")));

        // Null phone -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> ALICE.hasSamePhone(null));
    }

    @Test
    public void createEditedPerson() {
        // Create an EditPersonDescriptor with a new name
        EditPersonDescriptor epd = new EditPersonDescriptor()
            .setName(new Name(VALID_NAME_BOB));

        // Create edited person with new name
        Person editedPerson = ALICE.createEditedPerson(epd);
        assertEquals(VALID_NAME_BOB, editedPerson.getName().fullName);
        assertEquals(ALICE.getPhone(), editedPerson.getPhone());
        assertEquals(ALICE.getEmail(), editedPerson.getEmail());
        assertEquals(ALICE.getTags(), editedPerson.getTags());
        assertEquals(ALICE.getProjects(), editedPerson.getProjects());

        // Test with null descriptor
        assertThrows(NullPointerException.class, () -> ALICE.createEditedPerson(null));
    }

    @Test
    public void tagPerson() {
        // Create sets of new tags and projects
        Set<Tag> newTags = new LinkedHashSet<>();
        newTags.add(new Tag(VALID_TAG_HUSBAND));

        Set<Project> newProjects = new LinkedHashSet<>();
        newProjects.add(new Project(VALID_PROJECT_X));

        // Tag ALICE with new tags and projects
        Person taggedPerson = ALICE.tagPerson(newTags, newProjects);

        // Verify the new person has both original and new tags/projects
        assertTrue(taggedPerson.getTags().contains(new Tag("friends")));
        assertTrue(taggedPerson.getTags().contains(new Tag(VALID_TAG_HUSBAND)));
        assertTrue(taggedPerson.getProjects().contains(new Project(VALID_PROJECT_X)));

        // Test with null parameters
        assertThrows(NullPointerException.class, () -> ALICE.tagPerson(null, newProjects));
        assertThrows(NullPointerException.class, () -> ALICE.tagPerson(newTags, null));
    }

    @Test
    public void unTagPerson() {
        // First create a person with multiple tags and projects
        Person multiTaggedPerson = new PersonBuilder(ALICE)
            .withTags("friends", "family")
            .withProjects("website", "mobile")
            .build();

        // Create sets of tags and projects to remove
        Set<Tag> tagsToRemove = new LinkedHashSet<>();
        tagsToRemove.add(new Tag("friends"));

        Set<Project> projectsToRemove = new LinkedHashSet<>();
        projectsToRemove.add(new Project("website"));

        // Untag the person
        Person untaggedPerson = multiTaggedPerson.unTagPerson(tagsToRemove, projectsToRemove);

        // Verify tags and projects were removed
        assertFalse(untaggedPerson.getTags().contains(new Tag("friends")));
        assertTrue(untaggedPerson.getTags().contains(new Tag("family")));
        assertFalse(untaggedPerson.getProjects().contains(new Project("website")));
        assertTrue(untaggedPerson.getProjects().contains(new Project("mobile")));

        // Test with null parameters
        assertThrows(NullPointerException.class, () -> multiTaggedPerson.unTagPerson(null, projectsToRemove));
        assertThrows(NullPointerException.class, () -> multiTaggedPerson.unTagPerson(tagsToRemove, null));
    }

    @Test
    public void replaceProject() {
        // First create a person with a project
        Person personWithProject = new PersonBuilder(ALICE)
            .withProjects("website")
            .build();

        // Replace the project
        Project replacementProject = new Project("website");
        Person personWithReplacedProject = personWithProject.replaceProject(replacementProject);

        // Verify the project was replaced (in this case, it should be the same since we're not changing properties)
        assertTrue(personWithReplacedProject.getProjects().contains(replacementProject));

        // Test with null parameter
        assertThrows(NullPointerException.class, () -> personWithProject.replaceProject(null));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same phone number, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE)
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_ALICE)
            .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different phone number, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        Person editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_AMY).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different projects -> returns false
        editedAlice = new PersonBuilder(ALICE).withProjects(VALID_PROJECT_X).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void hashCode_test() {
        // Same person -> same hashcode
        assertEquals(ALICE.hashCode(), ALICE.hashCode());

        // Different person -> different hashcode
        assertNotEquals(ALICE.hashCode(), BOB.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName()
            + "{name=" + ALICE.getName()
            + ", phone=" + ALICE.getPhone()
            + ", email=" + ALICE.getEmail()
            + ", tags=" + ALICE.getTags()
            + ", projects=" + ALICE.getProjects()
            + "}";
        assertEquals(expected, ALICE.toString());
    }
}
