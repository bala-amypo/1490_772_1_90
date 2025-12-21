package com.example.demo.util;

import java.util.HashSet;
import java.util.Set;

public class TextSimilarityUtil {
    
    public static double similarity(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return 0.0;
        }
        
        String[] words1 = s1.toLowerCase().split("\\s+");
        String[] words2 = s2.toLowerCase().split("\\s+");
        
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        
        for (String word : words1) {
            set1.add(word);
        }
        
        for (String word : words2) {
            set2.add(word);
        }
        
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        
        if (union.isEmpty()) {
            return 0.0;
        }
        
        return (double) intersection.size() / union.size();
    }
}