package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Version
    public int beanVersion;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", beanVersion=" + beanVersion +
                '}';
    }
}
