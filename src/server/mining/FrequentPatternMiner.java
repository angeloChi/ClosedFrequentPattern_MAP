
package server.mining;

import server.data.*;
import server.utility.*;
import java.util.Iterator;


/**
 *@author Angelo, Simone, Antonio
 *
 *Scoperta di pattern frequenti 
 */

public class FrequentPatternMiner {
	/**
	 * Genera i pattern Frequenti
	 * 
	 * @param data
	 * @param minSup
	 * @return lista linkata con pattern frequenti
	 * @throws EmptySetException
	 */
	public static LinkList frequentPatternDiscovery(Data data, float minSup) throws EmptySetException {
		Queue<FrequentPattern> fpQueue = new Queue<FrequentPattern>();
		LinkList outputFP = new LinkList();

		if (data.getNumberOfExamples() == 0) { // Se la matrice è vuota...
			throw new EmptySetException();
		}

		Class discreteAttributeClass = DiscreteAttribute.class; // letterale di classe
		Class continuousAttributeClass = ContinuousAttribute.class;

		for (int i = 0; i < data.getNumberOfAttributes(); i++) {
			Attribute currentAttribute = data.getAttribute(i); // genera item discreto k=1 con valori attributo discreto-valore discreto possibile di attributo

			if (discreteAttributeClass.isInstance(currentAttribute)) {

				for (int j = 0; j < ((DiscreteAttribute) currentAttribute).getNumberOfDistinctValues(); j++) {
					DiscreteItem item = new DiscreteItem((DiscreteAttribute) currentAttribute,
							((DiscreteAttribute) currentAttribute).getValue(j));
					FrequentPattern fp = new FrequentPattern(); // genera pattern con questo item e  calcola il supporto

					fp.addItem(item);
					fp.setSupport(FrequentPatternMiner.computeSupport(data, fp));
					if (fp.getSupport() >= minSup) {
						fpQueue.enqueue(fp);
						outputFP.add(fp);
					}
				}
			} else if (continuousAttributeClass.isInstance(currentAttribute)) {
				ContinuousAttribute attributo_continuo = (ContinuousAttribute) currentAttribute;
				Iterator<Float> iteratore = attributo_continuo.iterator();
				float min = attributo_continuo.getMin();
				while (iteratore.hasNext()) {
					float max = iteratore.next();
					Interval interval = new Interval(min, max);
					min = max;
					ContinuousItem item_continuo = new ContinuousItem(attributo_continuo, interval);
					FrequentPattern fp = new FrequentPattern();
					fp.addItem(item_continuo);
					fp.setSupport(FrequentPatternMiner.computeSupport(data, fp));

					if (fp.getSupport() >= minSup) {
						fpQueue.enqueue(fp);
						outputFP.add(fp);
					}

				}
			}

		}
		outputFP = expandFrequentPatterns(data, minSup, fpQueue, outputFP);
		return outputFP;
	}

	/**
	 * Metodo che amplia i pattern frequenti e ne verifica il supporto
	 * 
	 * @param data
	 * @param minSup
	 * @param fpQueue
	 * @param outputFP
	 * @return listaLinkata con pattern frequent a k>1
	 */
	private static LinkList expandFrequentPatterns(Data data, float minSup, Queue<FrequentPattern> fpQueue,
			LinkList outputFP) {

		Class discreteAttributeClass = DiscreteAttribute.class;
		Class continuousAttributeClass = ContinuousAttribute.class;
		while (!fpQueue.isEmpty()) {
			FrequentPattern fp = (FrequentPattern) fpQueue.first();// sono i pattern fp di discovery aggiunti nella coda, tutti i vari valori distinti dei vari attributi
			fpQueue.dequeue();

			for (int i = 0; i < data.getNumberOfAttributes(); i++) {
				Attribute current_attribute = data.getAttribute(i);
				boolean found = false;
				for (int j = 0; j < fp.getPatternLength(); j++)
					if (fp.getItem(j).getAttribute().equals(data.getAttribute(i))) {
						found = true;
						break;
					}
				if (!found) {
					if (discreteAttributeClass.isInstance(current_attribute)) {
						for (int j = 0; j < ((DiscreteAttribute) data.getAttribute(i))
								.getNumberOfDistinctValues(); j++) {
							DiscreteItem item = new DiscreteItem((DiscreteAttribute) data.getAttribute(i),
									((DiscreteAttribute) (data.getAttribute(i))).getValue(j));
							FrequentPattern newFP = FrequentPatternMiner.refineFrequentPattern(fp, item); // generate
																											// refinement
							newFP.setSupport(FrequentPatternMiner.computeSupport(data, newFP));

							if (newFP.getSupport() >= minSup) {
								fpQueue.enqueue(newFP);
								outputFP.add(newFP);
							}

						}
					} else if (continuousAttributeClass.isInstance(current_attribute)) {
						ContinuousAttribute attributo_continuo = (ContinuousAttribute) current_attribute;
						Iterator<Float> iteratore = attributo_continuo.iterator();
						float min = attributo_continuo.getMin();
						while (iteratore.hasNext()) {
							float max = iteratore.next();
							Interval interval = new Interval(min, max);
							min = max;
							ContinuousItem item_continuo = new ContinuousItem(attributo_continuo, interval);
							FrequentPattern newFP = FrequentPatternMiner.refineFrequentPattern(fp, item_continuo);
							newFP.setSupport(FrequentPatternMiner.computeSupport(data, newFP));
							if (newFP.getSupport() >= minSup) {
								fpQueue.enqueue(newFP);
								outputFP.add(newFP);
							}

						}
					}

				}

			}

		}
		return outputFP;
	}

	/**
	 * Pattern di cui calcolare il supporto
	 * 
	 * @param data
	 * @param FP
	 * @return
	 */
	public static float computeSupport(Data data, FrequentPattern FP) {
		int x = 0;
		int cont;
		for (int i = 0; i < data.getNumberOfExamples(); i++) {
			cont = 0;
			for (int j = 0; j < FP.getPatternLength(); j++) {
				for (int y = 0; y < data.getNumberOfAttributes(); y++) {
					Attribute attributo = data.getAttribute(y);
					Item current_item = FP.getItem(j);
					if (current_item.getAttribute().getName().equals(attributo.getName())
							&& current_item.checkItemCondition(data.getAttributeValue(i, y)))

						cont++;

				}
				if (cont == FP.getPatternLength()) {
					x++;
				}
			}

		}
		return (float) x / data.getNumberOfExamples();
	}

	/**
	 * Aggiunge l'item argomento agli item già contenuti in FP
	 * 
	 * @param FP
	 * @param item
	 * @return Pattern frequente
	 */
	static FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item) {
		FrequentPattern newFP = new FrequentPattern();
		for (int i = 0; i < FP.getPatternLength(); i++) {
			newFP.addItem(FP.getItem(i));
		}
		newFP.addItem(item);
		return newFP;
	}
}
