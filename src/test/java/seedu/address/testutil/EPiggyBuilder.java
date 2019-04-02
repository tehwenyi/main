package seedu.address.testutil;

import seedu.address.model.EPiggy;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code EPiggy ab = new EPiggyBuilder().withPerson("John", "Doe").build();}
 */
public class EPiggyBuilder {

    private EPiggy EPiggy;

    public EPiggyBuilder() {
        EPiggy = new EPiggy();
    }

    public EPiggyBuilder(EPiggy EPiggy) {
        this.EPiggy = EPiggy;
    }

    /**
     * Adds a new {@code Person} to the {@code EPiggy} that we are building.
     */
    public EPiggyBuilder withPerson(Person person) {
        EPiggy.addPerson(person);
        return this;
    }

    public EPiggy build() {
        return EPiggy;
    }
}
