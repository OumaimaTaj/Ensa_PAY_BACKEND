package com.example.demo.repository;

import com.example.demo.enums.RoleOfUser;
import com.example.demo.model.Agent;
import com.example.demo.model.Client;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    List<Client> findAllByAgent(Agent agent);
    @Query( "select u from User u inner join u.roles r where r.name in :roles" )
    List<User> findBySpecificRoles(@Param("roles") RoleOfUser roles);


//    @Query("select u from User u join  Client c on c.id=u.id ")

}
