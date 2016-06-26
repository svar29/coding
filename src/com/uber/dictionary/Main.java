package com.uber.dictionary;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String args[]) throws IOException {
        String filename = args.length > 0 ? args[0] : "/Users/krunalmanik/uber/src/com.uber.dictionary/com.uber.dictionary.txt";
        DictionaryReader dictionaryReader = new DictionaryReader(filename);
        Set<String> dictionary = dictionaryReader.getDictionary();
        MeaningfulSentenceFromADictionary meaningfulSentenceFromADictionary = new MeaningfulSentenceFromADictionary();
        List<String> meaningfulSentence = meaningfulSentenceFromADictionary.breakWordIntoMeaningfulSentence("dragonballclub", dictionary);
        System.out.println(meaningfulSentence);
    }
}
