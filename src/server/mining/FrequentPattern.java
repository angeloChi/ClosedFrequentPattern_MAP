package server.mining;

import java.util.*;
import java.io.*;

/**
 * 
 * @author Angelo, Simone, Antonio
 * 
 * Rappresenta un itemset frequente
 *
 */
public class FrequentPattern implements Comparable<FrequentPattern>, Serializable {

	/**
	 * Lista che contiene oggetti Item che definiscono il pattern
	 * 
	 */
	private LinkedList<Item> fp;
	/**
	 * Valore di supporto (frequenza relativa)
	 */
	private float support;

	/**
	 * Aggregazione
	 */
	Item item;

	/**
	 * Inizializza la lista
	 */
	public FrequentPattern() {
		this.fp = new LinkedList<Item>();
	}

	/**
	 * Aggiunge un item in ultima posizione
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		this.fp.add(item);

	}

	/**
	 * Restituisce l'item in posizione index
	 * 
	 * @param index
	 * @return Item
	 */
	Item getItem(int index) {
		return this.fp.get(index);
	}

	/**
	 * Restituisce il valore del membro support
	 * 
	 * @return support
	 */
	float getSupport() {
		return this.support;
	}

	/**
	 * Restituisce la dimensione della lista
	 * 
	 * @return fp.size()
	 */
	int getPatternLength() {
		return this.fp.size();
	}

	/**
	 * Assegna un supporto al membro attributo
	 * 
	 * @param support
	 */
	void setSupport(float support) {
		this.support = support;
	}

		
	public String toString(){
	    Iterator<Item> iteratore = this.fp.iterator();
	    int i = 0;
	    String value = "";
	    while(iteratore.hasNext()){
	      if(i < this.fp.size() - 1){
	        value += iteratore.next() + " AND ";
	      }else
	        break;
	      i++;
	    }
	    value += this.getItem(this.getPatternLength()-1) + "[" + this.getSupport() + "]";
	    return value;
	  
	  }
	


	/**
	 * Iteratore sul pattern
	 * @return iteratore
	 */
	public Iterator<Item> iterator() {
		Iterator<Item> i = this.fp.iterator();
		while (i.hasNext()) {
			 i.next();
		}
		return i;
	}

	/**
	 * Restituisce il riferimento al contenitore degli items del pattern
	 * @return
	 */
	public LinkedList<Item> getItemList() {
		return this.fp;
	}

	/**
	 * Confronto di pattern rispetto la loro lunghezza
	 */
	public int compareTo(FrequentPattern f) {
		int x = this.getPatternLength();
		int y = f.getPatternLength();
		if (x < y)
			return -1;
		else if (x == y)
			return 0;
		else
			return 1;
	}
}
