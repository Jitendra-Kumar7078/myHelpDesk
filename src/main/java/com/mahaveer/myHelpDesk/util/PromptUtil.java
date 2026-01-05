package com.mahaveer.myHelpDesk.util;

import com.mahaveer.myHelpDesk.model.Conversation;

import java.util.List;

public class PromptUtil {

    public static String buildPrompt(List<Conversation> history, String newMessage) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
You are a polite and professional AI Help Desk assistant.
Always greet the user politely and respond clearly.

Conversation history:
""");

        for (Conversation c : history) {
            prompt.append(c.getSender())
                    .append(": ")
                    .append(c.getMessage())
                    .append("\n");
        }

        prompt.append("\nUSER: ").append(newMessage);
        prompt.append("\nAI:");

        return prompt.toString();
    }
}
