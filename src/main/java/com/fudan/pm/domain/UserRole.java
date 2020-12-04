package com.fudan.pm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -4113131686781592172L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name="user_id")   //默认为user中的主键
    private User user;

    @JsonIgnore
    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name="role_id")
    private Role role;


    public UserRole(){}

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
