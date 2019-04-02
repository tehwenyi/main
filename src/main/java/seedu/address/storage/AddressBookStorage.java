package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EPiggy;
import seedu.address.model.ReadOnlyEPiggy;

/**
 * Represents a storage for {@link EPiggy}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEPiggyFilePath();

    /**
     * Returns EPiggy data as a {@link ReadOnlyEPiggy}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEPiggy> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getEPiggyFilePath()
     */
    Optional<ReadOnlyEPiggy> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEPiggy} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyEPiggy addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyEPiggy)
     */
    void saveAddressBook(ReadOnlyEPiggy addressBook, Path filePath) throws IOException;

    /**
     * Creates a backup file for {@link ReadOnlyEPiggy}
     * @param addressBook
     * @throws IOException
     */
    void backupAddressBook(ReadOnlyEPiggy addressBook) throws IOException;

}
