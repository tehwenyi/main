package seedu.address.ui;

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

/**
 * The Summary Window. Provides summary and chart to the user.
 */
public class ReportWindow {
    /**
     * Display daily summary on area chart.
     */
    public void displayDailyReport() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Daily Summary");
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
    public void displayMonthlyReport() {
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
    public void displayExpensePercentageReport() {
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
    public void dispalyYearlySummary() {
        Stage window = new Stage();
        window.setTitle("Bar Chart Sample");
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Yearly Summary");
        xAxis.setLabel("Expense");
//        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Year");

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
}
