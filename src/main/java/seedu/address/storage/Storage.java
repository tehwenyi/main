package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

import seedu.address.storage.epiggy.EPiggyStorage;

/**
 * API of the Storage component
 */
public interface Storage extends EPiggyStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getEPiggyFilePath();

    @Override
    Optional<ReadOnlyEPiggy> readEPiggy() throws DataConversionException, IOException;

    @Override
    void saveEPiggy(ReadOnlyEPiggy ePiggy) throws IOException;


}
