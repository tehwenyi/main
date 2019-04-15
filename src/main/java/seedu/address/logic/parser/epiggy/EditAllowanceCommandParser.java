package seedu.address.logic.parser.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.epiggy.EditAllowanceCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author kev-inc

/**
 * Parses input arguments and creates a new EditAllowanceCommand object
 */
public class EditAllowanceCommandParser implements Parser<EditAllowanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAllowanceCommand
     * and returns an EditAllowanceCommand object for execution.
     */
    @Override
    public EditAllowanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COST, PREFIX_DATE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(EditAllowanceCommand.MESSAGE_NOT_EDITED,
                            EditAllowanceCommand.MESSAGE_USAGE),
                    pe);
        }

        EditAllowanceCommand.EditAllowanceDescriptor editAllowanceDescriptor = new EditAllowanceCommand
                .EditAllowanceDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editAllowanceDescriptor.setName(ParserUtil.parseItemName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            editAllowanceDescriptor.setCost(ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editAllowanceDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editAllowanceDescriptor::setTags);

        if (!editAllowanceDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAllowanceCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAllowanceCommand(index, editAllowanceDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
