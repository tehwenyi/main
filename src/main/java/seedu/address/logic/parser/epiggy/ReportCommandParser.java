package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.FORMAT_ERROR_MESSAGE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.logic.commands.epiggy.ReportCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and display report.
 */
public class ReportCommandParser implements Parser<ReportCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ReportCommand
     * and returns an ReportCommand object for execution.
     */
    @Override
    public ReportCommand parse(String args) throws ParseException {
        LocalDate date;
        int day = 1;
        int month = 1;
        int year = 1970;
        String[] type = {"YEAR", "MONTH", "DAY", "ALL"};

        if (args.equals("")) {
            // no parameter found
            return new ReportCommand(null, type[3]);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReportCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String dateString = argMultimap.getValue(PREFIX_DATE).get();
            // splits the dateString into year, month and day.
            String[] dateArr = dateString.split("/");
            try {
                if (dateArr.length == 3) {
                    // date string contains year, month and day
                    day = Integer.valueOf(dateArr[0]);
                    month = Integer.valueOf(dateArr[1]);
                    year = Integer.valueOf(dateArr[2]);
                    date = LocalDate.of(year, month, day);
                    return new ReportCommand(date, type[2]);
                } else if (dateArr.length == 2) {
                    // date string only contains month and year
                    month = Integer.valueOf(dateArr[0]);
                    year = Integer.valueOf(dateArr[1]);
                    date = LocalDate.of(year, month, day);
                    return new ReportCommand(date, type[1]);
                } else if (dateArr.length == 1) {
                    year = Integer.valueOf(dateArr[0]);
                    date = LocalDate.of(year, month, day);
                    return new ReportCommand(date, type[0]);
                } else {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ReportCommand.MESSAGE_USAGE));
                }
            } catch (Exception e) {
                throw new ParseException(String.format(FORMAT_ERROR_MESSAGE,
                        ReportCommand.MESSAGE_USAGE));
            }
        } else {
            date = LocalDate.now(); // useless value
            return new ReportCommand(date, type[3]);
        }
    }
}
