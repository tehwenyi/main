package seedu.address.storage.epiggy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EPiggy;
import seedu.address.model.ReadOnlyEPiggy;

/**
 * Represents a storage for {@link EPiggy}.
 */
public interface EPiggyStorage {
    Path getEPiggyFilePath();

    Optional<ReadOnlyEPiggy> readEPiggy() throws DataConversionException, IOException;

    Optional<ReadOnlyEPiggy> readEPiggy(Path filePath) throws DataConversionException, IOException;

    void saveEPiggy(ReadOnlyEPiggy ePiggy) throws IOException;

    void saveEPiggy(ReadOnlyEPiggy ePiggy, Path filePath) throws IOException;

    void backupEPiggy(ReadOnlyEPiggy ePiggy) throws IOException;
}
