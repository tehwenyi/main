    //package seedu.address.logic.commands;
    //
    //import static CommandTestUtil.assertCommandFailure;
    //import static CommandTestUtil.assertCommandSuccess;
    //import static seedu.address.testutil.TypicalPersons.getTypicalEPiggy;
    //
    //import org.junit.Before;
    //import org.junit.Ignore;
    //import org.junit.Test;
    //
    //import CommandHistory;
    //import Model;
    //import ModelManager;
    //import UserPrefs;
    //import seedu.address.model.person.Person;
    //import seedu.address.testutil.PersonBuilder;
    //
    ///**
    // * Contains integration tests (interaction with the Model) for {@code AddCommand}.
    // */
    //public class AddCommandIntegrationTest {
    //
    //    private Model model;
    //    private CommandHistory commandHistory = new CommandHistory();
    //
    //    @Before
    //    public void setUp() {
    //        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
    //    }
    //
    //    @Test
    //    public void execute_newPerson_success() {
    //        Person validPerson = new PersonBuilder().build();
    //
    //        Model expectedModel = new ModelManager(model.getEPiggy(), new UserPrefs());
    //        expectedModel.addPerson(validPerson);
    //        expectedModel.commitEPiggy();
    //
    //        assertCommandSuccess(new AddCommand(validPerson), model, commandHistory,
    //                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    //    }
    //
    //    @Ignore
    //    @Test
    //    public void execute_duplicatePerson_throwsCommandException() {
    //        Person personInList = model.getEPiggy().getPersonList().get(0);
    //        assertCommandFailure(new AddCommand(personInList), model, commandHistory,
    //                AddCommand.MESSAGE_DUPLICATE_EXPENSE);
    //    }
    //
    //}
