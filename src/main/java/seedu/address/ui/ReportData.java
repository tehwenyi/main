package seedu.address.ui;

/**
 * Data class is used for collecting data from expenses, savings and budgets.
 * Data class is also used for spot the data to the report chart.
 */
public class ReportData {
    private int year;
    private int budget;
    private int expense;
    private int saving;

    public ReportData(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public int getSaving() {
        return saving;
    }

    public void setSaving(int saving) {
        this.saving = saving;
    }

    public int updateValue(int original, int newValue) {
        return original + newValue;
    }
}
