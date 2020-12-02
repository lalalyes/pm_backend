package com.fudan.pm.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity                     //实体类的注解，必须注明
@Table(name = "user")      //指定对应的数据库表
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "work_number")
    private String workNumber;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "introduction")
    private String introduction;

    public User(String username, String password, String workNumber) {
        this.username = username;
        this.password = password;
        this.workNumber = workNumber;
    }
    public User() {
    }
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    /**
     * user_role  一对多
     *  用户只和角色有直接对应，只通过角色添加权限
     */
    @OneToMany(mappedBy ="user",cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<GrantedAuthority> authoritySet = new HashSet<>();   //去重
        for (UserRole role: userRoles) {
            authoritySet.add(new SimpleGrantedAuthority(role.getRole().getRoleName()));
        }

        authorities.addAll(authoritySet);
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }
}
