package spell;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.io.File;

public class SpellCorrector implements ISpellCorrector {
    Trie dictionary = new Trie();
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        //System.out.println("hello");
        File file = new File(dictionaryFileName);
        Scanner loadDictionary = new Scanner(file);

        while(loadDictionary.hasNext()){
            String str = loadDictionary.next();
            dictionary.add(str);
        }
    }

    private ArrayList<String> possibleWords(String inputString) {
        ArrayList<String> possibles = new ArrayList<String>();
        char letter;
        //Here is insertion
        for(int i = 0; i <= inputString.length(); i++) {
            if(i==0) {
                for (int j = 0; j < 26; j++){
                    letter = (char)(j+'a');
                    possibles.add(letter + inputString);
                }
            }
            else {
                for (int j = 0; j < 26; j++) {
                    letter = (char) (j + 'a');
                    possibles.add(inputString.substring(0, i) + letter + inputString.substring(i));
                }
            }
        }

        //Here is deletion
        for(int i = 0; i < inputString.length(); i++) {
            possibles.add(inputString.substring(0,i) + inputString.substring(i+1));
        }
        //transposition
        for(int i = 0; i < inputString.length(); i++) {
            if (inputString.length() == 1) {
                break;
            }
            if(i ==0) {
                StringBuilder str = new StringBuilder(inputString);
                char tmp = str.charAt(i);
                str.setCharAt(i, str.charAt(i+1));
                str.setCharAt(i+1, tmp);
                possibles.add(str.toString());
            }
            else if(i == inputString.length()-1) {
                StringBuilder str = new StringBuilder(inputString);
                char tmp = str.charAt(i);
                str.setCharAt(i, str.charAt(i-1));
                str.setCharAt(i-1, tmp);
                possibles.add(str.toString());
            }
            else {
                StringBuilder str = new StringBuilder(inputString);
                StringBuilder str2 = new StringBuilder(inputString);
                char tmp = str.charAt(i);
                char tmp2 = str.charAt(i);
                str.setCharAt(i, str.charAt(i+1));
                str.setCharAt(i+1, tmp);
                possibles.add(str.toString());
                str2.setCharAt(i, str2.charAt(i-1));
                str2.setCharAt(i-1, tmp2);
                possibles.add(str2.toString());
            }

        }
        //alteration
        for(int i = 0; i < inputString.length(); i++) {
            StringBuilder str = new StringBuilder(inputString);
            for (int j = 0; j < 26; j++) {
                letter = (char)(j+'a');
                str.setCharAt(i, letter);
                possibles.add(str.toString());
            }
        }
        return possibles;
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase(Locale.ROOT);
        ArrayList<String> matches = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();

        INode word = new Node();
        if(dictionary.find(inputWord) != null) {
            return inputWord;
        }

        else {
            ArrayList<String> possibles;
            possibles = possibleWords(inputWord);
            ArrayList<String> possibles2 = new ArrayList<>();
            //possibles = new ArrayList<>();
            //possibles.add("yea");
            for (String possible : possibles) {
                word = dictionary.find(possible);
                if (word != null) {
                    matches.add(possible);
                    count.add(word.getValue());
                    //TODO: Will need to figure out a way to get the count here, word does not exist
                }
            }

            if (matches.isEmpty()) {
                ArrayList<String> tmp;
                //TODO: Build out the two distance
                for (int i = 0; i < possibles.size(); i++) {
                    tmp = possibleWords(possibles.get(i));
                    possibles2.addAll(tmp);
                }
                for (String possible: possibles2) {
                    word = dictionary.find(possible);
                    if(word != null) {
                        matches.add(possible);
                        count.add(word.getValue());
                    }
                }
                if(matches.isEmpty()){
                    return null;
                }
            }
            if(matches.size() > 1) {
                ArrayList<String> finalList = new ArrayList<>();
                int max = getLargest(count);
                for(int i = 0; i < count.size(); i ++) {
                    if (count.get(i) == max) {
                        finalList.add(matches.get(i));
                    }
                }
                if (finalList.size() > 1) {
                    Collections.sort(finalList);
                    return finalList.get(0);
                }
                return finalList.get(0);
            }
            return matches.get(0);
        }
    }
    private int getLargest(ArrayList<Integer> count) {
        int max = 0;
        for (Integer integer : count) {
            if (integer >= max) {
                max = integer;
            }
        }
        return max;
    }
}
