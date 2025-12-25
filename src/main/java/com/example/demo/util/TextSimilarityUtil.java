package com.example.demo.util;

import java.util.*;

public class TextSimilarityUtil {

    public static double similarity(String s1, String s2) {
        if (s1 == null || s2 == null) return 0.0;

        Set<String> a = new HashSet<>(Arrays.asList(s1.toLowerCase().split("\\s+")));
        Set<String> b = new HashSet<>(Arrays.asList(s2.toLowerCase().split("\\s+")));

        Set<String> intersection = new HashSet<>(a);
        intersection.retainAll(b);

        Set<String> union = new HashSet<>(a);
        union.addAll(b);

        return union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();
    }
}
