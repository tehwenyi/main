package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.epiggy.TypicalExpenses.getTypicalEPiggy;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.EPiggy;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.UserPrefs;
import seedu.address.storage.epiggy.JsonEPiggyStorage;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonEPiggyStorage addressBookStorage = new JsonEPiggyStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Ignore
    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonEPiggyStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonEPiggyStorageTest} class.
         */
        EPiggy original = getTypicalEPiggy();
        storageManager.saveEPiggy(original);
        ReadOnlyEPiggy retrieved = storageManager.readEPiggy().get();
        assertEquals(original, new EPiggy(retrieved));
    }

    @Test
    public void getEPiggyFilePath() {
        assertNotNull(storageManager.getEPiggyFilePath());
    }

}
