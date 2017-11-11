package server.mining;

import server.data.*;

/**
 * 
 * @author Angelo, Simone, Antonio
 * Rappresentazione di una coppia <Attributo discreto - valore discreto>
 *  ad esempio (Outlook = "Sunny")
 *
 */
class DiscreteItem extends Item{
	/**Costruttore, avvalora i membri
	 * @param attribute
	 * @param value
	 */
	DiscreteItem(DiscreteAttribute attribute, String value){
		super(attribute,value);
		this.attribute = attribute;
		this.value = value;
	}
	
	/**Verifica che l'item corrente ha valore uguale a Value per la coppia
	 * <Attributo discreto - Value discreto>
	 */
	boolean checkItemCondition(Object value){  		
		if(this.value.equals(value))
			return true;
		else
			return false;
	}
}
