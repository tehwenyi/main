package seedu.address.storage.epiggy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.epiggy.item.Name;
import seedu.address.model.tag.Tag;



/**
 * Json friendly version of (@Link Expense)
 */
public class JsonAdaptedExpense {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";

    private final String name;
    private final String cost;
    private final String date;
    private final String type;
    private final List<JsonAdaptedTags> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given expense details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("name") String name,
                              @JsonProperty("cost") String cost,
                              @JsonProperty("date") String date,
                              @JsonProperty("type") String type,
                              @JsonProperty("tags") List<JsonAdaptedTags> tags) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.type = type;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense source) {
        name = source.getItem().getName().name;
        cost = String.valueOf(source.getItem().getCost().getAmount());
        date = source.getDate().toString();
        if (source instanceof Allowance) {
            type = "allowance";
        } else {
            type = "expense";
        }
        tags.addAll(source.getItem().getTags().stream()
                .map(JsonAdaptedTags::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Expense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expense.
     */
    public Expense toModelType() throws IllegalValueException {
        final List<Tag> expenseTags = new ArrayList<>();
        for (JsonAdaptedTags tag: tags) {
            expenseTags.add(tag.toModelType());
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

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        final Date modelDate = new Date();

        final Set<Tag> modelTags = new HashSet<>(expenseTags);

        if (type.equals("allowance")) {
            return new Allowance(new Item(modelName, modelCost, modelTags), modelDate);
        }
        return new Expense(new Item(modelName, modelCost, modelTags), modelDate);
    }
}
