package com.fudan.pm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name="authority")
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = -8974777274465208640L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id")
    private Long authority_id;

    @Column(name = "auth_name",unique = true)
    private String authName;


    //role_authority
    @JsonIgnore
    @ManyToMany(mappedBy = "roleAuthority")  //,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Role> roles;

    public Authority(){}

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Long getId() {
        return authority_id;
    }

    /**
     * from Interface GrantedAuthority
     * @return authName
     */
    @Override
    public String getAuthority() {
        return getAuthName();
    }

}
