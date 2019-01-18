import java.util.List;
import java.util.Scanner;

/*
 * The hangman game's user interface.
 * @author: Sathira Kittisukmongkol
 */
public class Console {

	private Hangman game;
	private Scanner input = new Scanner(System.in);

	/*
	 * The constructor of console.
	 * Before user plays the game,
	 * ask them for category and then start the game.
	 * @param game , the hangman game object.
	 */
	public Console(Hangman game) {
		this.game = game;
		askCategory();
		runConsole();
	}

	/*
	 * Ask user for the category of the word.
	 */
	private void askCategory() {
		List<String> category = game.getVocabCategory();
		System.out.println("Select Category:");
		for(int i = 0 ; i < category.size() ; i++)
			System.out.println("(" + i + ") " + category.get(i));
		while(true) {
			String userInput = input.nextLine();
			try {
				int choice = Integer.parseInt(userInput);
				if((choice >= 0) && (choice < category.size())) {
					game.setUserCategory(category.get(choice));
					break;
				}
				else
					System.out.println("Don't have your choice, try again...");
			} catch(Exception ex) {
				System.out.println("Your input is invalid, try again...");
			}
		}
	}

	/*
	 * Start the game.
	 */
	private void runConsole() {
		String userInput;
		game.randomSomeWord();
		System.out.println("Hint : " + game.getHint());
		while(!game.isEnd()) {
			showDialog();
			userInput = input.nextLine();
			game.userGuess(userInput);
		}
		showDialog();
		showGameStatus();
		System.out.println("Program ends ...");
	}

	/*
	 * Show the dialog of the game.
	 */
	private void showDialog() {
		for(String s : game.getAnswerArray())
			System.out.print(s + " ");
		System.out.print("score " + game.getScore() + ", ");
		System.out.print("remaining wrong guess " + game.getRemainLife());
		if( game.getWrongGuessed().size() != 0 ) {
			System.out.print(", wrong guessed: ");
			for(String s : game.getWrongGuessed())
				System.out.print(s + " ");
		}
		System.out.println("");
	}

	/*
	 * When the game is end,
	 * user wins the game or not.
	 */
	private void showGameStatus() {
		if(game.isUserWin())
			System.out.println("You Win !!!");
		else
			System.out.println("You lose !!!");
	}

}
