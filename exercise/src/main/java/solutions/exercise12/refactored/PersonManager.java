package solutions.exercise12.refactored;

import solutions.exercise12.refactored.model.Person;

/**
 * An interface providing management methods for person instances.
 * One could also favor the approach of creating subclasses of person for
 * different attribute handling.
 *
 * @since 1.0.0
 * */
public interface PersonManager {
    void addAttribute(Person person, String value, String Stereotype);
}
