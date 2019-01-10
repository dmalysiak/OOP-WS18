package solutions.exercise11;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "thing")
@Getter
@Setter
@EqualsAndHashCode
public class Thing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @Column
    protected String name;

    @Override
    public String toString()
    {
        return "Id:"+id+" name:"+name;
    }
}
