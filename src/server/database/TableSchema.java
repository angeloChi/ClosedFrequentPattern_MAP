package server.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Modella una tabella di un database relazionale, utilzzando la classe membro
 * Column.
 * 
 * @author Angelo, Simone, Antonio
 * 
 */
public class TableSchema {

	public class Column {
		private String name;
		private String type;

		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		public String getColumnName() {
			return this.name;
		}

		public boolean isNumber() {
			return type.equals("number");
		}

		public String toString() {
			return name + ":" + type;
		}
	}

	List<Column> tableSchema = new ArrayList<Column>(); // Lista delle colonne
														// della tabella

	public TableSchema(String tableName) throws SQLException { // Crea lo
																// schemadella
																// tabella
																// tableName
		HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();

		mapSQL_JAVATypes.put("CHAR", "string");
		mapSQL_JAVATypes.put("VARCHAR", "string");
		mapSQL_JAVATypes.put("LONGVARCHAR", "string");
		mapSQL_JAVATypes.put("BIT", "string");
		mapSQL_JAVATypes.put("SHORT", "number");
		mapSQL_JAVATypes.put("INT", "number");
		mapSQL_JAVATypes.put("LONG", "number");
		mapSQL_JAVATypes.put("FLOAT", "number");
		mapSQL_JAVATypes.put("DOUBLE", "number");

		Connection conn = DBAccess.getConnection();

		DatabaseMetaData meta = conn.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {

			if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(
						new Column(res.getString("COLUMN_NAME"), mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
		}
		res.close();
	}

	/**
	 * Restituisce il numero dei campi della tabella
	 * 
	 * @return intero
	 */
	public int getNumberOfAttributes() {
		return this.tableSchema.size();
	}

	/**
	 * Restituisce la colonna in posizione index della tabella
	 * 
	 * @param index
	 * @return column
	 */
	public Column getColumn(int index) {
		return this.tableSchema.get(index);
	}
}
