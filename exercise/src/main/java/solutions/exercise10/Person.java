package solutions.exercise10;

import lombok.*;

import javax.persistence.Entity;

@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Person {
    @NonNull
    String name;
    @EqualsAndHashCode.Exclude Integer age = 10;
}
