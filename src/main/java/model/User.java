package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(UserPK.class)
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Id
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "email")
    private String email;

    @Version
    private int beanVersion;

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", account=" + getAccount() +
                ", email='" + getEmail() + '\'' +
                ", beanVersion=" + getBeanVersion() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBeanVersion() {
        return beanVersion;
    }
}
