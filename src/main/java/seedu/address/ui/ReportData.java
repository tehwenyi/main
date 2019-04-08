package seedu.address.ui;

/**
 * Data class is used for collecting data from expenses, savings and budgets.
 * Data class is also used for spot the data to the report chart.
 */
public class ReportData {
    private int year;
    private double budget;
    private double expense;
    private double allowance;


    public ReportData(int year) {
        this.year = year;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public double getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double updateValue(double original, double newValue) {
        return original + newValue;
    }
}
