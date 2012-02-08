package ChordNGram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChordNGram {

	private static String[] genres = { "Indie", "Folk", "Pop", "Alternative", "Metal", "Classic", "Punk", "Progressive" };
	
	public static void loadNGrams(String fname) {
		// TODO
		try {
			BufferedReader file = new BufferedReader(new FileReader(fname));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		BufferedReader reader;
		
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		String option;
		String fname;
		String genre;
		String chordProg;
		boolean running = true;
		
		while (running) {
			System.out.println("Choose option: ");
			System.out.println("1. Train Model");
			System.out.println("2. Score Progression");
			System.out.println("3. Quit");
			System.out.print(">>");
			try {
				option = reader.readLine();
				
				if (option.equals("1")) {
					System.out.print("Please enter filename: ");
					fname = reader.readLine();
					// TODO Open the file and such
					System.out.print("What genre is this song?");
					genre = reader.readLine();
					// Retrieve appropriate NGram model
					// whatever.addProgression(something)
				}
				if (option.equals("2")) {
					System.out.print("Please enter progression: ");
					chordProg = reader.readLine();
					// TODO score and such
				}
				if (option.equals("3")) {
					System.out.println("Bye");
					// TODO closing and such
					running = false;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
