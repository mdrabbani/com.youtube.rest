package com.youtube.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.sql.ResultSet;

import org.owasp.esapi.ESAPI;

/**
 * This utility will convert a database data into JSON format
 * @author mdrabbani
 *
 */
public class ToJSON {
	/**
	 * This will convert database records into a JSON Array
	 * Simply pas in a ResultSet from a database connection and it
	 * loop through and return a JSON array
	 * 
	 * @param rs - database ResultSet
	 * @return - JSON array
	 * @throws - Exception
	 */
	public JSONArray toJSONArray(ResultSet rs) throws Exception {
		
		JSONArray json = new JSONArray();
		String temp = null;
		
		try {
			// we will need column names, this will save the table meta-data like col name
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			
			// loop through the ResultSet
			while (rs.next()) {
				
				// figure out how many columns are there
				int numColumns = rsmd.getColumnCount();
				// each row in the ResultSet will be converted to a JSON object
				JSONObject obj = new JSONObject();
				
				// loop through all the columns and place them into the JSON Object
				for (int i=1; i < numColumns + 1; i++) {
					
					String column_name = rsmd.getColumnName(i);
					
					if (rsmd.getColumnType(i)==java.sql.Types.ARRAY) {
						obj.put(column_name, rs.getArray(column_name));
						/*Debug*/ System.out.println("ToJSON: ARRAY");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.BIGINT) {
						obj.put(column_name, rs.getInt(column_name));
						/*Debug*/ System.out.println("ToJSON: BIGINT");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.BOOLEAN) {
						obj.put(column_name, rs.getBoolean(column_name));
						/*Debug*/ System.out.println("ToJSON: BOOLEAN");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.BLOB) {
						obj.put(column_name, rs.getBlob(column_name));
						/*Debug*/ System.out.println("ToJSON: BLOB");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.DOUBLE) {
						obj.put(column_name, rs.getDouble(column_name));
						/*Debug*/ System.out.println("ToJSON: DOUBLE");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.FLOAT) {
						obj.put(column_name, rs.getFloat(column_name));
						/*Debug*/ System.out.println("ToJSON: FLOAT");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.INTEGER) {
						obj.put(column_name, rs.getInt(column_name));
						/*Debug*/ System.out.println("ToJSON: INTEGER");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.NVARCHAR) {
						obj.put(column_name, rs.getNString(column_name));
						/*Debug*/ System.out.println("ToJSON: NVARCHAR");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.VARCHAR) {
						
						temp = rs.getString(column_name);
						temp = ESAPI.encoder().canonicalize(temp);
						temp = ESAPI.encoder().encodeForHTML(temp);
						obj.put(column_name, temp);
						//obj.put(column_name, rs.getString(column_name));
						// /*Debug*/ System.out.println("ToJSON: VARCHAR");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.TINYINT) {
						obj.put(column_name, rs.getInt(column_name));
						/*Debug*/ System.out.println("ToJSON: TINYINT");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.SMALLINT) {
						obj.put(column_name, rs.getInt(column_name));
						/*Debug*/ System.out.println("ToJSON: SMALLINT");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.DATE) {
						obj.put(column_name, rs.getDate(column_name));
						/*Debug*/ System.out.println("ToJSON: DATE");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP) {
						obj.put(column_name, rs.getTimestamp(column_name));
						/*Debug*/ System.out.println("ToJSON: TIMESTAMP");
					}
					else if (rsmd.getColumnType(i)==java.sql.Types.NUMERIC) {
						obj.put(column_name, rs.getBigDecimal(column_name));
						/*Debug*/ System.out.println("ToJSON: NUMERIC");
					}
					else {
						obj.put(column_name, rs.getObject(column_name));
						/*Debug*/ System.out.println("ToJSON: Object" + column_name);
					}
				}// end for each
				json.put(obj);
			} //end while
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return json; // return JSON Array
	}
}
