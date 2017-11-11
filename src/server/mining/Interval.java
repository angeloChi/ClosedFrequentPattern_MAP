package server.mining;
import java.io.*;

/**
 * Modella un intervallo di valori che un attributo continuo può assumere
 * (Temperature = [10;30.3])
 * 
 * @author Angelo, Simone, Antonio
 *
 */
class Interval implements Serializable {
	/**
	 * Estremo inferiore
	 */
	private float inf;
	/**
	 * Estremo superiore
	 */
	private float sup;

	/**
	 * Costruttore, avvalora i due attributi
	 * 
	 * @param inf
	 * @param sup
	 */
	Interval(float inf, float sup) {
		this.inf = inf;
		this.sup = sup;
	}

	/**
	 * Avvalora l'attributo inf
	 * 
	 * @param inf
	 */
	void setInf(float inf) {
		this.inf = inf;
	}

	/**
	 * Avvalora l'attributo sup
	 * 
	 * @param sup
	 */
	void setSup(float sup) {
		this.sup = sup;
	}

	/**
	 * Restituisce il valore di inf
	 * 
	 * @return estremo inferirore
	 */
	float getInf() {
		return this.inf;
	}

	/**
	 * Restituisce il valore di sup
	 * 
	 * @return
	 */
	float getSup() {
		return this.sup;
	}

	/**
	 * Verifica se il valore in input è incluso nell'intervallo definito
	 * dall'oggetto di classe interval
	 * 
	 * @param value
	 * @return esito della verifica
	 */
	boolean checkValueInclusion(float value) {
		if (value >= this.getInf() && value <= this.getSup())
			return true;
		else
			return false;
	}

	/**
	 * concatena in un oggetto String l'intervallo formato dagli attributi di
	 * classe
	 */
	public String toString() {
		String s = "";
		s += "[" + getInf() + ";" + getSup() + "]";
		return s;
	}

}