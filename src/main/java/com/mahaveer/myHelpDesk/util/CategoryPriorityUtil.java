package com.mahaveer.myHelpDesk.util;

public class CategoryPriorityUtil {

    public static String getCategory(String query){
        if (query.contains("payment")) return "PAYMENT";
        if (query.contains("login")) return "AUTH";
        if (query.contains("error")) return "TECHNICAL";
        return "GENERAL";
    }
    public static String getPriority(String query){
        if (query.contains("urgent")) return "HIGH";
        if (query.contains("slow")) return "MEDIUM";
        return "LOW";
    }
}
