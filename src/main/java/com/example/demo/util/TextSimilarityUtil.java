package com.example.demo.util;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class TextSimilarityUtil {
    
    public static double similarity(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0.0;
        }
        
        if (text1.isEmpty() && text2.isEmpty()) {
            return 1.0;
        }
        
        if (text1.isEmpty() || text2.isEmpty()) {
            return 0.0;
        }
        
        // Convert to lowercase and split into words
        Set<String> words1 = new HashSet<>(List.of(text1.toLowerCase().split("\\s+")));
        Set<String> words2 = new HashSet<>(List.of(text2.toLowerCase().split("\\s+")));
        
        // Calculate Jaccard similarity
        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);
        
        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);
        
        return union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();
    }
}