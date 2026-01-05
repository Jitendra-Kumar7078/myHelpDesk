package com.mahaveer.myHelpDesk.dto;

public class ChatMessageDto {

    private Long ticketId;
    private String message;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
