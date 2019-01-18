/*
 * The word.
 * @author: Sathira Kittisukmongkol
 */
public class Word {

	private String word;
	private String hint;

	/*
	 * The constructor of the word class.
	 * @param word , the word.
	 * @param hint , the hint of that word.
	 */
	public Word(String word, String hint) {
		this.word = word;
		this.hint = hint;
	}

	/*
	 * Get the word.
	 * @return word.
	 */
	public String getWord() {
		return word;
	}

	/*
	 * Get the hint of the word.
	 * @return hint.
	 */
	public String getHint() {
		return hint;
	}

}
