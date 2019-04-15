package guitests.guihandles.epiggy;

import guitests.GuiRobot;
import guitests.guihandles.StageHandle;
import javafx.stage.Stage;

/**
 * This class is used to handle report window.
 */
//@@author yunjun199321
public class ReportWindowHandle extends StageHandle {
    public static final String REPORT_WINDOW_TITLE = "Report";

    /**
     * Report window constructor.
     * @param reportWindowStage Stage of window.
     */
    public ReportWindowHandle(Stage reportWindowStage) {
        super(reportWindowStage);
    }

    /**
     * Returns true if a report window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(REPORT_WINDOW_TITLE);
    }
}
