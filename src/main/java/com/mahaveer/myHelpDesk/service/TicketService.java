package com.mahaveer.myHelpDesk.service;

import com.mahaveer.myHelpDesk.dto.TicketRequestDto;
import com.mahaveer.myHelpDesk.model.Ticket;
import org.springframework.stereotype.Service;


@Service
public interface TicketService {
    Ticket createTicket(TicketRequestDto dto);
}
