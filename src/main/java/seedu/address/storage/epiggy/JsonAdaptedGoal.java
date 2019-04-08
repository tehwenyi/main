package seedu.address.storage.epiggy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Name;

/**
 * Json friendly version of (@Link Goal)
 */
public class JsonAdaptedGoal {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Goal's %s field is missing!";

    private final Boolean isNull;
    private final String name;
    private final String cost;

    /**
     * Constructs a {@code JsonAdaptedGoal} with the given goal details.
     */
    @JsonCreator
    public JsonAdaptedGoal(
            @JsonProperty("isNull") Boolean isNull,
            @JsonProperty("name") String name,
            @JsonProperty("cost") String cost) {
        this.isNull = isNull;
        this.name = name;
        this.cost = cost;
    }

    /**
     * Converts a given {@code Goal} into this class for Jackson use.
     */
    public JsonAdaptedGoal(Goal source) {
        if (source == null) {
            isNull = true;
            name = null;
            cost = null;
        } else {
            isNull = false;
            name = source.getName().toString();
            cost = String.valueOf(source.getAmount().getAmount());
        }
    }

    /**
     * Converts this Jackson-friendly adapted goal object into the model's {@code Goal} object.
     *
     * @return
     * @throws IllegalValueException
     */
    public Goal toModelType() throws IllegalValueException {
        if (isNull) {
            return null;
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        final Name modelName = new Name(name);

        if (cost == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName()));
        }
        if (!Cost.isValidCost(cost)) {
            throw new IllegalValueException(Cost.MESSAGE_CONSTRAINTS);
        }
        final Cost modelCost = new Cost(cost);
        return new Goal(modelName, modelCost);
    }

}
