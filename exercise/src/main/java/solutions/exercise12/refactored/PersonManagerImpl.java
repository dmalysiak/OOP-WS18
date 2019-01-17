package solutions.exercise12.refactored;

import solutions.exercise12.refactored.model.PersonAttribute;
import solutions.exercise12.refactored.model.Person;

/**
 * An interface providing management methods for person instances.
 * One could also favor the approach of creating subclasses of person for
 * different attribute handling.
 *
 * @since 1.0.0
 * */
public class PersonManagerImpl implements PersonManager{
    @Override
    public void addAttribute(Person person, String value, String stereotype) {
        person.getPersonAttributes().add(new PersonAttribute(value,stereotype));
    }
}
