package seedu.address.logic.parser.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.epiggy.EditExpenseCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.commons.core.index.Index;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditExpenseCommand object
 */
public class EditExpenseCommandParser implements Parser<EditExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_COST, CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditExpenseCommand.MESSAGE_USAGE),
                    pe);
        }

        EditExpenseCommand.EditExpenseDescriptor editExpenseDescriptor = new EditExpenseCommand.EditExpenseDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            editExpenseDescriptor.setName(ParserUtil.parseItemName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_COST).isPresent()) {
            editExpenseDescriptor.setCost(ParserUtil.parseCost(argMultimap.getValue(CliSyntax.PREFIX_COST).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_DATE).isPresent()) {
            editExpenseDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(CliSyntax.PREFIX_DATE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(CliSyntax.PREFIX_TAG)).ifPresent(editExpenseDescriptor::setTags);

        if (!editExpenseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditExpenseCommand.MESSAGE_NOT_EDITED);
        }

        return new EditExpenseCommand(index, editExpenseDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        tagSet.add("Expense");
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
