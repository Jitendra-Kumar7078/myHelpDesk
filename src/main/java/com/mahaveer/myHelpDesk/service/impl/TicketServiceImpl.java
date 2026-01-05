package com.mahaveer.myHelpDesk.service.impl;

import com.mahaveer.myHelpDesk.dto.ChatMessageDto;
import com.mahaveer.myHelpDesk.dto.TicketRequestDto;
import com.mahaveer.myHelpDesk.model.Conversation;
import com.mahaveer.myHelpDesk.model.Ticket;
import com.mahaveer.myHelpDesk.repository.ConversationRepository;
import com.mahaveer.myHelpDesk.repository.TicketRepository;
import com.mahaveer.myHelpDesk.service.GeminiService;
import com.mahaveer.myHelpDesk.service.TicketService;
import com.mahaveer.myHelpDesk.util.CategoryPriorityUtil;
import com.mahaveer.myHelpDesk.util.PromptUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ConversationRepository conversationRepository;
    private final GeminiService geminiService;

    public TicketServiceImpl(
            TicketRepository ticketRepository,
            ConversationRepository conversationRepository,
            GeminiService geminiService
    ) {
        this.ticketRepository = ticketRepository;
        this.conversationRepository = conversationRepository;
        this.geminiService = geminiService;
    }

    @Override
    public Ticket createTicket(TicketRequestDto dto) {

        // 1️⃣ Category & Priority
        String category = CategoryPriorityUtil.getCategory(dto.getQuery());
        String priority = CategoryPriorityUtil.getPriority(dto.getQuery());

        // 2️⃣ AI Response
        String aiResponse = geminiService.getAIResponse(dto.getQuery());

        // 3️⃣ Save Ticket
        Ticket ticket = new Ticket();
        ticket.setUserName(dto.getUserName());
        ticket.setUserQuery(dto.getQuery());
        ticket.setCategory(category);
        ticket.setPriority(priority);
        ticket.setAiResponse(aiResponse);
        ticket.setOfflineMode(aiResponse.startsWith("OFFLINE"));

        Ticket savedTicket = ticketRepository.save(ticket);

        // 4️⃣ Save USER Conversation
        Conversation userConversation = new Conversation();
        userConversation.setTicketId(savedTicket.getId());
        userConversation.setMessage(dto.getQuery());
        userConversation.setSender("USER");
        userConversation.setCreatedAt(LocalDateTime.now());

        conversationRepository.save(userConversation);

        // 5️⃣ Save AI Conversation
        Conversation aiConversation = new Conversation();
        aiConversation.setTicketId(savedTicket.getId());
        aiConversation.setMessage(aiResponse);
        aiConversation.setSender("AI");
        aiConversation.setCreatedAt(LocalDateTime.now());

        conversationRepository.save(aiConversation);

        return savedTicket;
    }

    @Override
    public String addMessageToTicket(ChatMessageDto dto) {

        // 1️⃣ Save USER message
        Conversation userConversation = new Conversation();
        userConversation.setTicketId(dto.getTicketId());
        userConversation.setMessage(dto.getMessage());
        userConversation.setSender("USER");
        userConversation.setCreatedAt(LocalDateTime.now());

        conversationRepository.save(userConversation);

        // 2️⃣ Call AI
        String aiResponse = geminiService.getAIResponse(dto.getMessage());

        // 3️⃣ Save AI response
        Conversation aiConversation = new Conversation();
        aiConversation.setTicketId(dto.getTicketId());
        aiConversation.setMessage(aiResponse);
        aiConversation.setSender("AI");
        aiConversation.setCreatedAt(LocalDateTime.now());

        conversationRepository.save(aiConversation);

        return aiResponse;
    }

    @Override
    public String addMessageWithContext(ChatMessageDto dto) {

        // 1️⃣ Fetch previous conversation
        List<Conversation> history =
                conversationRepository.findByTicketIdOrderByCreatedAtAsc(dto.getTicketId());

        // 2️⃣ Build prompt with full context
        String prompt = PromptUtil.buildPrompt(history, dto.getMessage());

        // 3️⃣ Save USER message
        Conversation userConversation = new Conversation();
        userConversation.setTicketId(dto.getTicketId());
        userConversation.setMessage(dto.getMessage());
        userConversation.setSender("USER");
        userConversation.setCreatedAt(LocalDateTime.now());
        conversationRepository.save(userConversation);

        // 4️⃣ Call AI with context-aware prompt
        String aiResponse = geminiService.getAIResponse(prompt);

        // 5️⃣ Save AI response
        Conversation aiConversation = new Conversation();
        aiConversation.setTicketId(dto.getTicketId());
        aiConversation.setMessage(aiResponse);
        aiConversation.setSender("AI");
        aiConversation.setCreatedAt(LocalDateTime.now());
        conversationRepository.save(aiConversation);

        return aiResponse;
    }

}
