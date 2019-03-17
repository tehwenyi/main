package seedu.address.model.epiggy;

public class Savings {
    private float savings;

    public Savings(float savings) {
        this.savings = savings;
    }

    public float getSavings() {
        return savings;
    }

    public void addSavings(float increment) {
        savings += increment;
    }

    public void deductSavings(float decrement) {
        savings -= decrement;
    }

    @Override
    public String toString() {
        return String.format("$%.2f", savings);
    }
}
