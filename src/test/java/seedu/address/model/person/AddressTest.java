package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EPiggyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EPiggy(null));
    }

    @Test
    public void constructor_invalidEPiggy_throwsIllegalArgumentException() {
        String invalidEPiggy = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EPiggy(invalidEPiggy));
    }

    @Test
    public void isValidEPiggy() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> EPiggy.isValidEPiggy(null));

        // invalid addresses
        assertFalse(EPiggy.isValidEPiggy("")); // empty string
        assertFalse(EPiggy.isValidEPiggy(" ")); // spaces only

        // valid addresses
        assertTrue(EPiggy.isValidEPiggy("Blk 456, Den Road, #01-355"));
        assertTrue(EPiggy.isValidEPiggy("-")); // one character
        assertTrue(EPiggy.isValidEPiggy("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
