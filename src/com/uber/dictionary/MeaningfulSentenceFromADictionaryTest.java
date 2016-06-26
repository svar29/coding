package com.uber.dictionary;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeaningfulSentenceFromADictionaryTest {

    @Test
    public void testMeaningFulSentenceFromADictionaryTest() {

        Set<String> dictionary = new HashSet<String>() {{
            add("rag");
            add("on");
            add("ball");
            add("all");
            add("club");
            add("dragon");
        }};

        MeaningfulSentenceFromADictionary meaningfulSentenceFromADictionary = new MeaningfulSentenceFromADictionary();
        List<String> result = meaningfulSentenceFromADictionary.breakWordIntoMeaningfulSentence("dragonballclub", dictionary);
        System.out.println(result);
    }

    @Test
    public void testMeaningFulSentenceFromADictionaryTest2() {

        Set<String> dictionary = new HashSet<String>() {{
            add("a");
            add("aa");
            add("aaa");
        }};

        MeaningfulSentenceFromADictionary meaningfulSentenceFromADictionary = new MeaningfulSentenceFromADictionary();
        List<String> result = meaningfulSentenceFromADictionary.breakWordIntoMeaningfulSentence("aaa", dictionary);
        List<List<String>> possibleResults = Arrays.asList(
            Arrays.asList("a", "a", "a"), Arrays.asList("a", "aa"), Arrays.asList("aa", "a"), Arrays.asList("aaa")
        );
        assertTrue(possibleResults.contains(result));
    }

    @Test
    public void testMeaningFulSentenceForNoSentence() {

        Set<String> dictionary = new HashSet<String>() {{
            add("aa");
            add("aaa");
            add("aaaa");
            add("aaaaa");
            add("aaaaaa");
        }};

        MeaningfulSentenceFromADictionary meaningfulSentenceFromADictionary = new MeaningfulSentenceFromADictionary();
        List<String> result = meaningfulSentenceFromADictionary.breakWordIntoMeaningfulSentence("b", dictionary);
        assertEquals(result, new ArrayList<String>());
    }
}
