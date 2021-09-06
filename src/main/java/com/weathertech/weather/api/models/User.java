package com.weathertech.weather.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("users")
public class User {

    @Id
    @Column("id")
    private Long id;

    @Column("login")
    private String login;

    @JsonIgnore
    @Column("password")
    private String password;

    @Column("fullname")
    private String fullname;

    @Column("role_id")
    private Long roleId;

    @Column("create_time")
    private LocalDateTime createTime;

    @Transient
    private String roleName;

    public User() {

    }

    public User(Long id, String login, String password, String fullname, Long roleId, String roleName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullname = fullname;
        this.roleId = roleId;
        this.createTime = LocalDateTime.now();
        this.roleName = roleName;
    }

    public User(Long id, String login, String password, String fullname, Long roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullname = fullname;
        this.roleId = roleId;
        this.createTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
