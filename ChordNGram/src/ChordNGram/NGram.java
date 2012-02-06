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
	public int count;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2621819054707430376L;
	
	public NGram(String genre) {
		this.genre = genre;
	}
	
	public void parseProgression(final String chordProg) {
		List<String> chords = parseContexts(chordProg);
	}

	public List<String> parseContexts(final String chordProg) {
		List<String> chords = new ArrayList<String>();
		
		Matcher matcher = chord.matcher(chordProg);
		
		while (matcher.find()) {
			chords.add(matcher.group());
		}
		
		return chords;
	}
	
	public double getBiGramLikelihood(BiGram toGet)
	{
		Integer biGramCount = this.ngramTable.get(toGet);
		int newCount = this.count;
		/* Laplace Add One  -- Should be replaced with backing off soon */
		if(biGramCount == 0)
		{
			biGramCount++;
			newCount += ngramTable.size();
		}
		return (double)biGramCount/(double)newCount;
	}
	
	
}
