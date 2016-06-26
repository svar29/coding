package com.uber.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DictionaryReader {

    private final String filename;

    public DictionaryReader(String filename) {
        this.filename = filename;
    }

    public Set<String> getDictionary() throws IOException {
        System.out.println("Reading from file: " + filename);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

        Set<String> dictionary = new HashSet<String>();
        String line = null;
        while ( (line = bufferedReader.readLine()) != null) {
            dictionary.add(line);
        }
        return dictionary;
    }
}
