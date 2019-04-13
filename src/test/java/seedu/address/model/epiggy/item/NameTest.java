package seedu.address.model.epiggy.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }


    @Test
    public void isValidName() {

        // empty name
        assertFalse(Name.isValidName(""));
        assertFalse(Name.isValidName(" ")); // blank name

        // invalid name
        assertFalse(Name.isValidName("Ben & Jerry's"));
        assertFalse(Name.isValidName("Hello_panda"));
        assertFalse(Name.isValidName("Shazam!"));
        assertFalse(Name.isValidName("@pple"));
        assertFalse(Name.isValidName("#hashtag"));
        assertFalse(Name.isValidName("A very very very very very very very very long name"));

        // valid names
        assertTrue(Name.isValidName("1"));
        assertTrue(Name.isValidName("a"));
        assertTrue(Name.isValidName("Apple iPad"));
        assertTrue(Name.isValidName("A very very very very very very very long name"));


    }
}
