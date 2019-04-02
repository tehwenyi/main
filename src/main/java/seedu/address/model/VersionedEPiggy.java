package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.ReadOnlyEPiggy;

/**
 * {@code EPiggy} that keeps track of its own history.
 */
public class VersionedEPiggy extends EPiggy {

    private final List<ReadOnlyEPiggy> ePiggyStateList;
    private int currentStatePointer;

    public VersionedEPiggy(ReadOnlyEPiggy initialState) {
        super(initialState);

        ePiggyStateList = new ArrayList<>();
        ePiggyStateList.add(new EPiggy(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code EPiggy} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        ePiggyStateList.add(new EPiggy(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        ePiggyStateList.subList(currentStatePointer + 1, ePiggyStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(ePiggyStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(ePiggyStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < ePiggyStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedEPiggy)) {
            return false;
        }

        VersionedEPiggy otherVersionedAddressBook = (VersionedEPiggy) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                && ePiggyStateList.equals(otherVersionedAddressBook.ePiggyStateList)
                && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of ePiggyState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of ePiggyState list, unable to redo.");
        }
    }
}
