import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * The main core of hangman game.
 * @author: Sathira Kittisukmongkol
 */
public class Hangman {

	private int score = 0;
	private Word randomWord;
	private int remainLife = 10;
	private String userCategory;
	private boolean isEnd = false;
	private List<String> wordArrayAnswer = new ArrayList<>();
	private List<String> wrongGuessedList = new ArrayList<>();
	private HashMap<String, List<Word>> vocabTable = new HashMap<>();

	public Hangman() {
		readDataFromText();
	}

	private void readDataFromText() {
		File location = new File("./Word/");
		File[] listOfFiles = location.listFiles();
		for (File file : listOfFiles) {
			try {
				FileInputStream fstream = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				List<Word> listOfWord = new ArrayList<>();
				String line = br.readLine();
				while (line != null) {
				  String[] wordAndHint = line.split(",");
				  listOfWord.add(new Word(wordAndHint[0], wordAndHint[1]));
				  line = br.readLine();
				}
				String vocabTableKey = file.getName().replace(".txt", "");
				vocabTable.put(vocabTableKey, listOfWord);
				fstream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void userGuess(String userInput) {
		if(isAlphabet(userInput)) {
			if(hasContain(randomWord.getWord(), userInput)) {
				if(!wordArrayAnswer.contains(userInput)) {
					List<Integer> allIndex = allIndexOf(randomWord.getWord(), userInput);
					for(Integer i : allIndex) {
						wordArrayAnswer.set(i, userInput);
						score += (100/randomWord.getWord().length());
					}
				}
			}
			else {
				if(!wrongGuessedList.contains(userInput)) {
					remainLife -= 1;
					wrongGuessedList.add(userInput);
				}
			}
		}
	}

	public void randomSomeWord() {
		int randomIndex = (int)Math.floor(Math.random() * vocabTable.get(userCategory).size());
		randomWord = vocabTable.get(userCategory).get(randomIndex);
		for(int i = 0 ; i < randomWord.getWord().length() ; i++) {
			String currentStr = randomWord.getWord().charAt(i) + "";
			if(isAlphabet(currentStr))
				wordArrayAnswer.add("_");
			else
				wordArrayAnswer.add(currentStr);
		}
	}

	private List<Integer> allIndexOf(String word, String alphabet) {
		List<Integer> result = new ArrayList<>();
		for(int i = 0 ; i < word.length() ; i++) {
			if(word.charAt(i) == alphabet.charAt(0)) {
				result.add(i);
			}
		}
		return result;
	}

	private boolean hasContain(String word, String alphabet) {
		for(int i = 0 ; i < word.length() ; i++) {
			if(word.charAt(i) == alphabet.charAt(0)) {
				return true;
			}
		}
		return false;
	}

	public void setUserCategory(String choice) {
		userCategory = choice;
	}

	public List<String> getVocabCategory() {
		List<String> vocabCategory = new ArrayList<>();
		for(String str : vocabTable.keySet())
			vocabCategory.add(str);
		return vocabCategory;
	}

	public int getScore() {
		return score;
	}

	public int getRemainLife() {
		return remainLife;
	}

	public String getHint() {
		return randomWord.getHint();
	}

	public List<String> getAnswerArray() {
		return wordArrayAnswer;
	}

	public List<String> getWrongGuessed() {
		return wrongGuessedList;
	}

	private boolean isAlphabet(String input) {
		String[] alphabet = {"a", "b", "c", "d", "e",
				"f", "g", "h", "i", "j", "k", "l", "m",
				"n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z"};
		for(String s : alphabet) {
			if(input.equalsIgnoreCase(s))
				return true;
		}
		return false;
	}

	public boolean isUserWin() {
		if(wordArrayAnswer.contains("_"))
			return false;
		return true;
	}

	public boolean isEnd() {
		if(!wordArrayAnswer.contains("_")) {
			return true;
		}else if(remainLife == 0) {
			return true;
		}
		return false;
	}

	private void printTheVocabTable() {
		int numKeyInTable = vocabTable.size();
		String currentKey = "";
		for(int i = 0 ; i < numKeyInTable ; i++) {
			currentKey = vocabTable.keySet().toArray()[i].toString();
			System.out.println(currentKey);
			for(int j = 0 ; j < vocabTable.get(currentKey).size() ; j++) {
				Word word = vocabTable.get(currentKey).get(j);
				System.out.println((j + 1) + ". " + word.getWord() + " - " + word.getHint());
			}
		}
	}

}
