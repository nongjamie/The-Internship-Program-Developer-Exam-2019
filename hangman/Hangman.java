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

	/*
	 * The constructor of hangman game.
	 * Start the game by read the words and hints from txt file.
	 */
	public Hangman() {
		readDataFromText();
	}

	/*
	 * Read the words and hints from txt file from the Word folder.
	 */
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

	/*
	 * Update all related values when user input some guess.
	 * @param userInput , guess alphabet from the user.
	 */
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

	/*
	 * Random some word to let user guess.
	 */
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

	/*
	 * Find all index of input alphabet.
	 * @param word , the word which that wanted alphabet may be located in.
	 * @param alphabet , the alphabet that want to find in the word.
	 * @return result , the list of index of that alphabet in the word.
	 */
	private List<Integer> allIndexOf(String word, String alphabet) {
		List<Integer> result = new ArrayList<>();
		for(int i = 0 ; i < word.length() ; i++) {
			if(word.charAt(i) == alphabet.charAt(0)) {
				result.add(i);
			}
		}
		return result;
	}

	/*
	 * Check that alphabet contains in the word or not.
	 * @param word , the word which that wanted alphabet may be located in.
	 * @param alphabet , the alphabet that want to find in the word.
	 * @return true/false.
	 */
	private boolean hasContain(String word, String alphabet) {
		for(int i = 0 ; i < word.length() ; i++) {
			if(word.charAt(i) == alphabet.charAt(0)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Set the user choice.
	 * @param choice , the user's selected choice.
	 */
	public void setUserCategory(String choice) {
		userCategory = choice;
	}

	/*
	 * Get all the key in the vocabTable.
	 * @return vocabCategory , list of all the keys in the vocabTable.
	 */
	public List<String> getVocabCategory() {
		List<String> vocabCategory = new ArrayList<>();
		for(String str : vocabTable.keySet())
			vocabCategory.add(str);
		return vocabCategory;
	}

	/*
	 * Get the present score.
	 * @return score , present score.
	 */
	public int getScore() {
		return score;
	}

	/*
	 * Get the remain life.
	 * @return remainLife , the number of remain life.
	 */
	public int getRemainLife() {
		return remainLife;
	}

	/*
	 * Get the hint of the random word.
	 * @return the hint of the random word.
	 */
	public String getHint() {
		return randomWord.getHint();
	}

	/*
	 * Get the recently user's answers.
	 * @return wordArrayAnswer , list of recently user's answers.
	 */
	public List<String> getAnswerArray() {
		return wordArrayAnswer;
	}

	/*
	 * Get the recently user's wrong answers.
	 * @return wrongGuessedList , list of recently user's wrong answers.
	 */
	public List<String> getWrongGuessed() {
		return wrongGuessedList;
	}

	/*
	 * Check input is alphabet or not.
	 * @param input , alphabet that want to check.
	 * @return true/false.
	 */
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

	/*
	 * Check that user win or not.
	 * @return true/false.
	 */
	public boolean isUserWin() {
		if(wordArrayAnswer.contains("_"))
			return false;
		return true;
	}

	/*
	 * Check that game is end or not.
	 * @return true/false.
	 */
	public boolean isEnd() {
		if(!wordArrayAnswer.contains("_")) {
			return true;
		}else if(remainLife == 0) {
			return true;
		}
		return false;
	}

	/*
	 * Print the vocabTable.
	 * See all the words with their hint in vocabTable.
	 */
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
