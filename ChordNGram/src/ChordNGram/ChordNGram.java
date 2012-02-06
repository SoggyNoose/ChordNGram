package ChordNGram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChordNGram {
	
	public static void main(String[] args) {
		BufferedReader reader;
		
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		String chordProg;
		boolean running = true;
		
		while (running) {
			System.out.print("Input Chord progression (or type 'quit'): ");
			try {
				chordProg = reader.readLine();
				if (chordProg.equals("quit")) {
					running = false;
					break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
