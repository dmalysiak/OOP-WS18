package solutions.exercise12.refactored.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A representing a person.
 *
 * @since 1.0.0
 * */
@Getter
@Entity
@NoArgsConstructor //required by hibernate
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "person_attribute_id")
    private List<PersonAttribute> personAttributes = new ArrayList<>();
}
