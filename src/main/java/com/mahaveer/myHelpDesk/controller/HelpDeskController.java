package com.mahaveer.myHelpDesk.controller;


import com.mahaveer.myHelpDesk.dto.TicketRequestDto;
import com.mahaveer.myHelpDesk.model.Ticket;
import com.mahaveer.myHelpDesk.service.TicketService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/helpdesk")
public class HelpDeskController {
    private final TicketService ticketService;


    public HelpDeskController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/ask")
    public Ticket askQuestion(@RequestBody TicketRequestDto dto){
        return ticketService.createTicket(dto);
    }
}
