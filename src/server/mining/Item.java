package server.mining;

import server.data.*;
import java.io.*;

/**
 * 
 * @author Angelo, Simone, Antonio
 * 
 * Modella un generico Item (coppia Atttributo - valore) Outlook = "Sunny"
 *
 */
public abstract class Item implements Serializable {
	/** attributo coinvolto nell'item */
	protected Attribute attribute;
	/** valore dell'attributo */
	protected Object value;

	/** Aggregazione */
	Attribute attribute_aggr;

	/**
	 * Costruttore, avvalora i membri attributo
	 * 
	 * @param attribute
	 * @param value
	 */
	public Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * Restituisce il membro Attribute
	 * 
	 * @return attribute
	 */
	Attribute getAttribute() {
		return this.attribute;
	}

	/**
	 * Restituisce il membro value
	 * 
	 * @return value
	 */
	Object getValue() {
		return this.value;
	}

	/**
	 * Verifica se esiste un item formato dalla coppia <Attributo (membro della
	 * classe) - Value(valore passato per argomento)
	 * 
	 * @param value
	 * @return
	 */
	abstract boolean checkItemCondition(Object value);

	/**
	 * Restituisce una stringa composta dai contenuti dell'oggeti membro
	 * 
	 */
	public String toString() {
		String s = "";
		s +="(" + attribute + "=" + value + ")";
		return s;
	}

}
