package com.crio.qcharm.ds;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class PatternSearchAlgorithm {

  public static List<Integer> stringSearch(String text, String pattern, boolean efficient, boolean isLinkedList) {
    if (efficient) {
      return kmpStringSearch(text, pattern, isLinkedList);
    }
    return naiveStringSearch(text, pattern, isLinkedList);
  }

  private static int[] computeLPSArray(String pattern) {
    int m = pattern.length();
    int[] lps = new int[m];
    lps[0] = 0;
    int i = 1;
    int j = 0;
    while (i < m) {
      if (pattern.charAt(i) == pattern.charAt(j)) {
        j++;
        lps[i] = j;
        i++;
      } else {
        if (j != 0) {
          j = lps[j-1];
        }
        else {
          i++;
        }
      }
    }
    return lps;
  }

  private static List<Integer> kmpStringSearch(String text, String pattern, boolean isLinkedList) {
    List<Integer> res;
    if (isLinkedList) {
      res = new LinkedList<>();
    } else {
      res = new ArrayList<>();
    }
    int m = pattern.length();
    int n = text.length();
    if (m == 0) {
      return res;
    }
    int[] lps = computeLPSArray(pattern);
    int i = 0;
    int j = 0;
    while (i < n) {
      if (text.charAt(i) == pattern.charAt(j)) {
        i++;
        j++;
      }
      if (j == m) {
        res.add(i - j);
        j = lps[j - 1];
      } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
        if (j != 0) {
          j = lps[j - 1];
        } else {
          i++;
        }
      }
    }
    return res;
  }

  private static List<Integer> naiveStringSearch(String text, String pattern, boolean isLinkedList) {
    int m = pattern.length();
    int n = text.length();
    List<Integer> res;
    if (isLinkedList) {
      res = new LinkedList<>();
    } else {
      res = new ArrayList<>();
    }
    if (m == 0) {
      return res;
    }
    for (int i = 0; i<=n-m; i++) {
      int j;
      for(j = 0; j<m; j++) {
        if(text.charAt(i+j) != pattern.charAt(j)) {
          break;
        }
      }
      if(j == m) {
        res.add(i);
      }
    }
    return res;
  }
}