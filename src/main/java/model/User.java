package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @EmbeddedId
    public UserPK id;

    @MapsId("account")
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    public Account account;

    @Column(name = "email")
    public String email;

}
