package guitests.guihandles.epiggy;

import java.net.URL;

import guitests.GuiRobot;
import guitests.guihandles.StageHandle;
import guitests.guihandles.WebViewUtil;
import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
//@@author yunjun199321
public class HelpWindowHandle extends StageHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    private static final String HELP_WINDOW_BROWSER_ID = "#browser";

    public HelpWindowHandle(Stage helpWindowStage) {
        super(helpWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(HELP_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(HELP_WINDOW_BROWSER_ID));
    }
}
