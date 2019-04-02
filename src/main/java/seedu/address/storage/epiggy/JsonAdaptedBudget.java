package seedu.address.storage.epiggy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.epiggy.Budget;

public class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String amount;
//    private final String startDate;
//    private final String endDate;
//    private final String remainingAmount;

    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("amount") String amount,
                             @JsonProperty("startDate") String startDate,
                             @JsonProperty("endDate") String endDate,
                             @JsonProperty("remainingAmount") String remainingAmount) {
        this.amount = amount;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.remainingAmount = remainingAmount;
    }

    public JsonAdaptedBudget(Budget source) {
        amount = String.valueOf(source.getCost().getAmount());
    }

}
