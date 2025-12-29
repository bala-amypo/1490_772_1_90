package com.example.demo.util;

import java.util.*;

public final class TextSimilarityUtil {
    private TextSimilarityUtil() {}
    public static double similarity(String a, String b) {
        if (a == null || b == null) return 0.0;
        String as = a.trim();
        String bs = b.trim();
        if (as.isEmpty() || bs.isEmpty()) return 0.0;
        Set<String> sa = new HashSet<>();
        for (String s : as.toLowerCase().split("\\s+")) if (!s.isBlank()) sa.add(s);
        Set<String> sb = new HashSet<>();
        for (String s : bs.toLowerCase().split("\\s+")) if (!s.isBlank()) sb.add(s);
        if (sa.isEmpty() || sb.isEmpty()) return 0.0;
        Set<String> inter = new HashSet<>(sa);
        inter.retainAll(sb);
        Set<String> union = new HashSet<>(sa);
        union.addAll(sb);
        return union.isEmpty() ? 0.0 : ((double) inter.size()) / union.size();
    }
}