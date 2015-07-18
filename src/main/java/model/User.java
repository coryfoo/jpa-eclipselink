package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(UserPK.class)
public class User implements Serializable {

    @Id
    public Long id;

    @Id
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    public Account account;

    @Column(name = "email")
    public String email;

    @Version
    public int beanVersion;
}
