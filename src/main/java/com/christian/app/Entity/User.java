package com.christian.app.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class User implements Serializable {


    private static final long serialVersionUID = 7336592476967460883L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length =50)
    private String name;
    private String password;
    @Column(name="email",nullable = false,length = 50,unique = true)
    private String email;
    private Boolean enabled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
