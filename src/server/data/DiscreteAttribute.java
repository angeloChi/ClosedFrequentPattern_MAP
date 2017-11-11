package server.data;

/** Rappresenta un attributo discreto
 * @author Angelo, Simone, Antonio
 *
 */
public class DiscreteAttribute extends Attribute {
	/** Array di oggetti String, uno per ciascun valore discreto */
	private String values[];

	/**
	 * Costruttore, avvalora i membri attributi
	 * 
	 * @param name
	 * @param index
	 * @param values
	 */
	public DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);
		this.values = values;
	}

	/**
	 * Restituisce la cardinalità dell'array values
	 * 
	 * @return values.length
	 */
	public int getNumberOfDistinctValues() {
		return this.values.length;
	}

	/**
	 * Restituisce il valore dell'i-esimo dell'attributo discreto
	 * 
	 * @param i
	 * @return values[i]
	 */
	public String getValue(int i) {
		return this.values[i];
	}
}
