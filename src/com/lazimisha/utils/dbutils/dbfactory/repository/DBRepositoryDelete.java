package com.lazimisha.utils.dbutils.dbfactory.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lazimisha.utils.dbutils.dbfactory.DBUtilsConnectivity;

public class DBRepositoryDelete {

	public static int deleteById( Class < ? > cls, BigDecimal id ) {
		int result = 0;
		PreparedStatement preparedStatement = null;
		Connection connection = DBUtilsConnectivity.getConnection();
		try {
			connection.setAutoCommit( false );
			preparedStatement = connection
					.prepareStatement( "DELETE FROM ".concat( cls.getSimpleName() ).concat( " WHERE id = ?" ) );
			preparedStatement.setBigDecimal( 1, id );
			System.out.println( "delete statement: " + preparedStatement.toString() );
			result = preparedStatement.executeUpdate();
			if ( result == 1 ) {
				connection.commit();
			} else {
				connection.rollback();
			}
			connection.setAutoCommit( true );
		} catch ( SQLException e ) {
			e.printStackTrace();
		}
		return result;
	}

}
