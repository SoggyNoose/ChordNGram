package ChordNGram;

import java.io.Serializable;

public class BiGram implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7656518871438982739L;
	
	
	public String chord1;
	public String chord2;
	
	public BiGram(String chord1, String chord2) {
		this.chord1 = chord1;
		this.chord2 = chord2;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BiGram) {
			return (this.chord1.equals(((BiGram)obj).chord1)) && (this.chord2.equals(((BiGram)obj).chord2));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (this.chord1 + this.chord2).hashCode();
	}
}
