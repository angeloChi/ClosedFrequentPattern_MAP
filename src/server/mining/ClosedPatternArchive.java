package server.mining;

import java.util.*;
import java.io.*;

/**
 * 
 * @author Angelo, Simone, Antonio
 * 
 * Contenitore per i pattern frequenti chiusi con i relativi
 * pattern ridondanti
 * 
 */
public class ClosedPatternArchive implements Serializable {
	/**
	 * Contiene coppie di pattern frequenti chiusi con i loro pattern frequenti
	 * ridondanti
	 */
	HashMap<FrequentPattern, TreeSet<FrequentPattern>> archive;

	/**
	 * Costruttore
	 */
	ClosedPatternArchive() {
		this.archive = new HashMap<FrequentPattern, TreeSet<FrequentPattern>>();
	}

	/**
	 * Aggiunge il pattern frequente se non è già contenuto
	 * 
	 * @param fp
	 */
	public void put(FrequentPattern fp) {
		if (!this.archive.containsKey(fp))
			this.archive.put(fp, new TreeSet<FrequentPattern>());
	}

	/**
	 * Aggiunge il pattern frequente se non è già contenuto, se invece è
	 * contenuto inserisce l'argomento pattern all'insieme relativo al
	 * pattern-chiave
	 * 
	 * @param fp
	 * @param pattern
	 */
	public void put(FrequentPattern fp, FrequentPattern pattern) {
		put(fp);
		this.archive.get(fp).add(pattern);

	}

	/**
	 * Se vi sono ridondanti dal pattern chiave restituisce un TreeSet di
	 * pattern frequenti, altrimenti restituisce solo il pattern chiave
	 * 
	 * @param fp
	 * @return TreeSet
	 */
	public TreeSet<FrequentPattern> getRedundants(FrequentPattern fp) {
		if (this.archive.isEmpty()) {// altrimenti restituisci la chiave che è
										// stata aggiunta in un treeSet
			TreeSet<FrequentPattern> tree = new TreeSet<FrequentPattern>();
			tree.add(fp);
			return tree;
		} else {
			// restituisce il pattern frequente ridondante se la hashMap è piena
			return this.archive.get(fp);
		}
	}

	/**
	 * Scandendo le chiavi (le chiavi sono scandite con il metodo KeySet che
	 * restiuisce un set di chiavi) con un iteratore generico ma specializzato a
	 * frequentPattern si crea una stringa che concatena il pattern chiuso e il
	 * corrispondente pattern ridondante
	 */

	public String toString() {
		Iterator<FrequentPattern> i = this.archive.keySet().iterator();
		String str = "";
		int count = 1;
		while (i.hasNext()) {
			FrequentPattern key = i.next();
			str += count + "." + key.toString() + "\n" + this.archive.get(key) + "\n\n";
			count++;
		}
		return str;
	}

	


	/**
	 * Serializzazione
	 * 
	 * @param archivio
	 * @param path
	 * @throws IOException
	 */
	public static void salvataggio(ClosedPatternArchive archivio, String path) throws IOException { // Serializzazione
		FileOutputStream f = new FileOutputStream(new File(path));
		ObjectOutputStream s = new ObjectOutputStream(f);
		s.writeObject(archivio);
		System.out.println("Salvataggio completato");
		f.close();
		s.close();

	}

	/**
	 * Deserializzazione
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static ClosedPatternArchive caricamento(String path) throws IOException, Exception { // Deserializzazione
		ClosedPatternArchive closedPatternArchive = null;
		FileInputStream f = null;
		ObjectInputStream s = null;
		f = new FileInputStream(new File(path));
		s = new ObjectInputStream(f);
		closedPatternArchive = (ClosedPatternArchive) s.readObject();
		System.out.println("Caricamento completato");
		f.close();
		s.close();
		return closedPatternArchive;

	}

}
