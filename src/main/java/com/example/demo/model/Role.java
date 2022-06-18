package com.example.demo.model;

import com.example.demo.enums.RoleOfUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Data
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleOfUser name;
    public Role() {
    }
    public Role(RoleOfUser name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public RoleOfUser getName() {
        return name;
    }
    public void setName(RoleOfUser name) {
        this.name = name;
    }
}