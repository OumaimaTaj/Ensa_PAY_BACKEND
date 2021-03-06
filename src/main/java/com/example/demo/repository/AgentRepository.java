package com.example.demo.repository;

import com.example.demo.enums.RoleOfUser;
import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    @Query( "select u from User u inner join u.roles r where r.name in :roles" )
    List<User> findBySpecificRoles(@Param("roles") RoleOfUser roles);
    Agent findByUser(User user);
    @Query( "select c,r from Agent c inner join c.user r where r.id in :user_id" )
    Optional<Agent> findByUser(@Param("user_id") Long id);

}
