package seedu.address.storage.epiggy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.epiggy.Savings;

/**
 * Json friendly version of (@Link Savings)
 */
public class JsonAdaptedSavings {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Savings's %s field is missing!";

    private final String savings;

    /**
     * Constructs a {@code JsonAdaptedSavings} with the given savings details.
     * @param savings
     */
    @JsonCreator
    public JsonAdaptedSavings(@JsonProperty("savings") String savings) {
        this.savings = savings;
    }

    /**
     * Converts a given {@code Savings} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedSavings(Savings source) {
        savings = String.valueOf(source.getSavings());
    }

    /**
     * Converts this Jackson-friendly adapted goal object into the model's {@code Savings} object.
     *
     * @return
     * @throws IllegalValueException
     */
    public Savings toModelType() throws IllegalValueException {
        if (savings == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Savings.class.getSimpleName()));
        }
        return new Savings(Double.parseDouble(savings));
    }
}
