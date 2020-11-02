package challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JumbledNumbers{
	
	public String calculateTotalValue(String jumbledStr) {
			System.out.println("Please enter a scrambled string");

			String inputString = jumbledStr; 
			String outputString = "";
			
			if(inputString==null || inputString.isEmpty()) {
				System.out.println("No input provided. Program is terminating.");
			} else {
			
			inputString=inputString.toLowerCase();
			
			Map<String,Integer> words2SearchMap = initializeMap();
			HashMap<Character,Integer> charCountMap = new HashMap<>();
			List<Integer> result = new ArrayList<>(10);
			
			prepareCharacterMap(inputString,charCountMap);
			
			
			words2SearchMap.forEach(
					(key,value) -> 
					{
						processWord(key, value, charCountMap, result);
					}
			);
			
			Collections.sort(result);
			
			for (Integer v : result) {
				outputString=outputString.concat(v.toString());
			}
			result.forEach(output -> System.out.println(output));
			System.out.println("Output is :"+outputString);
			}
			
			System.out.println("Program is terminating. Thanks");
			return outputString;
		}
	
	/**
	 * We will prepare the count of characters present in the input string.
	 */
	private void prepareCharacterMap(String inputString,HashMap<Character,Integer> charCountMap) 
	{
		inputString.chars().forEach(
			characterCode -> {
				//we will get ascii value for characters hence we are typecasting it to char
				Character charValue= (char)characterCode;
				if(charCountMap.containsKey(charValue)) {
					Integer value = charCountMap.get(charValue);
					charCountMap.put(charValue,++value);
				}
				else {
					charCountMap.put(charValue,1);
				}
			}
		);
	}
	
	/**
	 * We will process the word & keep finding the word in the input string & retain the values in result
	 */
	private void processWord(String word,Integer wordValue,HashMap<Character,Integer> charCountMap,List<Integer> result) 
	{
		boolean isWordFound = Boolean.TRUE;
		do 
		{
			int wordLength = word.length();
			//We are iterating over the word i.e. if word is 'one' then we are processing each of its characters one by one
			for(int counter = 0; counter<wordLength; counter++) {
				//o  n  e   -->  neoone n (2)  e (2)  o(2)
				//we will get the characters for words e.g. zero ... nine
				Character charValue = word.charAt(counter);
				//we are trying to check for each character of the word is present in the input string which we are saving in charCountMap
				if(!isCharacterFound(charCountMap,charValue)) {
					isWordFound=Boolean.FALSE;
					break;
				}
			}
			//if word is found then remove all characters of that word from the input map
			if(isWordFound) {
				word.chars().forEach(
					//o  n  e   -->   neoone n (1)  e (1)  o(1)
					characterCode -> {
						consumeCharacter(charCountMap,(char)characterCode);
					}
				);
				result.add(wordValue);//we are adding the numerical value of the word e.g. 0...9
			}
		}while(isWordFound);//we will keep searching for the word till it exhausts
	}
	
	/**
	 * We are trying to check for each character of the word is present in the input string which we are saving in charCountMap
	 */
	private boolean isCharacterFound(HashMap<Character,Integer> charCountMap,Character character2Process) 
	{
		if(charCountMap.containsKey(character2Process)) {
			Integer tempValue = charCountMap.get(character2Process);
			if(tempValue!=null && tempValue>0) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * If word is found then remove all characters of that word from the input map
	 */
	private void consumeCharacter(HashMap<Character,Integer> charCountMap,Character character2Process) 
	{
		if(charCountMap.containsKey(character2Process)) {
			Integer tempValue = charCountMap.get(character2Process);
			if(tempValue!=null && tempValue>0) {
				--tempValue;
				charCountMap.put(character2Process, tempValue);
			}
		}
	}
	
	/**
	 * Static map of values that we will use to find numbers in input string
	 */
	private static Map<String,Integer> initializeMap()
	{
		Map<String,Integer> wordsMap = new LinkedHashMap<>(10);
		//please note the order in which we have kept the numbers. This is to ensure that words with greater count exhaust first from input string
		//there by reducing chances of finding a word as substring of another word
		wordsMap.put("eight",8);
		wordsMap.put("seven",7);
		wordsMap.put("three",3);
		wordsMap.put("four",4);
		wordsMap.put("five",5);
		wordsMap.put("nine",9);
		wordsMap.put("zero",0);
		wordsMap.put("one",1);
		wordsMap.put("two",2);
		wordsMap.put("six",6);
		return wordsMap;
	}
}
