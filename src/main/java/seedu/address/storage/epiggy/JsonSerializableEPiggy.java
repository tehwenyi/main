package seedu.address.storage.epiggy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EPiggy;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;

//@@author kev-inc

/**
 * An Immutable epiggy that is serializable to JSON format.
 */
@JsonRootName(value = "epiggy")
public class JsonSerializableEPiggy {
    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();
    private final JsonAdaptedGoal goal;


    /**
     * Constructs a {@code JsonSerializableEPiggy} with the given expense.
     */
    @JsonCreator
    public JsonSerializableEPiggy(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses,
                                  @JsonProperty("goal") JsonAdaptedGoal goal,
                                  @JsonProperty("budgets") List<JsonAdaptedBudget> budgets) {
        this.expenses.addAll(expenses);
        this.budgets.addAll(budgets);
        this.goal = goal;
    }

    /**
     * Converts a given {@code ReadOnlyEPiggy} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEPiggy}.
     */
    public JsonSerializableEPiggy(ReadOnlyEPiggy source) {
        expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
        budgets.addAll(source.getBudgetList().stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
        goal = new JsonAdaptedGoal(source.getGoal().get());
    }

    /**
     * Converts this epiggy into the model's {@code EPiggy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EPiggy toModelType() throws IllegalValueException {
        EPiggy ePiggy = new EPiggy();
        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            if (expense instanceof Allowance) {
                ePiggy.addAllowance((Allowance) expense);
            } else {
                ePiggy.addExpense(expense);
            }
        }
        for (int i = 0; i < budgets.size(); i++) {
            Budget budget = budgets.get(i).toModelType();
            ePiggy.addBudget(i, budget);
        }
        ePiggy.setGoal(goal.toModelType());
        return ePiggy;
    }
}
