package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EPiggy;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.person.Person;

/**
 * An Immutable EPiggy that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableEPiggy {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEPiggy} with the given persons.
     */
    @JsonCreator
    public JsonSerializableEPiggy(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyEPiggy} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEPiggy}.
     */
    public JsonSerializableEPiggy(ReadOnlyEPiggy source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code EPiggy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EPiggy toModelType() throws IllegalValueException {
        EPiggy ePiggy = new EPiggy();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (ePiggy.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            ePiggy.addPerson(person);
        }
        return ePiggy;
    }

}
