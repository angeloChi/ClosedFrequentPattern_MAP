package server.mining;

import server.data.ContinuousAttribute;

/**
 * 
 * @author Angelo, Simone, Antonio
 * 
 * rappresenta una coppia <Attributo continuo- intervallo di valori assunti>
 * (Temperature =[10;30.3])
 * 

 *
 */
public class ContinuousItem extends Item {

	/**
	 * Estremo inferiore
	 */
	private float inf;
	/**
	 * Estremo superiore
	 */
	private float sup;

	/**
	 * Avvalora i membri attributi
	 * 
	 * @param attribute
	 * @param value
	 */
	public ContinuousItem(ContinuousAttribute attribute, Interval value) {
		super(attribute, value);
		this.inf = value.getInf();
		this.sup = value.getSup();
	}

	/**
	 * Ad esempio, dato una transazioni in cui [Temperature=15] e un Item con
	 * <Temperature =[10;30.3]>, verifica se 15 è incluso in [10;30.3].
	 */
	@Override
	boolean checkItemCondition(Object value) {
		return ((Interval) this.value).checkValueInclusion((float)value);
	}
	
	/**
	 * Metodo per verificare se un intervallo contiene l'altro intervallo
	 * @param valore
	 * @return esito della condizione
	 */
	boolean checkInterval(Object valore){
		Interval interval = (Interval)  valore;
		if(this.inf <= interval.getInf() && this.sup >= interval.getSup())
			return true;
		else
			return false;
	}

	public String toString() {
		String s = "";
		s = attribute + "," + "[" + this.inf + ";" + this.sup + "]";
		return s;
	}

	float getInf() {
		return this.inf;
	}

	float getSup() {
		return this.sup;
	}
}
