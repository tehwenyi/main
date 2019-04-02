package seedu.address.testutil;

import seedu.address.model.EPiggy;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code EPiggy ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private EPiggy EPiggy;

    public AddressBookBuilder() {
        EPiggy = new EPiggy();
    }

    public AddressBookBuilder(EPiggy EPiggy) {
        this.EPiggy = EPiggy;
    }

    /**
     * Adds a new {@code Person} to the {@code EPiggy} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        EPiggy.addPerson(person);
        return this;
    }

    public EPiggy build() {
        return EPiggy;
    }
}
