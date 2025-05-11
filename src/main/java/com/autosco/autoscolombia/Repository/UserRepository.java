package com.autosco.autoscolombia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autosco.autoscolombia.Model.User;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
        
}
