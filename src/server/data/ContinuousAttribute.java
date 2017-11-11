package server.data;

import server.mining.ContinuousAttributeIterator;
import java.util.*;

/**
 * Rappresenta un intervallo per un attributo continuo (numerico) ottenuto per
 * discretizzazione
 * 
 * @author Angelo, Simone, Antonio
 *
 */
public class ContinuousAttribute extends Attribute implements Iterable<Float> {

	/** Rappresentano gli estremi di un intervallo */
	/** Estremo superiore */
	private float max;
	/** Estremo inferiore */
	private float min;

	/**
	 * Costruttore, inizializza i membri attributi
	 * 
	 * @param name
	 * @param index
	 * @param min
	 * @param max
	 */
	public ContinuousAttribute(String name, int index, float min, float max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}

	/**
	 * Restituisce il valore del membro min
	 * 
	 * @return min
	 */
	public float getMin() {
		return this.min;
	}

	/**
	 * Restituisce il valore del membro max
	 * 
	 * @return max
	 */
	public float getMax() {
		return this.max;
	}

	/**
	 * istanzia e restituisce un oggetto di classe ContinuousAttributeIterator
	 * con il numero massimo di intervalli fissato a 5
	 */
	public Iterator<Float> iterator() {
		ContinuousAttributeIterator i = new ContinuousAttributeIterator(getMin(), getMax(), 5);
		return i;
	}
}
