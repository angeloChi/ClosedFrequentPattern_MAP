package server.database;
import java.util.LinkedList;
import server.mining.FrequentPattern;

/**
 * 
 * @author Angelo, Simone, Antonio
 *
 */
public interface Irrilevancy {

	/**
	 * Input: lista di pattern frequenti (derivata dalla lista dei pattern scoperti dai metodi della
     * classe FrequentPatternMiner
     * Output: lista di tutti i pattern frequenti risultanti
	 * @param outFP
	 */
	 void prunePermutations(LinkedList<FrequentPattern> outFP);
	
	/**
	 * Input: lista di pattern frequenti (derivata dalla lista dei pattern scoperti dai metodi della
     * classe FrequentPatternMiner
     * Output: lista di tutti i pattern frequenti risultanti
	 * @param outFP
	 */
	void pruneSingle (LinkedList<FrequentPattern> outFP);
}
