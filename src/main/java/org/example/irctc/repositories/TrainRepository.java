package org.example.irctc.repositories;

import org.example.irctc.entities.Train;
import org.example.irctc.entities.User;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface TrainRepository extends CrudRepository<Train, Long> {
   Optional<Train> findById(Long id);

    List<Train> findBySourceAndDestination(String source, String destination);

}
