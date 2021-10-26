package childspeak;

import java.io.*;
import java.util.ArrayList;

public class ChildSpeak {
	static File input = new File("resources\\small_input.txt");
	static String lines;
	
	public static void main(String[] args) throws Exception {
		ArrayList<String> wordList = new ArrayList<String>();
		ArrayList<String> wordInput = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(input));
		ArrayList<String> wordArrayList = new ArrayList<String>();
		
		while ((lines = br.readLine()) != null) {
			// Create an arraylist of the words in the file
			wordList.add(lines);
			
			// Get the first consonant of the word
			char consonant = getFirstConsonant(lines);
			
			// Replaces all the subsequent consonants with the first consonant
			String replacedConsonants = replaceConsonants(consonant, lines);
			
			// If the word starts with a vowel, she puts the first consonant to the very beginning
			String replacedInitialVowel = replaceInitialVowel(consonant, replacedConsonants);
			
			// If there is a group of consecutive consonants, she replaces the whole group with just a single consonant
			// She ignores all the consonants after the last vowel
			String removedConsecutiveConsonants = removeConsecutiveConsonants(replacedInitialVowel).toString();
			
			// If there is a group of consecutive vowels, she replaces that group with the last vowel from the group
			removeConsecutiveVowels(removedConsecutiveConsonants, wordArrayList).toString();
			
			wordInput.add(lines);
		}
		
		// For every word, produce a word n line in the output file
		// where n is a number of words from the dictionary having the same pronunciation as word
		frequencyCount(wordArrayList, wordInput);
		br.close();
	}
	
	public static boolean isVowel(char letter) {
		if (letter == 'a' || letter == 'e' ||
			letter == 'i' || letter == 'o' ||
			letter == 'u') {
			return true;
		}
		return false;
	}
	
	public static char getFirstConsonant (String word) {
		char consonant = 0;
		
		for (int i = 0; i < word.length(); i++) {
			if (!isVowel(word.charAt(i))) {
				consonant = word.charAt(i);
				break;
			}
		}

		return consonant;
	}
	
	public static String replaceConsonants (char letter, String word) {
		String updatedWord = word;
		
		if (word.length() > 2) {
			for (int i = 0; i < word.length(); i++) {
				if (!isVowel(word.charAt(i))) {
					updatedWord = updatedWord.replace(word.charAt(i), letter).toString();
				}
			}
		}

		return updatedWord;
	}
	
	public static String replaceInitialVowel (char letter, String word) {
		if (isVowel(word.charAt(0)))
			word = letter + word;

		return word;
	}
	
	public static StringBuilder removeConsecutiveConsonants (String word) {
		StringBuilder str = new StringBuilder();
		char initial = word.charAt(0);
		
		for (int i = 1; i < word.length(); i++) {
			if (initial != word.charAt(i)) {
				str.append(initial);
			}
			initial = word.charAt(i);
		}
		
		if(isVowel(word.charAt(word.length() - 1))) {
			str.append(initial);			
		}

		return str;
	}
	
	public static StringBuilder removeConsecutiveVowels (String word, ArrayList<String> wordList) throws Exception {
		StringBuilder str = new StringBuilder();
		str.append(word);
		
		if (str.toString().isEmpty()) {
			return str;
		}
		
		char currentChar = word.charAt(0);
		StringBuilder str1 = new StringBuilder();
		int size = word.length();
		
		for (int i = 1; i < size; i++) {
			char nextChar = word.charAt(i);

			if (isVowel(currentChar) && isVowel(nextChar)) {
				currentChar = nextChar;
				str1.append(currentChar);
//				continue;
			} else if (isVowel(currentChar) && !isVowel(nextChar)) {
				str1.append(currentChar);
			} else {
				str1.append(currentChar);
			}

//			currentChar = nextChar;
//			str1.append(currentChar);
		}
		
//		str1.append(currentChar);
		wordList.add(str.toString());

		return str1;
	}
	
	public static void frequencyCount (ArrayList<String> words, ArrayList<String> wordInput) throws Exception {
		int count = 0;
		
		for (int i = 0; i < wordInput.size(); i++) {
			for (int j = 0; j < wordInput.size(); j++) {
				if (words.get(i).equals(words.get(j)) == true) {
					count++;
				}
			}
			
			// Subtract 1 because we don't need to add itself to the count
			count = count - 1;
			System.out.println(wordInput.get(i) + " " + words.get(i) + " " + count);
//			System.out.println(wordInput.get(i) + " " + count);
			//We need to reset it to zero for the next word
			count = 0;
		}
		
	}
}