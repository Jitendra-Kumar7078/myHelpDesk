package com.mahaveer.myHelpDesk.service.impl;

import com.mahaveer.myHelpDesk.service.GeminiService;
import org.springframework.stereotype.Service;

@Service
public class GeminiServiceImpl implements GeminiService {
    @Override
    public String getAIResponse(String prompt) {
        try {
            return "Thank you for contacting Help Desk. We are reviewing your issue.";
        } catch (Exception e){
            return "OFFLINE: our system is currently unavailable. Your ticket is saved.";
        }
    }
}
