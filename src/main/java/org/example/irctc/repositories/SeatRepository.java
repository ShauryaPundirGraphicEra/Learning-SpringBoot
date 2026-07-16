package org.example.irctc.repositories;

import org.example.irctc.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<Seat> findByTrainTrainIdAndSeatNumber(
            Long trainId,
            int seatNumber);
}
