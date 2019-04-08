package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.epiggy.TypicalExpenses.BOWLING;
import static seedu.address.testutil.epiggy.TypicalExpenses.IPHONE;
import static seedu.address.testutil.epiggy.TypicalExpenses.getTypicalEPiggy;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EPiggy;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Item;
import seedu.address.model.expense.Name;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.tag.Tag;
import seedu.address.storage.epiggy.JsonEPiggyStorage;

@Ignore
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
        EPiggy original = getTypicalEPiggy();
        JsonEPiggyStorage jsonEPiggyBookStorage = new JsonEPiggyStorage(filePath);

        // Save in new file and read back
        jsonEPiggyBookStorage.saveEPiggy(original, filePath);
        ReadOnlyEPiggy readBack = jsonEPiggyBookStorage.readEPiggy(filePath).get();
        assertEquals(original, new EPiggy(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExpense(IPHONE);
        original.removeExpense(BOWLING);
        jsonEPiggyBookStorage.saveEPiggy(original, filePath);
        readBack = jsonEPiggyBookStorage.readEPiggy(filePath).get();
        assertEquals(original, new EPiggy(readBack));

        // Save and read without specifying file path
        original.addExpense(new Expense(new Item(new Name("KFC"), new Cost("13.00"),
                getTagSet("food", "dinner")), new Date("26/04/2019")));
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

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
