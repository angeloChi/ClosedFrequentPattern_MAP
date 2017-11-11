package server.mining;

import java.util.*;

/**
 * 
 * @author Angelo, Simone, Antonio
 * 
 * Modella l'iterazione sulla collezione di intervalli di valori che l'attributo
 * continuo può assumere
 *
 */
public class ContinuousAttributeIterator implements Iterator<Float> {
	/**
	 * valore minimo dell'attributo
	 */
	private float min;
	/**
	 * valore massimo dell'attributo
	 */
	private float max;
	/**
	 * j-esimo elemento della collezione, ovvero elemento corrente della
	 * iterazione
	 */
	private int j = 0;
	/**
	 * numero di elementi complessivi (numero di intervalli di valori)
	 */
	private int numValues;

	/**
	 * Costruttore avvalora i dati membro attributi
	 * 
	 * @param min
	 * @param max
	 * @param numValues
	 */
	public ContinuousAttributeIterator(float min, float max, int numValues) {
		this.min = min;
		this.max = max;
		this.numValues = numValues;
	}

	/**
	 * verifica che il j-esimo elemento (estremo) non superi il numero massimo
	 * di intervalli
	 */
	public boolean hasNext() {

		if (this.j >= this.numValues)
			return false;
		else
			return true;
	}

	/**
	 * restituisce l'elemento successivo (estremo) rispetto alla collezione di
	 * intervalli
	 */
	public Float next() {
		float ampiezza = this.max / numValues;
		this.min += ampiezza;
		j++;
		return this.min;
	}

	public void remove() {

	}

}
