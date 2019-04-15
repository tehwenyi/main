package seedu.address.testutil;

import seedu.address.model.EPiggy;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building ePiggy objects.
 * Example usage: <br>
 *     {@code ePiggy ab = new EPiggyBuilder().withPerson("John", "Doe").build();}
 */
public class EPiggyBuilder {

    private EPiggy ePiggy;

    public EPiggyBuilder() {
        ePiggy = new EPiggy();
    }

    public EPiggyBuilder(EPiggy ePiggy) {
        this.ePiggy = ePiggy;
    }

    /**
     * Adds a new {@code Person} to the {@code ePiggy} that we are building.
     */
    public EPiggyBuilder withPerson(Person person) {
        ePiggy.addPerson(person);
        return this;
    }

    public EPiggy build() {
        return ePiggy;
    }
}
