import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CheatingWordGuessingGame {
	private ArrayList<String> dictionary;
	ArrayList<Character> missedLetters;
	private ArrayList<Character> buildword;
	private static final int RAND_MIN=2;
	private static final int RAND_MAX=14;
	private int randLength;
	private  static final String[] pics= {
					"  +---+\n"+
					"  |   |\n"+
					"      |\n"+
					"      |\n"+
					"      |\n"+
					"      |\n"+
					"=========",
					"  +---+\n" + 
					"  |   |\n" + 
					"  O   |\n" + 
					"      |\n" + 
					"      |\n" + 
					"      |\n" + 
					"=========",
					"  +---+\n"+
					"  |   |\n"+
					"  O   |\n"+
					"  |   |\n"+
					"      |\n"+
					"      |\n"+
					"=========",
					"  +---+\n" +
					"  |   |\n" +
					"  O   |\n" +
					" /|   |\n" +
					"      |\n" +
					"      |\n" +
					"=========",
					"  +---+\n" +
					"  |   |\n" +
					"  O   |\n" +
					" /|\\  |\n" +
					"      |\n" +
					"      |\n" +
					"=========",
					"  +---+\n" +
					"  |   |\n" +
					"  O   |\n" +
					" /|\\  |\n" +
					" /    |\n" +
					"      |\n" +
					"=========", 
					"  +---+\n" +
					"  |   |\n" +
					"  O   |\n" +
					" /|\\  |\n" +
					" / \\  |\n" +
					"      |\n" +
					"========="};
	
	public static void main(String[] args) {
		String word="",gameState="you lost";
		Scanner in=new Scanner(System.in);
		CheatingWordGuessingGame hangman=new CheatingWordGuessingGame();
		hangman.initialization();
		while(word.equals("")&& hangman.missedLetters.size()<7) {
			hangman.display("");
			String guess=in.next();
			word=hangman.cheat(guess);
		}
		do {
			hangman.display(word);
			String guess=in.next();
			gameState=hangman.normalGame(word,guess);
		}while(gameState.equals("you lost") && hangman.missedLetters.size()<7);
		hangman.display(word);
		System.out.println(gameState+" the word was "+word);
		in.close();
	}

	public CheatingWordGuessingGame() {
		dictionary=new ArrayList<String>();
		missedLetters=new ArrayList<Character>();
		Scanner input;
		try {
			input = new Scanner(new File("word_list.txt"));
			while (input.hasNext()) {
				String word = input.next();
				dictionary.add(word);
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
	}
	public void initialization() {
		Random random=new Random(); 
		randLength=random.nextInt(RAND_MAX-RAND_MIN)+RAND_MIN;
		for(int i = dictionary.size()-1;i >= 0 ;i--) {
			String word=dictionary.get(i);
			if(word.length()!=randLength) {
				dictionary.remove(i);
			}
		}
	}
	
	public String cheat(String guess) {
		
		if(guess.length()>1) {
			if(dictionary.contains(guess)&&dictionary.size()>1) {
				dictionary.remove(Collections.binarySearch(dictionary,guess));
			}
		}else {
			missedLetters.add(guess.toCharArray()[0]);
			for(int i = dictionary.size()-1;i >= 0 ;i--) {
				if(dictionary.get(i).contains(guess)&&dictionary.size()>1) {
					dictionary.remove(i);
				}else if(dictionary.get(i).contains(guess)) {
					buildword=new ArrayList<Character>();
					buildword.add(guess.toCharArray()[0]);
					missedLetters.removeAll(buildword);
					return dictionary.get(0);
				}
			}
		}

		return "";

	}
	public String normalGame(String word,String guess) {
		if(guess.equals(word)) {
			return "you won";
		}
		if(guess.length()==1) {
			char letter=guess.toCharArray()[0];
			if(word.contains(guess)) {
				buildword.add(letter);
				char[] cWord=word.toCharArray();
				for (int i = 0; i < cWord.length; i++) {
					if(!buildword.contains(cWord[i]))
						return "you lost";
				}
				return "you won";
			}else {
				missedLetters.add(letter);
			}
			
		}
		return "you lost";
	}
	
	public void display(String word) {
		
		if(missedLetters.size()>0 && missedLetters.size()<7) {
			System.out.println(pics[missedLetters.size()-1]);
		}if(missedLetters.size()>=7)
			System.out.println(pics[6]);
		for (int i = 0; i < randLength; i++) {
			if(word.equals("")) {
				System.out.print("___  ");
			}else {
				char letter=word.charAt(i);
				if(buildword.contains((letter))) {
					System.out.print(" "+letter+"   ");
				}else {
					System.out.print("___  ");
				}
			}
		}
		System.out.print("\nmissed letters:=");
		for (int i = 0; i < missedLetters.size(); i++) {
			System.out.print(missedLetters.get(i)+" , ");
		}
		System.out.println();

	}

}
