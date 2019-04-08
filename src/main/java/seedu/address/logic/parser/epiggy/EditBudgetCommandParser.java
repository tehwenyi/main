package seedu.address.logic.parser.epiggy;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.epiggy.EditBudgetCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditBudgetCommand object
 */
public class EditBudgetCommandParser implements Parser<EditBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBudgetCommand
     * and returns an EditBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBudgetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_COST, CliSyntax.PREFIX_PERIOD, CliSyntax.PREFIX_DATE);

        EditBudgetCommand.EditBudgetDetails editBudgetDetails = new EditBudgetCommand.EditBudgetDetails();
        if (argMultimap.getValue(CliSyntax.PREFIX_COST).isPresent()) {
            editBudgetDetails.setAmount(ParserUtil.parseCost(argMultimap.getValue(CliSyntax.PREFIX_COST).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_PERIOD).isPresent()) {
            editBudgetDetails.setPeriod(ParserUtil.parsePeriod(argMultimap.getValue(CliSyntax.PREFIX_PERIOD).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_DATE).isPresent()) {
            editBudgetDetails.setStartDate(ParserUtil.parseDate(argMultimap.getValue(CliSyntax.PREFIX_DATE).get()));
        }

        if (!editBudgetDetails.isAnyFieldEdited()) {
            throw new ParseException(EditBudgetCommand.MESSAGE_NOT_EDITED);
        }

        return new EditBudgetCommand(editBudgetDetails);
    }

}
