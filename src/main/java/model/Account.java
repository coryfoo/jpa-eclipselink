package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Version
    private int beanVersion;



    @Override
    public String toString() {
        return "Account{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", beanVersion=" + getBeanVersion() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeanVersion() {
        return beanVersion;
    }
}
