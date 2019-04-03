package seedu.address.ui;

import java.text.SimpleDateFormat;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
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
        //TODO: max, min, total spend of the day
        double minSpend = Double.MAX_VALUE; // minimum spend of the day
        double maxSpend = Double.MIN_VALUE; // maximum spend of the day
        double totalSpend = 0; // total spend of the day

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
                // convert to calender object.
                calExpenseDay.setTime(currentDate);
                // find specified date and type of the expense is not allowance
                if (calExpenseDay.get(Calendar.DAY_OF_MONTH)
                        == calSpecifiedDay.get(Calendar.DAY_OF_MONTH)
                        && !expense.getItem().getName()
                        .toString().equals("Allowance")) {
                    // find min value
                    double price = expense.getItem().getCost().getAmount();
                    if (price < minSpend) {
                        minSpend = price;
                    }
                    // find max value
                    if (price > maxSpend) {
                        maxSpend = price;
                    }
                    // calculate total value
                    totalSpend += price;
                    // hour as index, amount as value
                    int hour = calExpenseDay.get(Calendar.HOUR_OF_DAY);
                    hours[hour] += expense.getItem().getCost().getAmount();
                }
            }
            for (int i = 0; i < hours.length; i++) {
                seriesExpense.getData().add(new XYChart.Data(i, hours[i])); // spot data to the chart
            }
        }
        // JavaFx chart setup
        // create a layout of the new window
        VBox layout = new VBox(10);
        Label min = new Label();
        Label max = new Label();
        Label total = new Label();

        if (!expenses.isEmpty()) {
            min.setText("The lowest expense record on today: S$" + minSpend);
            max.setText("The highest expense record on today: S$" + maxSpend);
            total.setText("The total amount of expense on today: S$" + totalSpend);
            layout.getChildren().addAll(areaChart, min, max, total);

            // JavaFx bug. 
            VBox.setMargin(areaChart, new Insets(10, 20, 10, 10));
            VBox.setMargin(min, new Insets(5, 10, 0, 50));
            VBox.setMargin(max, new Insets(5, 10, 0, 50));
            VBox.setMargin(total, new Insets(5, 10, 0, 50));
        } else {
            total.setText("No Record found!");
            layout.getChildren().addAll(areaChart, total);
        }
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
        double totalAllowance = 0; // total allowance of the month
        double totalExpense = 0; // total expense of the month
        double minExpense = Double.MAX_VALUE; // a minimum amount of expense within the month
        double maxExpense = Double.MIN_VALUE; // a maximum amount of expense within the month
        int dayWithMinExpense = 0; // the day with minimum expense
        int dayWithMaxExpense = 0; // the day with maximum expense

        if (!expenses.isEmpty()) {
            for (Expense expense : expenses) {
                LocalDate currentDay = expense.getDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                // find same year and same month with the target month
                if (currentDay.getMonthValue() == date.getMonthValue()
                        && currentDay.getYear() == date.getYear()) {
                    // -1 because getMonthValue from 1 to 12
                    if (expense.getItem().getName().toString().equals("Allowance")) {
                        // calculate total allowance
                        allowances[currentDay.getMonthValue() - 1] += expense.getItem().getCost().getAmount();
                    } else {
                        exps[currentDay.getMonthValue() - 1] += expense.getItem().getCost().getAmount();
                    }
                }
            }
        }
        // spot the data into the chart
        for (int i = 0; i < allowances.length; i++) {
            // calculate total expense and allowance
            totalAllowance += allowances[i];
            totalExpense += exps[i];
            // get the days with min, max expense value
            if (exps[i] < minExpense) {
                minExpense = exps[i];
                dayWithMinExpense = i + 1; // day is not start from 0.
            }
            if (exps[i] > maxExpense) {
                maxExpense = exps[i];
                dayWithMaxExpense = i + 1;
            }
            // spot the chart
            seriesExpense.getData().add(new XYChart.Data(i + 1, exps[i]));
            seriesAllowance.getData().add(new XYChart.Data(i + 1, allowances[i]));
        }
        // JavaFX stage content setup
        lineChart.getData().addAll(seriesExpense, seriesAllowance);
        VBox layout = new VBox(10);
        Label labelOfTotalExpense = new Label();
        Label labelOfTotalAllowance = new Label();
        Label labelOfTotalSaving = new Label();
        Label labelOfMinExpenseDay = new Label();
        Label labelOfMaxExpenseDay = new Label();

        if (!expenses.isEmpty()) {
            // adds labels into layout
            labelOfTotalExpense.setText("The total amount of expense on this month: S$"
                    + totalExpense);
            labelOfTotalAllowance.setText("The total amount of allowance on this month: S$"
                    + totalAllowance);
            labelOfTotalSaving.setText("The total amount of Saving on this month: S$"
                    + (totalAllowance - totalExpense));
            labelOfMinExpenseDay.setText("The lowest expense record is S$"
                    + minExpense
                    + " at "
                    + dayWithMinExpense
                    + " "
                    + new SimpleDateFormat("MMM").format(cal.getTime())
                    + " "
                    + cal.get(Calendar.YEAR));
            labelOfMaxExpenseDay.setText("The highest expense record is S$"
                    + maxExpense
                    + " at "
                    + dayWithMaxExpense
                    + " "
                    + new SimpleDateFormat("MMM").format(cal.getTime())
                    + " "
                    + cal.get(Calendar.YEAR));
            layout.getChildren().addAll(lineChart, labelOfTotalExpense, labelOfTotalAllowance,
                    labelOfTotalSaving, labelOfMaxExpenseDay, labelOfMinExpenseDay);
            // JavaFx bug, need to manually set all nodes margin!!!
            VBox.setMargin(lineChart, new Insets(10, 20, 10, 10));
            VBox.setMargin(labelOfTotalAllowance, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfTotalExpense, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfTotalSaving, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfMaxExpenseDay, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfMinExpenseDay, new Insets(5, 10, 0, 50));
        } else {
            labelOfTotalExpense.setText("No record found!");
            layout.getChildren().addAll(lineChart, labelOfTotalExpense);
            // JavaFx bug, need to manually set all nodes margin!!!
            VBox.setMargin(lineChart, new Insets(10, 20, 10, 10));
            VBox.setMargin(labelOfTotalExpense, new Insets(5, 10, 0, 50));
        }
        Scene scene = new Scene(layout, 800, 600);
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
        double[] allowances = new double[12];
        double[] expenses = new double[12];
        double totalAllowance = 0; // total allowance of the year
        double totalExpense = 0; // total expense of the year
        double totalBudget = 0; // total budget of the year
        double minExpenseValue = Double.MAX_VALUE;
        double maxExpenseValue = Double.MIN_VALUE;
        int monthWithMinExpense = 0; // the day with minimum expense
        int monthWithMaxExpense = 0; // the day with maximum expense

        if (!expenseList.isEmpty()) {
            for (Expense expense : expenseList) {
                LocalDate currentDate = expense.getDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate();
                if (currentDate.getYear() == date.getYear()) {
                    // found the specified year
                    if (expense.getItem().getName().toString().equals("Allowance")) {
                        // allowance
                        allowances[currentDate.getMonthValue() - 1] += expense.getItem().getCost().getAmount();
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
                    budgets[currentDate.getMonthValue() - 1] += budget.getCost().getAmount();
                }
            }
        }

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};

        String[] monthsLong = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
        for (int i = 0; i < expenses.length; i++) {
            // calculate total expense and allowance
            totalExpense += expenses[i];
            totalAllowance += allowances[i];
            totalBudget += budgets[i];

            // get the days with min, max expense value
            if (expenses[i] < minExpenseValue) {
                minExpenseValue = expenses[i];
                monthWithMinExpense = i;
            }
            if (expenses[i] > maxExpenseValue) {
                maxExpenseValue = expenses[i];
                monthWithMaxExpense = i;
            }
            seriesExpense.getData().add(new XYChart.Data(months[i], expenses[i]));
            seriesBudget.getData().add(new XYChart.Data(months[i], budgets[i]));
            seriesAllowance.getData().add(new XYChart.Data(months[i], allowances[i]));
        }

        VBox layout = new VBox(10);
        Label labelOfTotalExpense = new Label();
        Label labelOfTotalAllowance = new Label();
        Label labelOfTotalSaving = new Label();
        Label labelOfTotalBudget = new Label();
        Label labelOfMinExpenseDay = new Label();
        Label labelOfMaxExpenseDay = new Label();

        if (!expenseList.isEmpty()) {
            // adds labels into layout
            labelOfTotalExpense.setText("The total amount of expense on this year: S$"
                    + totalExpense);
            labelOfTotalAllowance.setText("The total amount of allowance on this year: S$"
                    + totalAllowance);
            labelOfTotalSaving.setText("The total amount of saving on this year: S$"
                    + (totalAllowance - totalExpense));
            labelOfTotalBudget.setText("The total amount of budget on this year: S$"
                    + totalBudget);
            labelOfMinExpenseDay.setText(monthsLong[monthWithMinExpense]
                    + " is the least consumed month in "
                    + date.getYear()
                    + ". The lowest expense record is S$"
                    + minExpenseValue);

            labelOfMaxExpenseDay.setText(monthsLong[monthWithMaxExpense]
                    + " is the most consumed month in "
                    + date.getYear()
                    + ". The highest expense record is S$"
                    + maxExpenseValue);
            layout.getChildren().addAll(bc, labelOfTotalExpense, labelOfTotalAllowance,
                    labelOfTotalBudget, labelOfTotalSaving, labelOfMaxExpenseDay, labelOfMinExpenseDay);
            VBox.setMargin(bc, new Insets(10, 20, 10, 10));
            VBox.setMargin(labelOfTotalAllowance, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfTotalExpense, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfTotalSaving, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfTotalBudget, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfMaxExpenseDay, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfMinExpenseDay, new Insets(5, 10, 0, 50));
        } else {
            labelOfTotalExpense.setText("No record found!");
            layout.getChildren().addAll(bc, labelOfTotalExpense);
            VBox.setMargin(bc, new Insets(10, 20, 10, 10));
            VBox.setMargin(labelOfTotalExpense, new Insets(5, 10, 0, 50));
        }
        Scene scene = new Scene(layout, 800, 650);
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

        bc.setTitle("Completed Summary");
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
                double amount = budgets.get(i).getCost().getAmount();

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

        double totalExpense = 0;
        double totalBudget = 0;
        double totalAllowance = 0;
        double maxExpense = Double.MIN_VALUE;
        String YearWithMaxExpense = "";
        TreeMap<Integer, ReportData> tm = new TreeMap<>(map);
        for (Map.Entry<Integer, ReportData> entry : tm.entrySet()) {
            totalAllowance += entry.getValue().getAllowance();
            totalBudget += entry.getValue().getBudget();
            totalExpense += entry.getValue().getExpense();

            if (entry.getValue().getExpense() > maxExpense) {
                maxExpense = entry.getValue().getExpense();
                YearWithMaxExpense = entry.getKey().toString();
            }

            series1.getData().add(new XYChart.Data(entry.getKey().toString(),
                    entry.getValue().getAllowance()));
            series2.getData().add(new XYChart.Data(entry.getKey().toString(),
                    entry.getValue().getExpense()));
            series3.getData().add(new XYChart.Data(entry.getKey().toString(),
                    entry.getValue().getBudget()));
        }
        VBox layout = new VBox(10);
        Label labelOfTotalExpense = new Label();
        Label labelOfTotalAllowance = new Label();
        Label labelOfTotalSaving = new Label();
        Label labelOfTotalBudget = new Label();
        Label labelOfMaxExpenseValue = new Label();
        Label labelOfMaxExpenseYear = new Label();
        if (!tm.isEmpty()) {
            // adds labels into layout
            labelOfTotalExpense.setText("The total amount of expense: S$"
                    + totalExpense);
            labelOfTotalAllowance.setText("The total amount of allowance: S$"
                    + totalAllowance);
            labelOfTotalSaving.setText("The total amount of saving: S$"
                    + (totalAllowance - totalExpense));
            labelOfTotalBudget.setText("The total amount of budget: S$"
                    + totalBudget);
            labelOfMaxExpenseYear.setText(YearWithMaxExpense
                    + " is the most consumed year. "
                    + "The highest expense record is S$"
                    + maxExpense);
            layout.getChildren().addAll(bc, labelOfTotalExpense, labelOfTotalBudget, labelOfTotalAllowance,
                    labelOfTotalSaving, labelOfMaxExpenseYear,labelOfMaxExpenseValue);
            // JavaFx bug, need to manually set all nodes margin!!!
            VBox.setMargin(bc, new Insets(10, 20, 10, 10));
            VBox.setMargin(labelOfTotalAllowance, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfTotalExpense, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfTotalSaving, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfTotalBudget, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfMaxExpenseValue, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfMaxExpenseYear, new Insets(5, 10, 0, 50));
        } else {
            labelOfTotalExpense.setText("No record found!");
            layout.getChildren().addAll(bc, labelOfTotalExpense);
            VBox.setMargin(labelOfTotalExpense, new Insets(5, 10, 0, 50));
            VBox.setMargin(labelOfTotalExpense, new Insets(5, 10, 0, 50));
        }
        Scene scene = new Scene(layout, 800, 650);
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
