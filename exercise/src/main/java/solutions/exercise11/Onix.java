package solutions.exercise11;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Onix extends Thing {
    public Onix()
    {
        name="Onix";
    }
}
