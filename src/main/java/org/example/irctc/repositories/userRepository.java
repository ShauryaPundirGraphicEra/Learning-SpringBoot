package org.example.irctc.repositories;

import org.example.irctc.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface userRepository extends CrudRepository<User, Long> {

}
