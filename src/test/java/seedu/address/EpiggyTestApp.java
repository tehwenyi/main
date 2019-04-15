package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EPiggy;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.UserPrefs;

import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.epiggy.JsonEPiggyStorage;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
//@@author yunjun199321
public class EpiggyTestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil
            .getFilePathInSandboxFolder("sampleEpiggyData.json");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing_epiggy.json");
    protected Supplier<ReadOnlyEPiggy> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public EpiggyTestApp() {
    }

    public EpiggyTestApp(Supplier<ReadOnlyEPiggy> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            JsonEPiggyStorage jsonEPiggyStorage = new JsonEPiggyStorage(saveFileLocation);
            try {
                jsonEPiggyStorage.saveEPiggy(initialDataSupplier.get());
            } catch (IOException ioe) {
                throw new AssertionError(ioe);
            }
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.setGuiSettings(new GuiSettings(1400.0, 700.0, (int) x, (int) y));
        userPrefs.setEPiggyFilePath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public EPiggy readStorageEPiggy() {
        try {
            return new EPiggy(storage.readEPiggy().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the EPiggy format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getEPiggyFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getEPiggy()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredPersonList());
        return copy;
    }

    /**
     * Returns the mode after executed the command.
     */
    public Model getModelAfterExecution() {
        return this.model;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

