package com.example.demo.enums;


import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

public enum RoleOfUser {
    ROLE_CLIENT,
    ROLE_AGENT,
    ROLE_ADMIN;



    public String getFullName() {
        return "ROLE_" + this.name();
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        return Sets.newHashSet(
                new SimpleGrantedAuthority("ROLE_" + this.name())
        );
    }

}
