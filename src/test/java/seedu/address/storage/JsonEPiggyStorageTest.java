package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalEPiggyBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EPiggy;

import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.storage.epiggy.JsonEPiggyStorage;

public class JsonEPiggyStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEPiggyStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEPiggyBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEPiggyBook(null);
    }

    private java.util.Optional<ReadOnlyEPiggy> readEPiggyBook(String filePath) throws Exception {
        return new JsonEPiggyStorage(Paths.get(filePath)).readEPiggy(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEPiggyBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEPiggyBook("notJsonFormatEPiggyBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readEPiggyBook_invalidPersonEPiggyBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEPiggyBook("invalidPersonEPiggyBook.json");
    }

    @Test
    public void readEPiggyBook_invalidAndValidPersonEPiggyBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEPiggyBook("invalidAndValidPersonEPiggyBook.json");
    }

    @Ignore
    @Test
    public void readAndSaveEPiggyBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempEPiggyBook.json");
        EPiggy original = getTypicalEPiggyBook();
        JsonEPiggyStorage jsonEPiggyBookStorage = new JsonEPiggyStorage(filePath);

        // Save in new file and read back
        jsonEPiggyBookStorage.saveEPiggy(original, filePath);
        ReadOnlyEPiggy readBack = jsonEPiggyBookStorage.readEPiggy(filePath).get();
        assertEquals(original, new EPiggy(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonEPiggyBookStorage.saveEPiggy(original, filePath);
        readBack = jsonEPiggyBookStorage.readEPiggy(filePath).get();
        assertEquals(original, new EPiggy(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonEPiggyBookStorage.saveEPiggy(original); // file path not specified
        readBack = jsonEPiggyBookStorage.readEPiggy().get(); // file path not specified
        assertEquals(original, new EPiggy(readBack));

    }

    @Test
    public void saveEPiggyBook_nullEPiggyBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEPiggyBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code ePiggy} at the specified {@code filePath}.
     */
    private void saveEPiggyBook(ReadOnlyEPiggy ePiggy, String filePath) {
        try {
            new JsonEPiggyStorage(Paths.get(filePath))
                    .saveEPiggy(ePiggy, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEPiggyBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEPiggyBook(new EPiggy(), null);
    }
}
