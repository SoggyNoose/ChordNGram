package ChordNGram;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChordNGram {

	private static String[] genres = { "Indie", "Classic" };
	private static List<NGram> bigrams = new ArrayList<NGram>();
	
	public static void loadNGrams() {
		FileInputStream fis;
		ObjectInputStream in;
		NGram ngram = null;
		
		for (String genre : genres) {
			try {
				fis = new FileInputStream(genre);
				in = new ObjectInputStream(fis);
				ngram = (NGram)in.readObject();
				in.close();
			} catch (IOException e) {
				System.out.println("Could not open file for genre " + genre + ", starting new ngram");
				ngram = new NGram(genre);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			bigrams.add(ngram);
		}
	}
	
	public static void saveNGrams() {
		FileOutputStream fos;
		ObjectOutputStream out;
		
		for (NGram ngram : bigrams) {
			try {
				
				fos = new FileOutputStream(ngram.genre);
				out = new ObjectOutputStream(fos);
				out.writeObject(ngram);
				out.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static NGram getModel(BufferedReader reader) {
		for (int i=0; i<genres.length; i++) {
			System.out.println((i+1) + ". " + genres[i]);
		}
		
		try {
			String select = reader.readLine();
			
			int opt = Integer.parseInt(select);
			
			if (opt>bigrams.size()) {
				System.out.println("Now look at what you've done. It's ruined");
				return null;
			}
			
			return bigrams.get(opt-1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void readFile(String fname, NGram model) {
		FileReader fis;
		BufferedReader reader;
		
		try {
			fis = new FileReader(fname);
			reader = new BufferedReader(fis);
			
			String line;
			
			while((line = reader.readLine()) != null) {
				model.addProgression(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void scoreProgression(String prog) {
		Map<String, Double> scores = new TreeMap<String, Double>();
		
		for (NGram genre : bigrams) {
			scores.put(genre.genre, genre.scoreProgression(prog));
		}
		
		double accu = 0.0;
		for (String genre : scores.keySet()) {
			accu += scores.get(genre);
		}
		
		for (String genre : scores.keySet()) {
			System.out.println("Probability of being " + genre + " is " + Double.toString((scores.get(genre)/accu)*100) + "%");
		}
	}
	
	public static void main(String[] args) {
		BufferedReader reader;
		
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		String option;
		String fname;
		NGram genre;
		String chordProg;
		boolean running = true;
		
		loadNGrams();
		
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
					System.out.println("What genre is this song?");
					genre = getModel(reader);
					if (genre == null) continue;
					readFile(fname, genre);
				}
				if (option.equals("2")) {
					System.out.print("Please enter progression: ");
					chordProg = reader.readLine();
					// TODO score and such
					scoreProgression(chordProg);
				}
				if (option.equals("3")) {
					reader.close();
					running = false;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Bye");
		saveNGrams();
	}
}
