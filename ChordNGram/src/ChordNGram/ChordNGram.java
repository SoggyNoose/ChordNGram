package ChordNGram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChordNGram {


	private static Pattern chord = Pattern.compile("[VIvi]+((M7)|(m7)|(sus))?");
	
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
				
				Matcher matcher = chord.matcher(chordProg);
				
				while (matcher.find()) {
					System.out.println("I found the chord: " + matcher.group());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
