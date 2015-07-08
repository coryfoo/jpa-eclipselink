package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Account implements Serializable {

    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;
}
