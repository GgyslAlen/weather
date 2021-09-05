package com.weathertech.weather.api.models;

import com.fasterxml.jackson.annotation.JsonRawValue;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;

@Table("roles")
public class Role {
    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("display_name")
    private String displayName;

    public Role(Long id, String name, String displayName) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
    }

    public Role() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}