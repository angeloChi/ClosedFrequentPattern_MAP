package server.database;

import java.util.*;

import server.database.TableSchema.Column;

import java.sql.*;

/**
 * 
 * @author Angelo, Simone, Antonio
 * 
 *         Definire la classe Table_Data che modella la gestione dei dati
 *         contenuti in una tabella. Al suo interno verrà definita la classe
 *         Tuple_Data come membro e utilizzata per modellare una tupla della
 *         tabella.
 */
public class Table_Data {
	public class TupleData {

		public List<Object> tuple = new ArrayList<Object>();

		public String toString() {
			String value = " ";
			Iterator<Object> it = tuple.iterator();
			while (it.hasNext())
				value += (it.next().toString() + " ");
			return value;
		}
	}

	/**
	 * Interroga la tabella passata in input. Dopo aver effettuato la selezione
	 * di tutte le tuple della tabella, per ogni tupla controlla se un elemento
	 * è String oppure Float e lo aggiunge alla tupla corrente. Infine aggiunge
	 * la tupla alla lista di tuple da restituire in uscita.
	 * 
	 * @param table
	 * @return lista di tuple
	 * @throws SQLException
	 */
	public List<TupleData> getTransazioni(String table) throws SQLException {
		Statement st = null;
		ResultSet rs = null;

		List<TupleData> trans = new ArrayList<TupleData>();
		Connection conn = DBAccess.getConnection();

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM " + table); // Seleziono tutte
															// le tuple della
															// tabella
			ResultSetMetaData rsmd = rs.getMetaData(); // Per reperire
														// informazioni...

			while (rs.next()) { // finchè ci sono tuple...
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					TupleData td = new TupleData();

					if (rsmd.getColumnType(i) == 7) // Float
						td.tuple.add(rs.getFloat(i));
					else if (rsmd.getColumnType(i) == 12) // Stringhe
						td.tuple.add(rs.getString(i));
					trans.add(td);
				}
			}
		} finally {
			rs.close();
			st.close();
		}
		return trans;
	}

	/**
	 * Interroga la tabella passata in input. Seleziona dalla tabella solo la
	 * colonna passata in input restituendo i valori distinti dell'attributo
	 * column
	 * 
	 * @param table
	 * @param column
	 * @return lista di valori distinti dell'attributo
	 * @throws SQLException
	 */
	public List<Object> getColumnValues(String table, Column column) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		Connection conn = DBAccess.getConnection();
		TupleData td = new TupleData();
		try {
			st = conn.createStatement();

			String sql = "select DISTINCT (" + column.getColumnName() + ")  FROM " + table;
			rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) { // finchè ci sono tuple...
				if (rsmd.getColumnType(1) == 7)
					td.tuple.add(rs.getFloat(1));
				else if (rsmd.getColumnType(1) == 12)
					td.tuple.add(rs.getString(1));

			}
		} finally {
			rs.close();
			st.close();
		}
		return td.tuple;
	}

	/**
	 * Interroga la tabella passata in input. Seleziona dalla tabella solo la
	 * colonna passata in input restituendo i valori aggregati dell'attributo
	 * column rispetto all'argomento aggregate
	 * 
	 * @param table
	 * @param column
	 * @param aggregate
	 * @return Object (min o max)
	 * @throws SQLException
	 */

	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		Connection conn = DBAccess.getConnection();
		TupleData td = new TupleData();

		try {
			st = conn.createStatement();

			if (aggregate.ordinal() == 0) {

				rs = st.executeQuery("SELECT MIN( " + column.getColumnName() + ") FROM " + table);
				ResultSetMetaData rsmd = rs.getMetaData();

				while (rs.next()) {
					if (rsmd.getColumnType(1) == 7)
						td.tuple.add(rs.getFloat(1));
					else if (rsmd.getColumnType(1) == 12)
						td.tuple.add(rs.getString(1));
				}
			} else if (aggregate.ordinal() == 1) {
				rs = st.executeQuery("SELECT MAX( " + column.getColumnName() + ") FROM " + table);
				ResultSetMetaData rsmd = rs.getMetaData();

				while (rs.next()) {
					if (rsmd.getColumnType(1) == 7)
						td.tuple.add(rs.getFloat(1));
					else if (rsmd.getColumnType(1) == 12)
						td.tuple.add(rs.getString(1));
				}
			}

		} finally {
			rs.close();
			st.close();
		}
		return td.tuple.get(0);
	}
}
