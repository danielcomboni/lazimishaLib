package com.lazimisha.utils.dbutils.dbfactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtilsDateActions {

	public static final String CURRENT_SYSTEM_DATE = "SELECT CURDATE() systemDate";
	public static final String DATE_NOW = "SELECT NOW() systemDate";

	
	public static String getDateValue( String theDateValueDesired ) {

		String theDateValue = null;

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtilsConnectivity.getConnection();
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement( theDateValueDesired );
			resultSet = preparedStatement.executeQuery();
			if ( resultSet.next() ) {
				theDateValue = resultSet.getString( "systemDate" );
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtilsConnectivity.closePreparedStatementAndConnectionOnNewInsertion( preparedStatement, connection );
		}

		return theDateValue;
	}

	public static void main( String [ ] args ) {
		String date = getDateValue( DATE_NOW );
		System.out.println( "date: " + date );
	}

}
