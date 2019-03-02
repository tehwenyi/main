package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {
    // TODO: Modify add command and pass all the JUnit test cases.
    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

/*
original code

public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
+ "Parameters: "
+ PREFIX_NAME + "NAME "
+ PREFIX_PHONE + "PHONE "
+ PREFIX_EMAIL + "EMAIL "
+ PREFIX_ADDRESS + "ADDRESS "
+ "[" + PREFIX_TAG + "TAG]...\n"
+ "Example: " + COMMAND_WORD + " "
+ PREFIX_NAME + "John Doe "
+ PREFIX_PHONE + "98765432 "
+ PREFIX_EMAIL + "johnd@example.com "
+ PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
+ PREFIX_TAG + "friends "
+ PREFIX_TAG + "owesMoney";
*/

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the ePiggy. "
            + "Parameters: "
            + PREFIX_NAME + "EXPENSE NAME "
            + PREFIX_COST + "COST "
            + PREFIX_CATEGORY + "CATEGORY "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Chicken Rice Set"
            + PREFIX_COST + "15 "
            + PREFIX_CATEGORY + "Food"
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_DATE + "2019-02-21"
            + PREFIX_TAG + "Lunch";

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";
    //    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists int he address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            //throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
