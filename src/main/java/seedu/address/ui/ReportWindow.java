package seedu.address.ui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;

/**
 * Report Window. Provides report and chart to the user.
 */
public class ReportWindow {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Stage window;

    /**
     * A method controls report chart display.
     *
     * @param model EPiggy model
     * @param date  User specified date, month or year
     * @param type  Report display type
     */
    public void displayReportController(Model model, LocalDate date, String type) {
        try {
            logger.info("Creates Report window");
            ReportDisplayType expenseDisplayType = ReportDisplayType.valueOf(type);
            window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Report");
            switch (expenseDisplayType) {
            case ALL:
                displayCompleteReport(model);
                break;
            case DAY:
                displayReportOnSpecifiedDay(model, date);
                break;
            case MONTH:
                displayReportOnSpecifiedMonth(model, date);
                break;
            case YEAR:
                displayReportOnSpecifiedYear(model, date);
                break;
            default:
                displayCompleteReport(model);
                break;
            }
            window.showAndWait();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Display daily summary on area chart.
     */
    private void displayReportOnSpecifiedDay(Model model, LocalDate date) {

        // Creates an Area Chart
        final NumberAxis xAxis = new NumberAxis(0, 24, 1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> areaChart =
                new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle("Report for date: " + date);

        XYChart.Series seriesExpense = new XYChart.Series();
        seriesExpense.setName("Expense");
        yAxis.setLabel("Expense");
        xAxis.setLabel("Hours");
        Calendar calExpenseDay = Calendar.getInstance();
        Calendar calSpecifiedDay = Calendar.getInstance();
        calSpecifiedDay.setTime(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault())
                .toInstant())); // coverts localDate to calendar.

        final ObservableList<Expense> expenses = model.getFilteredExpenseList();
        double[] hours = new double[24];
        if (!expenses.isEmpty()) {
            // expense is not empty
            for (Expense expense : expenses) {
                Date currentDate = expense.getDate();
                calExpenseDay.setTime(currentDate);
                if (calExpenseDay.get(Calendar.DAY_OF_MONTH)
                        == calSpecifiedDay.get(Calendar.DAY_OF_MONTH)
                        && !expense.getItem().getName()
                        .toString().equals("Allowance")) {
                    // find specified date and expense type is not allowance

                    int hour = calExpenseDay.get(Calendar.HOUR_OF_DAY);
                    // hour as index, amount as value
                    hours[hour] += expense.getItem().getCost().getAmount();
                }
            }
            for (int i = 0; i < hours.length; i++) {
                seriesExpense.getData().add(new XYChart.Data(i, hours[i]));
            }
        }

        VBox layout = new VBox();
        layout.getChildren().add(areaChart);
        Scene scene = new Scene(layout, 800, 600);
        areaChart.getData().add(seriesExpense);
        window.setScene(scene);
    }

    /**
     * Displays monthly summary on line chart.
     */
    private void displayReportOnSpecifiedMonth(Model model, LocalDate date) {
        Calendar cal = Calendar.getInstance();
        Date targetDate = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        cal.setTime(targetDate);

        //defining the axes
        final NumberAxis xAxis = new NumberAxis(1, 31, 1);
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Days");
        yAxis.setLabel("Amount");

        //creates the chart
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Report for month: " + date.getMonth().toString()
                .substring(0, 1).toUpperCase() + date.getMonth().toString().substring(1).toLowerCase()
                + " " + date.getYear()); // Format chart title.
        XYChart.Series seriesExpense = new XYChart.Series();
        seriesExpense.setName("Expense");
        XYChart.Series seriesAllowance = new XYChart.Series();
        seriesAllowance.setName("Allowance");

        final ObservableList<Expense> expenses = model.getFilteredExpenseList();
        double[] exps = new double[31];
        double[] allowances = new double[31];
        if (!expenses.isEmpty()) {
            for (Expense expense : expenses) {
                LocalDate currentDay = expense.getDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                if (currentDay.getMonthValue() == date.getMonthValue()
                        && currentDay.getYear() == date.getYear()) {
                    // same year and same month with the target month
                    // -1 because getMonthValue from 1 to 12
                    if (expense.getItem().getName().toString().equals("Allowance")) {
                        allowances[currentDay.getMonthValue() - 1] += expense.getItem().getCost().getAmount();
                    } else {
                        exps[currentDay.getMonthValue() - 1] += expense.getItem().getCost().getAmount();
                    }
                }
            }
        }
        // spot the data into the chart
        for (int i = 0; i < allowances.length; i++) {
            seriesExpense.getData().add(new XYChart.Data(i + 1, exps[i]));
            seriesAllowance.getData().add(new XYChart.Data(i + 1, allowances[i]));
        }

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(seriesExpense, seriesAllowance);

        window.setScene(scene);
    }

    /**
     * Display the proportion of income spent on different categories on pie chart.
     */
    private void displayExpensePercentageReport(Model model) {
        Scene scene = new Scene(new Group(), 800, 600);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Food", 13),
                        new PieChart.Data("Daily necessities", 25),
                        new PieChart.Data("Electronics", 10),
                        new PieChart.Data("Cosmetics", 22),
                        new PieChart.Data("Others", 30));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Percentage of spending on each categories"); (
                (Group) scene.getRoot()
        ).getChildren().add(chart);
        window.setScene(scene);
    }

    /**
     * Displays yearly summary on bar chart.
     */
    private void displayReportOnSpecifiedYear(Model model, LocalDate date) {
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Report for year: " + date.getYear());
        yAxis.setLabel("Amount");
        xAxis.setLabel("Months");

        XYChart.Series seriesAllowance = new XYChart.Series();
        seriesAllowance.setName("Allowance");
        XYChart.Series seriesExpense = new XYChart.Series();
        seriesExpense.setName("Expense");
        XYChart.Series seriesBudget = new XYChart.Series();
        seriesBudget.setName("Budget");

        final ObservableList<Budget> budgetList = model.getFilteredBudgetList();
        final ObservableList<Expense> expenseList = model.getFilteredExpenseList();

        double[] budgets = new double[12];
        double[] savings = new double[12];
        double[] expenses = new double[12];

        if (!expenseList.isEmpty()) {
            for (Expense expense : expenseList) {
                LocalDate currentDate = expense.getDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                if (currentDate.getYear() == date.getYear()) {
                    // found the specified year
                    if (expense.getItem().getName().toString().equals("Allowance")) {
                        // allowance
                        savings[currentDate.getMonthValue() - 1] += expense.getItem().getCost().getAmount();
                    } else {
                        // expense
                        expenses[currentDate.getMonthValue() - 1] += expense.getItem().getCost().getAmount();
                    }
                }
            }
        }
        if (!budgetList.isEmpty()) {
            for (Budget budget : budgetList) {
                LocalDate currentDate = budget.getStartDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                if (currentDate.getYear() == date.getYear()) {
                    budgets[currentDate.getMonthValue() - 1] += budget.getBudgetedAmount().getAmount();
                }
            }
        }

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec"};
        for (int i = 0; i < expenses.length; i++) {
            seriesExpense.getData().add(new XYChart.Data(months[i], expenses[i]));
            seriesBudget.getData().add(new XYChart.Data(months[i], budgets[i]));
            seriesAllowance.getData().add(new XYChart.Data(months[i], savings[i]));
        }

        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(seriesBudget, seriesExpense, seriesAllowance);
        window.setScene(scene);
    }

    /**
     * Displays all expenses, allowances and budgets of the user on bar chart.
     */
    private void displayCompleteReport(Model model) {

        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);

        final ObservableList<Budget> budgets = model.getFilteredBudgetList();
        final ObservableList<Expense> expenses = model.getFilteredExpenseList();

        bc.setTitle("Summary");
        yAxis.setLabel("Amount");
        xAxis.setLabel("Year");

        HashMap<Integer, ReportData> map = new HashMap<>();
        // convert expense data into ReportData
        if (!expenses.isEmpty()) {
            for (int i = 0; i < expenses.size(); i++) {
                int year = expenses.get(i).getDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate().getYear(); // get year from expense
                ReportData data;

                double amount = expenses.get(i).getItem().getCost().getAmount();
                if (map.containsKey(year)) {
                    // if year data exists
                    ReportData temp = map.get(year);
                    if (expenses.get(i).getItem().getName().toString().equals("Allowance")) {
                        temp.setAllowance(temp.updateValue(temp.getAllowance(), amount));
                    } else {
                        temp.setExpense(temp.updateValue(temp.getExpense(), amount));
                    }
                    map.put(year, temp);
                } else {
                    // year data does not exist
                    data = new ReportData(year);
                    if (expenses.get(i).getItem().getName().toString().equals("Allowance")) {
                        data.setAllowance(amount);
                    } else {
                        data.setExpense(amount);
                    }
                    map.put(year, data);
                }
            }
        }
        // convert expense data to ReportData
        if (!budgets.isEmpty()) {
            for (int i = 0; i < budgets.size(); i++) {
                int year = budgets.get(i).getStartDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate().getYear(); // get year from expense
                ReportData data;
                double amount = budgets.get(i).getBudgetedAmount().getAmount();

                if (map.containsKey(year)) {
                    // if year data exists
                    ReportData temp = map.get(year);
                    temp.setBudget(temp.updateValue(temp.getBudget(), amount));
                    map.put(year, temp);
                } else {
                    // year data does not exist
                    data = new ReportData(year);
                    data.setBudget(amount);
                    map.put(year, data);
                }
            }
        }
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Allowance");

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Expense");

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Budget");

        TreeMap<Integer, ReportData> tm = new TreeMap<>(map);
        for (Map.Entry<Integer, ReportData> entry : tm.entrySet()) {
            series1.getData().add(new XYChart.Data(entry.getKey().toString(),
                    entry.getValue().getAllowance()));
            series2.getData().add(new XYChart.Data(entry.getKey().toString(),
                    entry.getValue().getExpense()));
            series3.getData().add(new XYChart.Data(entry.getKey().toString(),
                    entry.getValue().getBudget()));
        }

        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series1, series2, series3);
        window.setScene(scene);
    }

    /**
     * Chart will be displayed according to report display type.
     */
    private enum ReportDisplayType {
        MONTH, DAY, YEAR, ALL
    }
}
