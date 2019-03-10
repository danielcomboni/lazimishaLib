package com.lazimisha.utils.dbutils.dbfactory;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lazimisha.utils.annotation.DBCreateTable;
import com.lazimisha.utils.dbutils.dbfactory.insert.DBUtilsInsertion;
import com.lazimisha.utils.dbutils.dbfactory.insert.DBUtilsUpdate;

public class DBUtilsConnectivity {

	private DBUtilsConnectivity() {

	}

	/**
	 * returns connection to the database
	 * 
	 * @return {@link Connection}
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {

			Properties dbconn = new Properties();
			FileInputStream input = new FileInputStream( "dbconn.conn" );
			dbconn.load( input );
			Class.forName( dbconn.getProperty( "driver" ) );
			connection = DriverManager.getConnection( dbconn.getProperty( "url" ), dbconn.getProperty( "user" ),
					dbconn.getProperty( "password" ) );

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return connection;

	}

	/**
	 * 
	 * @param insertionSql
	 * @return
	 */

	public static PreparedStatement getPreparedStatement() {
		PreparedStatement preparedStatement = null;
		return preparedStatement;
	}

	private static Object classObject;

	public static Object getClassObject() {
		return classObject;
	}

	public static void setClassObject( Object classObject ) {
		DBUtilsConnectivity.classObject = classObject;
	}

	private static List < Class < ? > > clsCustoms;

	public static List < Class < ? > > getClsCustoms() {

		return clsCustoms;

	}

	public static List < Class < ? > > customsclassesForIds( Class < ? > cls ) {
		List < Class < ? > > clses = new ArrayList <>();

		if ( getClsCustoms() == null ) {
			clses.add( cls );
			setClsCustoms( clses );
		} else {
			getClsCustoms().add( cls );
		}
		return getClsCustoms();
	}

	private static Map < Class < ? >, BigDecimal > mapOfReferencedIDToClassesOfreference = new LinkedHashMap <>();

	public static Map < Class < ? >, BigDecimal > getMapOfReferencedIDToClassesOfreference() {
		return mapOfReferencedIDToClassesOfreference;
	}

	public static void setMapOfReferencedIDToClassesOfreference(
			Map < Class < ? >, BigDecimal > mapOfReferencedIDToClassesOfreference ) {
		DBUtilsConnectivity.mapOfReferencedIDToClassesOfreference = mapOfReferencedIDToClassesOfreference;
	}

	public static void setClsCustoms( List < Class < ? > > clsCustoms ) {
		DBUtilsConnectivity.clsCustoms = clsCustoms;
	}

	private static boolean checkTableExistence( Class < ? > clsTableName ) {
		String query = "SELECT 1 FROM ".concat( clsTableName.getSimpleName() ).concat( " LIMIT 1" );
		Connection connectionCreateTable = getConnection();
		PreparedStatement preparedStatementCreateTable = null;
		ResultSet rs = null;

		boolean test = false;

		try {
			preparedStatementCreateTable = connectionCreateTable.prepareStatement( query );
			rs = preparedStatementCreateTable.executeQuery();
		} catch ( SQLException e ) {
			test = true;
			// e.printStackTrace();
		} finally {
			DBUtilsConnectivity.closeResultSetPreparedStatementAndConnectionOnNewInsertion( rs,
					preparedStatementCreateTable, connectionCreateTable );
		}
		return test;
	}

	public static void createTable( Class < ? > classOfTheAnnotatedFields ) {

		checkTableExistence( classOfTheAnnotatedFields );

		if ( checkTableExistence( classOfTheAnnotatedFields ) ) {

			/**
			 * creating the table begins
			 */

			Class < ? > cls = loadClassAtRunTime( classOfTheAnnotatedFields );

			String sqlCreateTable = DBCreateTable.createTableAnnotation( cls );
			Connection connectionCreateTable = getConnection();
			PreparedStatement preparedStatementCreateTable = null;

			DBCreateTable.preparedStatementCreateTable( preparedStatementCreateTable, connectionCreateTable,
					sqlCreateTable );
			/**
			 * end table creation
			 */

		}

	}

	public static int saveNew( Class < ? > classOfTheAnnotatedFields ) {

		int result = 0;

		Class < ? > cls = loadClassAtRunTime( classOfTheAnnotatedFields );

		createTable( classOfTheAnnotatedFields );

		Connection connection = getConnection();
		PreparedStatement preparedStatement = DBUtilsInsertion.insertViaPreparedStatement( cls, connection,
				getClassObject(), getMapOfReferencedIDToClassesOfreference() );

		try {

			connection.setAutoCommit( false );

			result = preparedStatement.executeUpdate();
			getGeneratedKey( preparedStatement );

			if ( result == 1 ) {
				connection.commit();
			} else {
				connection.rollback();
			}

			connection.setAutoCommit( true );

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			closePreparedStatementAndConnectionOnNewInsertion( preparedStatement, connection );
		}
		return result;
	}

	private static BigDecimal generatedKey;

	public static BigDecimal getGeneratedKey() {
		return generatedKey;
	}

	public static void setGeneratedKey( BigDecimal generatedKey ) {
		DBUtilsConnectivity.generatedKey = generatedKey;
	}

	public static BigDecimal getGeneratedKey( PreparedStatement ps ) {

		BigDecimal generatedKey = BigDecimal.ZERO;
		ResultSet rs = null;
		try {

			rs = ps.getGeneratedKeys();
			if ( rs != null && rs.next() ) {

				generatedKey = rs.getBigDecimal( 1 );

				setGeneratedKey( generatedKey );
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
		return generatedKey;
	}

	public static int update( Class < ? > classOfTheAnnotatedFields, BigDecimal id ) {

		int result = 0;

		Class < ? > cls = loadClassAtRunTime( classOfTheAnnotatedFields );

		Connection connection = getConnection();
		PreparedStatement preparedStatement = DBUtilsUpdate.updateViaPreparedStatement( cls, connection,
				getClassObject(), getMapOfReferencedIDToClassesOfreference(), id );

		try {

			connection.setAutoCommit( false );

			result = preparedStatement.executeUpdate();

			if ( result == 1 ) {
				connection.commit();
			} else {
				connection.rollback();
			}

			connection.setAutoCommit( true );

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			closePreparedStatementAndConnectionOnNewInsertion( preparedStatement, connection );
		}
		return result;
	}

	private static Class < ? > loadClassAtRunTime( Class < ? > cls ) {
		Class < ? > cls1 = null;
		try {
			cls1 = Class.forName( cls.getName() );
		} catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		return cls1;
	}

	public static void closePreparedStatementAndConnectionOnNewInsertion( PreparedStatement preparedStatement,
			Connection connection ) {

		try {

			if ( preparedStatement != null ) {
				preparedStatement.close();
				// System.out.println( "pst closed" );
			}

			if ( connection != null ) {
				connection.close();
				// System.out.println( "conn closed" );
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		}

	}

	public static void closeResultSetPreparedStatementAndConnectionOnNewInsertion( ResultSet resultSet,
			PreparedStatement preparedStatement, Connection connection ) {

		try {

			if ( resultSet != null ) {
				resultSet.close();
				// System.out.println( "rs set closed" );
			}

			if ( preparedStatement != null ) {
				preparedStatement.close();
				// System.out.println( "pst closed" );
			}

			if ( connection != null ) {
				connection.close();
				// System.out.println( "conn closed" );
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		}

	}

}
