package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserPK implements Serializable {

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="account_id", nullable = false)
    private Long account;

    @SuppressWarnings("unused")
    public UserPK() { }

    public UserPK(Long id, Long account) {
        this.id = id;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(account).build();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UserPK && new EqualsBuilder()
                .append(id, ((UserPK) obj).id)
                .append(account, ((UserPK) obj).account)
                .build();
    }
}
