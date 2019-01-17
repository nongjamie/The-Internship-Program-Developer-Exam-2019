import java.util.List;
import java.util.Scanner;

/*
 * The hangman game's user interface.
 * @author: Sathira Kittisukmongkol
 */
public class Console {

	private Hangman game;
	private Scanner input = new Scanner(System.in);

	public Console(Hangman game) {
		this.game = game;
		askCategory();
		runConsole();
	}

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

	private void showGameStatus() {
		if(game.isUserWin())
			System.out.println("You Win !!!");
		else
			System.out.println("You lose !!!");
	}

}
