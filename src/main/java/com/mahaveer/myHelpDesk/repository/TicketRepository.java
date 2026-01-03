package com.mahaveer.myHelpDesk.repository;

import com.mahaveer.myHelpDesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
