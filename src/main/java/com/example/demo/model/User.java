package com.
example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    private String username;

    @Size(max = 50)
    @Email
    private String email;
    @Size(max = 120)
    private String password;
    @Column(columnDefinition = "DATE")
    private Date birthday;
    private String first_time="not_change";

    @Column(nullable = true, unique = true)
    private String IDCard;

    @Column
    private String phoneNumber;

    @Column
    private String address;
    @Column(nullable = true, unique = true)
    private String immatriculation;
    @Column
    private String patente;


    @Column
    private String description;

    @Column
    private String image;

    // E-Code is a payment code validation for client
    @Column
    private Integer e_code;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private Client client;
    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private Agent agent;
    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private Admin admin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}