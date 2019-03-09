package seedu.address.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * The Summary Window. Provides summary chart to the user.
 */
public class SummaryWindow implements Initializable {

    public static final String SUMMARY_SCREEN_FXML = "/main/resources/view/ui/SummaryWindow.fxml";

    FXMLLoader loader = new FXMLLoader(getClass().getResource(SUMMARY_SCREEN_FXML));

    @FXML
    private TextArea command_text_area;
    @FXML
    private AreaChart<?, ?> Expense_area_chart;
    @FXML
    private BorderPane default_expense_chart;

    /**
     * Display Summary.
     */
    public static void display() {
        SummaryWindow window = new SummaryWindow();
        window.initialize(null, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(1, 300));
        series.getData().add(new XYChart.Data(2, 200));
        series.getData().add(new XYChart.Data(3, 400));
        series.getData().add(new XYChart.Data(4, 500));

        XYChart.Series series2 = new XYChart.Series();
        series2.getData().add(new XYChart.Data(1, 1000));
        series2.getData().add(new XYChart.Data(2, 2000));
        series2.getData().add(new XYChart.Data(3, 4000));
        series2.getData().add(new XYChart.Data(4, 5000));

        Expense_area_chart.getData().addAll(series, series2);


    }
}
