package seedu.address.model.epiggy.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//@@author kev-inc

class CostTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Cost(null));
    }

    @Test
    public void constructor_emptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Cost(""));
    }

    @Test
    public void constructor_validAmounts_success() {
        assertEquals(0.01, new Cost("0.01").getAmount());
        assertEquals(1, new Cost("1").getAmount());
        assertEquals(1, new Cost("1.00").getAmount());
        assertEquals(999999.99, new Cost("999999.99").getAmount());
    }

    @Test
    public void constructor_invalidAmounts_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Cost("0"));
        assertThrows(IllegalArgumentException.class, () -> new Cost("0.00"));
        assertThrows(IllegalArgumentException.class, () -> new Cost("0.001"));
        assertThrows(IllegalArgumentException.class, () -> new Cost("1.001"));
        assertThrows(IllegalArgumentException.class, () -> new Cost("999999.999"));
        assertThrows(IllegalArgumentException.class, () -> new Cost("1000000"));
        assertThrows(IllegalArgumentException.class, () -> new Cost("a"));
    }
}
