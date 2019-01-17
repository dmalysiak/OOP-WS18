package solutions.exercise12.refactored.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A class representing an abstract attribute.
 *
 * @since 1.0.0
 * */
@Entity
@RequiredArgsConstructor
@NoArgsConstructor //required by hibernate
@Getter
public class PersonAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    String value;
    @NonNull
    String stereotype;
}
