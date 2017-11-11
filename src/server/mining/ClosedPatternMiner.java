package server.mining;

import server.data.Attribute;
import server.data.ContinuousAttribute;
import server.data.DiscreteAttribute;

import java.util.*;
import java.math.*;

import server.database.*;

/**
 * 
 * @author Angelo, Simone, Antonio
 * 
 * Scoperta dei pattern chiusi a partire da quelli frequenti
 *  calcolati in precedenza
 * 
 *
 */
public class ClosedPatternMiner implements Irrilevancy {
	/**
	 * 
	 * @param outFP
	 * @param epsilon
	 * @return
	 */
	public static ClosedPatternArchive closedPatternDiscovery(LinkedList<FrequentPattern> outFP, float epsilon) {
		ClosedPatternMiner cm = new ClosedPatternMiner();
		cm.pruneSingle(outFP);
		cm.prunePermutations(outFP);
		ClosedPatternArchive cp = new ClosedPatternArchive();
		int max_length;
		int current_length;
		ClosedPatternMiner cl = new ClosedPatternMiner();
		cl.pruneSingle(outFP);
		Iterator<FrequentPattern> i = outFP.iterator();
		while (i.hasNext()) {
			FrequentPattern fp = i.next();
			max_length = fp.getPatternLength();
			current_length = max_length;
			while (current_length > 1) {
				current_length--;

				for (FrequentPattern r : outFP) {
					if (r.getPatternLength() == current_length) {
						if (isCoveredBySupport(fp, r, epsilon) && isCoveredByItems(fp, r)) {
							cp.put(fp, r);

						}
					}
				}
			}
		}

		return cp;

	}

	/**
	 * Verifica se i pattern rendono vera la condizione di chiusura rispetto al
	 * supporto
	 * 
	 * @param current
	 *            (candidato chiuso)
	 * @param examined
	 *            (candidato ridondante)
	 * @param epsilon
	 *            (soglia di chiusura)
	 * @return esito condizione di chiusura
	 */
	private static boolean isCoveredBySupport(FrequentPattern current, FrequentPattern examined, float epsilon) {
		if (Math.abs(examined.getSupport() - current.getSupport()) < epsilon)
			return true;
		else
			return false;
	}

	/**
	 * Verifica se i pattern rendono vera la condizione di chiusura rispetto
	 * agli items contenuti
	 * 
	 * @param current
	 * @param examined
	 * @return esito della condizione di chiusura
	 */
	public static boolean isCoveredByItems(FrequentPattern current, FrequentPattern examined) {
		Class discreteItemClass = DiscreteItem.class;
		Class continuousItemClass = ContinuousItem.class;
		boolean bool = false;
		for (int i = 0; i < current.getPatternLength(); i++) {
			for (int j = 0; j < examined.getPatternLength(); j++) {
				if (discreteItemClass.isInstance(current.getItem(i))
						&& discreteItemClass.isInstance(examined.getItem(j))) {
					DiscreteItem itemDiscretoCurrent = (DiscreteItem) current.getItem(i);
					DiscreteItem itemDiscretoExamined = (DiscreteItem) examined.getItem(j);
					if (itemDiscretoCurrent.checkItemCondition(itemDiscretoExamined.getValue()))
						bool = true;
				} else if (continuousItemClass.isInstance(current.getItem(i))
						&& continuousItemClass.isInstance(examined.getItem(j))) {
					ContinuousItem itemContinuoCurrent = (ContinuousItem) current.getItem(i);
					ContinuousItem itemContinuoExamined = (ContinuousItem) examined.getItem(j);
					if (itemContinuoCurrent.checkInterval(itemContinuoExamined.getValue())) {
						bool = false;
					}
				}
			}

		}
		return bool;
	}

	/**
	 * Identifica il pattern con più items
	 * 
	 * @param outFP
	 * @return restituisce la lunghezza
	 */
	private static int getMaxLength(LinkedList<FrequentPattern> outFP) {
		Iterator<FrequentPattern> i = outFP.iterator();
		int max = 0;
		while (i.hasNext()) {
			if (i.next().getPatternLength() > max)
				max = i.next().getPatternLength();
		}
		return max;
	}

	private static boolean isPermutation(FrequentPattern current, FrequentPattern examined) {
		Class discreteItemClass = DiscreteItem.class;
		Class continuousItemClass = ContinuousItem.class;
		int count = 0;
		if (current.getPatternLength() == examined.getPatternLength()) {
			for (int i = 0; i < current.getPatternLength(); i++) {
				for (int j = 0; j < examined.getPatternLength(); j++) {
					if (discreteItemClass.isInstance(current.getItem(i))
							&& discreteItemClass.isInstance(examined.getItem(j))) {
						DiscreteItem itemDiscretoCurrent = (DiscreteItem) current.getItem(i);
						DiscreteItem itemDiscretoExamined = (DiscreteItem) examined.getItem(j);
						if (itemDiscretoCurrent.checkItemCondition(itemDiscretoExamined.getValue()))

							count++;
					} else if (continuousItemClass.isInstance(current.getItem(i))
							&& continuousItemClass.isInstance(examined.getItem(j))) {
						ContinuousItem itemContinuoCurrent = (ContinuousItem) current.getItem(i);
						ContinuousItem itemContinuoExamined = (ContinuousItem) examined.getItem(j);
						if ((itemContinuoCurrent.getInf() == itemContinuoExamined.getInf())
								& (itemContinuoCurrent.getSup() == itemContinuoExamined.getSup())) {

							count++;
						}
					}
				}

			}

			if (count == current.getPatternLength()) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Elimina itemSet con supporto identico
	 */
	public void prunePermutations(LinkedList<FrequentPattern> outFP) {

		for (int i = 0; i < outFP.size(); i++) {
			for (int j = i + 1; j < outFP.size(); j++) {
				if (isPermutation(outFP.get(i), outFP.get(j))
						& (outFP.get(i).getSupport() == outFP.get(j).getSupport())) {
					outFP.remove(j);

				}
			}
		}

	}

	/**
	 * Elimina pattern frequenti di lunghezza 1
	 */
	public void pruneSingle(LinkedList<FrequentPattern> outFP) {
		Iterator<FrequentPattern> i = outFP.iterator();
		while (i.hasNext()) {
			if (i.next().getPatternLength() == 1) {
				i.remove();
			}
		}
	}
}
