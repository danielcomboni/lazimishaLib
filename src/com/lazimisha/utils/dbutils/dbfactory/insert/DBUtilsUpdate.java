package com.lazimisha.utils.dbutils.dbfactory.insert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lazimisha.utils.annotation.AnnotationTableDefinition;
import com.lazimisha.utils.crud.TableColumnDefinitions;
import com.lazimisha.utils.dbutils.dbfactory.GivePreparedStatementUpdateIDValue;
import com.lazimisha.utils.dbutils.dbfactory.GivePreparedStatementValuesBigDecimal;
import com.lazimisha.utils.dbutils.dbfactory.GivePreparedStatementValuesBoolean;
import com.lazimisha.utils.dbutils.dbfactory.GivePreparedStatementValuesCustomClassId;
import com.lazimisha.utils.dbutils.dbfactory.GivePreparedStatementValuesString;
import com.lazimisha.utils.dbutils.dbfactory.TableNameObtainer;

public class DBUtilsUpdate {

	private DBUtilsUpdate() {

	}

	/**
	 * creates the sql statement for insertion
	 * 
	 * @param classOfInsertion
	 * @return
	 */
	public static String updateSql( Class < ? > classOfTheAnnotatedFields ) {

		return "UPDATE ".concat( TableNameObtainer.getTableName( classOfTheAnnotatedFields ) )
				.concat( getTableColumns( classOfTheAnnotatedFields ) ).concat( " WHERE id = ? " ).concat( ";" );
	}

	/**
	 * 
	 * @param classOfTheAnnotatedFields
	 * @return fields names whose types are determined and use them to make the
	 *         prepared statement entries
	 */
	private static List < Field > fieldsUsedForUpdate( Class < ? > classOfTheAnnotatedFields ) {
		List < Field > fields = new ArrayList <>();
		Field [ ] fieldArray = classOfTheAnnotatedFields.getDeclaredFields();
		for ( Field f : fieldArray ) {

			if ( listOfColumnNamesWithoutPrimaryKeyConstraint.contains( f.getName() ) ) {
				fields.add( f );
				System.out.println( f.getName() + ":" + f.getType().getSimpleName() );
			}

		}
		return fields;
	}

	public static PreparedStatement updateViaPreparedStatement( Class < ? > classOfTheAnnotatedFields,
			Connection connection, Object obj, Map < Class < ? >, BigDecimal > mapOfObjectsToClasses, BigDecimal id ) {

		getListOfColumnNamesWithoutPrimaryKeyConstraint( classOfTheAnnotatedFields );

		PreparedStatement preparedStatement = null;
		List < Field > fields = fieldsUsedForUpdate( classOfTheAnnotatedFields );

		try {

			preparedStatement = connection.prepareStatement( updateSql( classOfTheAnnotatedFields ) );
			GivePreparedStatementValuesBigDecimal.giveBigDecimalPreparedStatementValues( preparedStatement, fields,
					classOfTheAnnotatedFields, obj );

			GivePreparedStatementValuesString.giveStringPreparedStatementValues( preparedStatement, fields,
					classOfTheAnnotatedFields, obj );

			GivePreparedStatementValuesBoolean.giveBooleanPreparedStatementValues( preparedStatement, fields,
					classOfTheAnnotatedFields, obj );

			GivePreparedStatementValuesCustomClassId.giveCustomClassPreparedStatementValues( preparedStatement, fields,
					mapOfObjectsToClasses, obj );

			GivePreparedStatementUpdateIDValue.giveBigDecimalPreparedStatementUpdateID( preparedStatement,
					classOfTheAnnotatedFields, id );

			System.out.println( "id up: " + id );

			// getLastIDPreparedStatementValue( preparedStatement,
			// classOfTheAnnotatedFields, id );

			System.out.println( "pst update: " + preparedStatement.toString() );

		} catch ( SQLException e ) {
			e.printStackTrace();
		}

		return preparedStatement;
	}

	public static void getLastIDPreparedStatementValue( PreparedStatement preparedStatement,
			Class < ? > classOfTheAnnotatedFields, BigDecimal id ) {

		Field [ ] fs = classOfTheAnnotatedFields.getDeclaredFields();

		try {
			preparedStatement.setBigDecimal( fs.length, id );
		} catch ( SQLException e ) {
			e.printStackTrace();
		}

	}

	private static List < String > listOfColumnNamesWithoutPrimaryKeyConstraint = null;

	private static List < String > getListOfColumnNamesWithoutPrimaryKeyConstraint(
			Class < ? > classOfTheAnnotatedFields ) {
		Field [ ] fields = classOfTheAnnotatedFields.getDeclaredFields();
		Map < String, Field > fieldList = new LinkedHashMap <>();
		String sqlColumns = "";
		for ( Field f : fields ) {
			fieldList.put( f.getName(), f );
		}
		listOfColumnNamesWithoutPrimaryKeyConstraint = new ArrayList <>();
		Map < String, Annotation > annotationList = new LinkedHashMap <>();
		for ( Entry < String, Field > f : fieldList.entrySet() ) {
			annotationList.put( f.getValue().getName(), f.getValue().getAnnotation( AnnotationTableDefinition.class ) );
		}
		for ( Map.Entry < String, Annotation > a : annotationList.entrySet() ) {

			if ( a.getValue() instanceof AnnotationTableDefinition ) {
				AnnotationTableDefinition myAnnotation = ( AnnotationTableDefinition ) a.getValue();

				if ( !myAnnotation.tableColumnDefinition().equalsIgnoreCase( TableColumnDefinitions.ID_AUTO_INC ) ) {
					// System.out.println( "key..." + a.getKey() );
					listOfColumnNamesWithoutPrimaryKeyConstraint.add( a.getKey() );

				}

			}
		}
		for ( String s : listOfColumnNamesWithoutPrimaryKeyConstraint ) {
			sqlColumns = sqlColumns.concat( s ).concat( "," );
		}
		return listOfColumnNamesWithoutPrimaryKeyConstraint;
	}

	/**
	 * 
	 * @param classOfTheAnnotatedFields
	 * @return String of comma separated columns of the table
	 * 
	 */
	private static String getTableColumns( Class < ? > classOfTheAnnotatedFields ) {
		String sqlColumns = " SET ";
		for ( String s : listOfColumnNamesWithoutPrimaryKeyConstraint ) {

			sqlColumns = sqlColumns.concat( s ).concat( " = ?," );

		}
		int lengthOfColumns = sqlColumns.length();
		String sqlColumnsWithLastCommaRemoved = "";
		for ( int i = 0; i < lengthOfColumns - 1; i++ ) {
			sqlColumnsWithLastCommaRemoved = sqlColumnsWithLastCommaRemoved
					.concat( String.valueOf( sqlColumns.charAt( i ) ) );
		}
		sqlColumns = sqlColumnsWithLastCommaRemoved;
		return sqlColumns;
	}

	// private static String getValuesOfWildCardPlaceHolders( Class < ? >
	// classOfTheAnnotatedFields ) {
	// String wildCards = "";
	// for ( int i = 0; i < listOfColumnNamesWithoutPrimaryKeyConstraint.size(); i++
	// ) {
	// wildCards = wildCards.concat( "?" ).concat( "," );
	// }
	// int lengthOfWildCards = wildCards.length();
	// String wildCardsWithLastCommaRemoved = "";
	// for ( int i = 0; i < lengthOfWildCards - 1; i++ ) {
	// wildCardsWithLastCommaRemoved = wildCardsWithLastCommaRemoved
	// .concat( String.valueOf( wildCards.charAt( i ) ) );
	// }
	// wildCards = wildCardsWithLastCommaRemoved;
	// return "VALUES(".concat( wildCards );
	// }

}
