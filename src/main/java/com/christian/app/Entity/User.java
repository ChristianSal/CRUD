package com.christian.app.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
@NoArgsConstructor

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

    private String imagePath;

    @Transient
    private String imageUrl;

    private String docPath;

    @Transient
    private String docUrl;


}
