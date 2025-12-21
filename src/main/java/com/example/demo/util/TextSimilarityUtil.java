package com.example.demo.util;

import java.util.*;

public class TextSimilarityUtil {

    public static double calculateSimilarity(String s1, String s2) {
        if (s1 == null || s2 == null) return 0.0;

        Set<String> w1 = new HashSet<>(List.of(s1.toLowerCase().split("\\s+")));
        Set<String> w2 = new HashSet<>(List.of(s2.toLowerCase().split("\\s+")));

        Set<String> intersection = new HashSet<>(w1);
        intersection.retainAll(w2);

        Set<String> union = new HashSet<>(w1);
        union.addAll(w2);

        return union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();
    }
}
