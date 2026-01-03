package com.mahaveer.myHelpDesk.service;

import org.springframework.stereotype.Service;

@Service
public interface GeminiService {
    String getAIResponse(String prompt);
}
