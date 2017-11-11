package server.utility;

/**
 * 
 * @author Angelo, Simone, Antonio
 * 
 * Modella un elemento singolo della struttura dati linkata
 * 
 */
class Cella {

	Object elemento;

	Puntatore successivo = null;

	public Cella(Object e) {
		elemento = e;
	}

}
