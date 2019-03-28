package seedu.address.ui;

import java.time.LocalDate;
import java.time.ZoneId;
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
 * The Summary Window. Provides summary and chart to the user.
 */
public class ReportWindow {
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * A method controls report display.
     *
     * @param model AddressBook model
     * @param date  User specified date, month or year
     * @param type  Report display type
     */
    public void displayReportController(Model model, LocalDate date, String type) {
        logger.info("Creates Report window");
        ReportDisplayType expenseDisplayType = ReportDisplayType.valueOf(type);
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
    }

    /**
     * Display daily summary on area chart.
     */
    private void displayReportOnSpecifiedDay(Model model, LocalDate date) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Report for " + date.toString());
        // Creates an Area Chart
        final NumberAxis xAxis = new NumberAxis(1, 31, 1);
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart<Number, Number> areaChart =
                new AreaChart<Number, Number>(xAxis, yAxis);
        areaChart.setTitle("Daily Summary");

        XYChart.Series seriesExpense = new XYChart.Series();
        seriesExpense.setName("Expense");
        yAxis.setLabel("Expense");
        xAxis.setLabel("Days");

        seriesExpense.getData().add(new XYChart.Data(1, 400));
        seriesExpense.getData().add(new XYChart.Data(2, 1000));
        seriesExpense.getData().add(new XYChart.Data(3, 500));
        seriesExpense.getData().add(new XYChart.Data(4, 800));
        seriesExpense.getData().add(new XYChart.Data(5, 500));
        seriesExpense.getData().add(new XYChart.Data(6, 1800));
        seriesExpense.getData().add(new XYChart.Data(7, 1500));
        seriesExpense.getData().add(new XYChart.Data(8, 1300));
        seriesExpense.getData().add(new XYChart.Data(9, 1009));
        seriesExpense.getData().add(new XYChart.Data(10, 2001));
        seriesExpense.getData().add(new XYChart.Data(11, 3001));
        seriesExpense.getData().add(new XYChart.Data(12, 401));

        VBox layout = new VBox();
        layout.getChildren().add(areaChart);
        Scene scene = new Scene(layout, 800, 600);
        areaChart.getData().add(seriesExpense);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Displays monthly summary on line chart.
     */
    private void displayReportOnSpecifiedMonth(Model model, LocalDate date) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Monthly Summary");

        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Months");
        yAxis.setLabel("Expense");

        //creates the chart
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Monthly Expense Summary");
        XYChart.Series seriesExpense = new XYChart.Series();
        seriesExpense.setName("Expense");

        seriesExpense.getData().add(new XYChart.Data("Jan", 1230));
        seriesExpense.getData().add(new XYChart.Data("Feb", 1400));
        seriesExpense.getData().add(new XYChart.Data("Mar", 1500));
        seriesExpense.getData().add(new XYChart.Data("Apr", 2004));
        seriesExpense.getData().add(new XYChart.Data("May", 1112));
        seriesExpense.getData().add(new XYChart.Data("Jun", 2006));
        seriesExpense.getData().add(new XYChart.Data("Jul", 1002));
        seriesExpense.getData().add(new XYChart.Data("Aug", 1005));
        seriesExpense.getData().add(new XYChart.Data("Sep", 1403));
        seriesExpense.getData().add(new XYChart.Data("Oct", 1007));
        seriesExpense.getData().add(new XYChart.Data("Nov", 1209));
        seriesExpense.getData().add(new XYChart.Data("Dec", 1225));

        XYChart.Series seriesBudget = new XYChart.Series();
        seriesBudget.setName("Budget");
        seriesBudget.getData().add(new XYChart.Data("Jan", 1500));
        seriesBudget.getData().add(new XYChart.Data("Feb", 1500));
        seriesBudget.getData().add(new XYChart.Data("Mar", 1500));
        seriesBudget.getData().add(new XYChart.Data("Apr", 1500));
        seriesBudget.getData().add(new XYChart.Data("May", 1500));
        seriesBudget.getData().add(new XYChart.Data("Jun", 1500));
        seriesBudget.getData().add(new XYChart.Data("Jul", 1500));
        seriesBudget.getData().add(new XYChart.Data("Aug", 1500));
        seriesBudget.getData().add(new XYChart.Data("Sep", 1500));
        seriesBudget.getData().add(new XYChart.Data("Oct", 1500));
        seriesBudget.getData().add(new XYChart.Data("Nov", 1500));
        seriesBudget.getData().add(new XYChart.Data("Dec", 1500));

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(seriesExpense, seriesBudget);

        window.setScene(scene);
        window.showAndWait();

    }

    /**
     * Display the proportion of income spent on different categories on pie chart.
     */
    private void displayExpensePercentageReport(Model model) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Summary");
        Scene scene = new Scene(new Group(), 800, 600);

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Food", 13),
                        new PieChart.Data("Daily necessities", 25),
                        new PieChart.Data("Electronics", 10),
                        new PieChart.Data("Cosmetics", 22),
                        new PieChart.Data("Others", 30));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Percentage of spending on each categories");
        (
                (Group) scene.getRoot()
        ).getChildren().add(chart);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Displays yearly summary on bar chart.
     */
    private void displayReportOnSpecifiedYear(Model model, LocalDate date) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Bar Chart Sample");
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Yearly Summary");
        yAxis.setLabel("Expense");
        xAxis.setLabel("Year");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Saving");
        series1.getData().add(new XYChart.Data("2015", 2521.34));
        series1.getData().add(new XYChart.Data("2016", 2348.82));
        series1.getData().add(new XYChart.Data("2017", 1040));
        series1.getData().add(new XYChart.Data("2018", 3207.15));
        series1.getData().add(new XYChart.Data("2019", 1320));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Expense");
        series2.getData().add(new XYChart.Data("2015", 25601.34));
        series2.getData().add(new XYChart.Data("2016", 20148.82));
        series2.getData().add(new XYChart.Data("2017", 10000));
        series2.getData().add(new XYChart.Data("2018", 35407.15));
        series2.getData().add(new XYChart.Data("2019", 12000));


        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Budget");
        series3.getData().add(new XYChart.Data("2015", 25610.34));
        series3.getData().add(new XYChart.Data("2016", 21480.82));
        series3.getData().add(new XYChart.Data("2017", 10600));
        series3.getData().add(new XYChart.Data("2018", 35497.15));
        series3.getData().add(new XYChart.Data("2019", 12500));

        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series1, series2, series3);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Displays all expenses savings and budgets of the user on bar chart.
     */
    private void displayCompleteReport(Model model) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("All Summary Report");
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);

        final ObservableList<Budget> budgets = model.getFilteredBudgetList();
        final ObservableList<Expense> expenses = model.getFilteredExpenseList();

        bc.setTitle("Summary");
        yAxis.setLabel("Amount");
        xAxis.setLabel("Year");

        HashMap<Integer, InnerData> map = new HashMap<>();
        // convert expense data into innerData
        if (!expenses.isEmpty()) {
            for (int i = 0; i < expenses.size(); i++) {
                int year = expenses.get(i).getDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate().getYear(); // get year from expense
                InnerData data;

                int amount = expenses.get(i).getItem().getPrice().getAmount();
                if (map.containsKey(year)) {
                    // if year data exists
                    InnerData temp = map.get(year);
                    if (expenses.get(i).getItem().getName().toString().equals("Allowance")) {
                        temp.setSaving(temp.updateValue(temp.getSaving(), amount));
                    } else {
                        temp.setExpense(temp.updateValue(temp.getExpense(), amount));
                    }
                    map.put(year, temp);
                } else {
                    // year data does not exist
                    data = new InnerData(year);
                    if (expenses.get(i).getItem().getName().toString().equals("Allowance")) {
                        data.setSaving(amount);
                    } else {
                        data.setExpense(amount);
                    }
                    map.put(year, data);
                }
            }
        }
        // convert expense data to innerData
        if (!budgets.isEmpty()) {
            for (int i = 0; i < budgets.size(); i++) {
                int year = budgets.get(i).getStartDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate().getYear(); // get year from expense
                InnerData data;
                int amount = budgets.get(i).getPrice().getAmount();
                if (map.containsKey(year)) {
                    // if year data exists
                    InnerData temp = map.get(year);
                    temp.setBudget(temp.updateValue(temp.getExpense(), amount));
                    map.put(year, temp);
                } else {
                    // year data does not exist
                    data = new InnerData(year);
                    data.setBudget(amount);
                    map.put(year, data);
                }
            }
        }
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Saving");

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Expense");

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Budget");

        TreeMap<Integer, InnerData> tm = new TreeMap<>(map);
        for (Map.Entry<Integer, InnerData> entry : tm.entrySet()) {
            series1.getData().add(new XYChart.Data(entry.getKey().toString(),
                    entry.getValue().getSaving()));
            series2.getData().add(new XYChart.Data(entry.getKey().toString(),
                    entry.getValue().getExpense()));
            series3.getData().add(new XYChart.Data(entry.getKey().toString(),
                    entry.getValue().getBudget()));
        }

        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series1, series2, series3);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Chart will be displayed according to report display type.
     */
    private enum ReportDisplayType {
        MONTH, DAY, YEAR, ALL
    }

    /**
     * Data class for collecting data.
     */
    class InnerData {
        private int year;
        private int budget;
        private int expense;
        private int saving;

        public InnerData(int year) {
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

}
