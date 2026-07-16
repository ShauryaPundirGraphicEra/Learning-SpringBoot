package org.example.irctc.repositories;

import org.example.irctc.entities.Train;
import org.example.irctc.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface TrainRepository extends CrudRepository<Train, Long> {
   Optional<Train> findById(Long id);

   // List<Train> findBySourceAndDestination(String source, String destination);
    @Query("SELECT t FROM trains t WHERE :source MEMBER OF t.stations AND :destination MEMBER OF t.stations")
    List<Train> findBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);
}
