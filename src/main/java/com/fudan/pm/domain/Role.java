package com.fudan.pm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity                     //实体类的注解，必须注明
@Table(name = "role")      //指定对应的数据库表
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class Role implements Serializable,GrantedAuthority {
    private static final long serialVersionUID = 1718168173096985194L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;


    /**
     * 和 Authority 多对多
     * 用户只和角色有直接对应，只通过角色添加权限
     * 和User中的authorities不同
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "role_authority",joinColumns = @JoinColumn(name="role_id"),inverseJoinColumns=@JoinColumn(name="authority_id"))
    private Set<Authority> roleAuthority = new HashSet<>();

    /**
     * 和 UserRole 一对多
     */
    @JsonIgnore
    @OneToMany(mappedBy = "role",cascade = CascadeType.MERGE, fetch = FetchType.LAZY)   //权限：更新； 懒加载
    private Set<UserRole> userRoles = new HashSet<>();

    public Role(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public Set<Authority> getRoleAuthority() {
        return roleAuthority;
    }

    public void setRoleAuthority(Set<Authority> roleAuthority) {
        this.roleAuthority = roleAuthority;
    }

    /**
     * from Interface GrantedAuthority
     * @return roleName
     */
    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
