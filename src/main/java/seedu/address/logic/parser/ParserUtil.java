package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Name;
import seedu.address.model.expense.Period;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Name parseItemName(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String cost} into a {@code Cost}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Cost parseCost(String cost) throws ParseException {
        requireNonNull(cost);
        String trimmedCost = cost.trim();
        if (!Cost.isValidCost(trimmedCost)) {
            throw new ParseException(Cost.MESSAGE_CONSTRAINTS);
        }
        return new Cost(Double.parseDouble(trimmedCost));
    }

    /**
     * Parses a {@code String Date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        Date parsedDate;
        // TODO add more forms of setting date eg. dd.mm.yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            parsedDate = dateFormat.parse(date.trim());
        } catch (java.text.ParseException parseException) {
            throw new ParseException("Date should be in the format dd/mm/yyyy.");
        }
        return parsedDate;
    }

    /**
     * Parses a {@code String period} into a {@code Period}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Period parsePeriod(String period) throws ParseException {
        requireNonNull(period);
        String trimmedPeriod = period.trim();
        if (!Period.isValidPeriod(trimmedPeriod)) {
            throw new ParseException(Period.MESSAGE_CONSTRAINTS);
        }
        return new Period(Integer.parseInt(trimmedPeriod));
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
