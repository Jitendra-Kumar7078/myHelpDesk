package com.mahaveer.myHelpDesk.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaveer.myHelpDesk.service.GeminiService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Value("${spring.ai.google.genai.api-key}")
    private String apiKey;

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    @PostConstruct
    public void verifyKey() {
        System.out.println("Gemini API key loaded: " + apiKey);
    }

    @Override
    public String getAIResponse(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String requestJson = """
                    {
                      "contents": [
                        {
                          "parts": [
                            { "text": "%s" }
                          ]
                        }
                      ]
                    }
                    """.formatted(prompt.replace("\"", "\\\""));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            GEMINI_URL + apiKey,
                            entity,
                            String.class
                    );

            String body = response.getBody();
            System.out.println("RAW GEMINI RESPONSE = " + body);

            // ✅ PROPER JSON PARSING
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(body);

            String aiText = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            System.out.println("AI FINAL RESPONSE = " + aiText);
            return aiText;

        } catch (Exception e) {
            e.printStackTrace();
            return "OFFLINE: AI service is temporarily unavailable. Your ticket has been saved.";
        }
    }
}
