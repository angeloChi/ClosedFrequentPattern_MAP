package server.data;

import java.io.*;


/** Modella un generico attributo discreto o continuo
 * @author Angelo, Simone, Antonio
 *
 */
public class Attribute implements Serializable {

	/** Nome simbolico dell'attributo */
	protected String name;
	/** Identificativo numerico dell'attributo */
	protected int index;

	/**
	 * Costruttore inizializza i membri attributi
	 * 
	 * @param name
	 * @param index
	 */
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * Restituisce il valore del membro name
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Restituisce il valore del membro index
	 * 
	 * @return index
	 */
	int getIndex() {
		return this.index;
	}

	/**
	 * Restituisce il valore del membro name, per la stampa di attribute
	 * 
	 * @return name
	 */
	public String toString() {
		return this.name;
	}
}
