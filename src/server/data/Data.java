package server.data;

import java.util.*;
import java.sql.*;

import server.mining.*;

import server.database.*;
import server.database.Table_Data.TupleData;

/**
 * Insieme delle transazioni
 * 
 * @author Angelo, Simone, Antonio
 *
 */
public class Data {
	/**
	 * Una matrice nXm di tipo Object che contiene l'insieme di transazioni ed è
	 * organizzato come numero di transazioni X numero di attributi
	 */
	private Object data[][];

	/** Cardinalità dell’insieme di transazioni */
	private int numberOfExamples;

	/** Oggetto per modellare un generico attributo */
	private List<Attribute> attributeSet;

	/** Aggregazione */
	Attribute attribute;

	/**
	 * Costruttore, popola la matrice Avvalora l'attributo attributeSet
	 */

	public Data(String name_table) {

		try {

			this.attributeSet = new LinkedList<Attribute>();
			TableSchema table_schema = new TableSchema(name_table);
			Table_Data table_data = new Table_Data();

			for (int i = 0; i < table_schema.getNumberOfAttributes(); i++) {// ciclo
																			// per
																			// popolare
																			// attributeSet
				List<Object> lista = table_data.getColumnValues(name_table, table_schema.getColumn(i)); // lista
																										// di
																										// transazioni
																										// distinti
				String column_name = table_schema.getColumn(i).getColumnName();// nome
																				// colonna
				String[] attributo = new String[lista.size()]; // array che
																// conterà
																// valori
																// dell'attributo
																// distinti

				if (!(table_schema.getColumn(i).isNumber())) {
					Iterator<Object> it = lista.iterator();
					int k = 0;
					while (it.hasNext()) {
						attributo[k] = (String) it.next();
						k++;
					}
					this.attributeSet.add(new DiscreteAttribute(column_name, i, attributo));
				} else if (table_schema.getColumn(i).isNumber()) {
					TableSchema.Column column = table_schema.getColumn(i);
					float min = (float) table_data.getAggregateColumnValue(name_table, column, QUERY_TYPE.MIN);
					float max = (float) table_data.getAggregateColumnValue(name_table, column, QUERY_TYPE.MAX);
					ContinuousAttribute continuousAttribute = new ContinuousAttribute(column_name, i, min, max);
					this.attributeSet.add(continuousAttribute);

				}
			}

			// matrice
			this.numberOfExamples = (table_data.getTransazioni(name_table).size()
					/ table_schema.getNumberOfAttributes());

			data = new Object[this.numberOfExamples][table_schema.getNumberOfAttributes()];

			List<TupleData> listaTuple = table_data.getTransazioni(name_table);

			Iterator<TupleData> it = listaTuple.iterator();
			while (it.hasNext()) {
				for (int i = 0; i < this.numberOfExamples; i++) {
					for (int j = 0; j < table_schema.getNumberOfAttributes(); j++) {
						Object x = it.next().tuple.get(0);
						data[i][j] = x;

					}
				}
			}

		} catch (SQLException exc) {
			System.err.println("Errore table SQL");
		}
	}

	/**
	 * Restituisce il valore del membro numberOfExamples numero di transazioni
	 * (14)
	 * 
	 * @return numberOfExamples
	 */
	public int getNumberOfExamples() {
		return this.numberOfExamples;
	}

	/**
	 * Restituisce la cardinalità del membro attributeSet numero attributi(4)
	 * 
	 * @return attributeSet.size
	 */
	public int getNumberOfAttributes() {
		return this.attributeSet.size();
	}

	/**
	 * Restituisce il valore dell'attributo attributeIndex per la transazione
	 * exampleIndex
	 * 
	 * @param exampleIndex
	 * @param attributeIndex
	 * @return data.[exampleIndex][attributeIndex]
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return this.data[exampleIndex][attributeIndex];
	}

	/**
	 * Restituisce l'elemento in posizione index
	 * 
	 * @param index
	 * @return attributeSet.get(index)
	 */
	public Attribute getAttribute(int index) {
		return this.attributeSet.get(index);
	}

	/**
	 * Legge i valori della matrice e li concatena in un oggetto String
	 */
	public String toString() {
		int i = 1;
		String s = new String();
		for (Object[] u : data) {
			s += (i) + ":";
			i = i + 1;
			for (Object elem : u) {
				s += elem + ",";
			}
			s += "\n";
		}
		return s;
	}
}
