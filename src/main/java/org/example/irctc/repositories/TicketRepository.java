package org.example.irctc.repositories;

import org.example.irctc.entities.Ticket;
import org.example.irctc.entities.Train;
import org.example.irctc.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Optional<Ticket> findById(String id);

    List<Ticket> findByUser(User user);
    List<Ticket> findByUserUserId(Long userId);
    Optional<Ticket> findByTicketIdAndUserUserId(Long ticketId, Long userId);

    void deleteById(Long id);
}
