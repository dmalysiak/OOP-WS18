package solutions.exercise11;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Pikachu extends Thing {
    public Pikachu()
    {
        name="Pikachu";
    }
}
