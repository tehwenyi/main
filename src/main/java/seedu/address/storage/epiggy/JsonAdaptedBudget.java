package seedu.address.storage.epiggy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Period;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String amount;
    private final String startDate;
    private final String period;
    private final String remainingAmount;

    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("amount") String amount,
                             @JsonProperty("startDate") String startDate,
                             @JsonProperty("period") String period,
                             @JsonProperty("remainingAmount") String remainingAmount) {
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.remainingAmount = remainingAmount;

    }

    public JsonAdaptedBudget(Budget source) {
        amount = String.valueOf(source.getCost().getAmount());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date sd = source.getStartDate();
        startDate = sdf.format(sd);
        period = source.getPeriod().toString();
        remainingAmount = String.valueOf(source.getRemainingAmount().getAmount());
    }

    public Budget toModelType() throws IllegalValueException {
        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName()));
        }
        final Cost modelAmount = new Cost(amount);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        final Date modelStartDate = ParserUtil.parseDate(startDate);

        if (period == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Period.class.getSimpleName()));
        }
        final Period modelPeriod = new Period(period);

        if (remainingAmount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName()));
        }
        final Cost modelRemaining = new Cost(Double.parseDouble(remainingAmount));

        Budget b = new Budget(modelAmount, modelPeriod, modelStartDate);
        b.setRemainingAmount(modelRemaining);
        return b;
    }

}
