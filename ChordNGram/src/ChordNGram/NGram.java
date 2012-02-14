package ChordNGram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NGram implements Serializable {
	private static final Pattern chord = Pattern.compile("[VIvi]+((M7)|(m7)|(sus))?");
	
	public String genre;
	public Map<BiGram, Integer> ngramTable = new HashMap<BiGram, Integer>();
	public Map<String, Integer> contextTable = new HashMap<String, Integer>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2621819054707430376L;
	
	public NGram(String genre) {
		this.genre = genre;
	}
	
	public double scoreProgression(final String chordProg) {
		List<String> chords = parseChords(chordProg);
		
		// Create list of BiGrams
		List<BiGram> sequence = new ArrayList<BiGram>();
		for (int i=1; i<chords.size(); i++) {
			BiGram bigram = new BiGram(chords.get(i-1), chords.get(i));
			sequence.add(bigram);
		}
		
		// Check for missing bigrams, updating if needed
		checkMissingBigram(sequence);
		
		// Score
		double score = 1;
		for (BiGram bigram : sequence) {
			score *= getBiGramLikelihood(bigram);
		}
		
		return score;
	}
	
	public void addProgression(final String chordProg) {
		List<String> chords = parseChords(chordProg);
		
		for (int i=1; i<chords.size(); i++) {
			BiGram bigram = new BiGram(chords.get(i-1), chords.get(i));
			Integer count = ngramTable.get(bigram);
			if (count == null) {
				ngramTable.put(bigram, 1);
			} else {
				ngramTable.put(bigram, count+1);
			}
			
			Integer context = contextTable.get(bigram.chord1);
			if (context == null) {
				contextTable.put(bigram.chord1, 1);
			} else {
				contextTable.put(bigram.chord1, context+1);
			}
		}
	}

	public List<String> parseChords(final String chordProg) {
		List<String> chords = new ArrayList<String>();
		
		Matcher matcher = chord.matcher(chordProg);
		
		while (matcher.find()) {
			chords.add(matcher.group());
		}
		
		return chords;
	}
	
	public void checkMissingBigram(List<BiGram> sequence) {
		List<BiGram> missingBgram = new ArrayList<BiGram>();
		List<String> missingContext = new ArrayList<String>();
		boolean hasMissing = false;
		
		for (BiGram bigram : sequence) {
			Integer biGramCount = this.ngramTable.get(bigram);
			Integer contextCount = this.contextTable.get(bigram.chord1);
			
			if(contextCount == null)
			{
				hasMissing = true;
				missingContext.add(bigram.chord1);
			}
			if (biGramCount == null) {
				hasMissing = true;
				missingBgram.add(bigram);
			}
		}
		
		if (hasMissing) {
			/* Laplace Add One  -- Should be replaced with backing off soon */
			for (BiGram key : ngramTable.keySet()) {
				ngramTable.put(key, ngramTable.get(key)+1);
			}
			for (String key : contextTable.keySet()) {
				contextTable.put(key, contextTable.get(key)+1);
			}
			
			// Add missing contexts to table
			for (String context : missingContext) {
				contextTable.put(context, 1);
			}
			
			// Add missing bigrams to table
			for (BiGram bigram : missingBgram) {
				ngramTable.put(bigram, 1);
			}
		}
	}
	
	public double getBiGramLikelihood(BiGram toGet)
	{
		Integer biGramCount = this.ngramTable.get(toGet);
		int contextCount = this.contextTable.get(toGet.chord1);
		return (double)biGramCount/(double)contextCount;
	}
	
	
}
