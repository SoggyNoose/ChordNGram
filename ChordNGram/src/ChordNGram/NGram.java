package ChordNGram;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NGram implements Serializable {
	
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
	
	public void parseProgression() {
		
	}

	public void parseContexts() {
		
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
