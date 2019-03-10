package com.lazimisha.utils.dbutils.dbfactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lazimisha.utils.annotation.AnnotationTableDefinition;
import com.lazimisha.utils.crud.TableColumnDefinitions;

public class DBUtilsStoredProcedureInsertion {

	private DBUtilsStoredProcedureInsertion() {

	}

	private static final String TILDE = "`";

	public static int createStoredProcedureInsertion( Class < ? > classOfTheAnnotatedFields ) {
		int result = 0;

		String sql = "CREATE PROCEDURE ";

		Field [ ] fields = classOfTheAnnotatedFields.getDeclaredFields();

		Map < String, Field > fieldList = new LinkedHashMap <>();

		for ( Field f : fields ) {
			fieldList.put( f.getName(), f );
		}

		Map < String, Annotation > annotationList = new LinkedHashMap <>();
		for ( Entry < String, Field > f : fieldList.entrySet() ) {
			annotationList.put( f.getValue().getName(), f.getValue().getAnnotation( AnnotationTableDefinition.class ) );
		}

		List < String > createTableStrList = new ArrayList <>();

		for ( Map.Entry < String, Annotation > a : annotationList.entrySet() ) {

			if ( a.getValue() instanceof AnnotationTableDefinition ) {

				AnnotationTableDefinition myAnnotation = ( AnnotationTableDefinition ) a.getValue();

				if ( !myAnnotation.tableColumnDefinition().equalsIgnoreCase( TableColumnDefinitions.ID_AUTO_INC ) ) {
					createTableStrList.add( a.getKey() + " " + myAnnotation.tableColumnDefinition() );
				}

			}

		}

		String createTableStr = "CREATE TABLE IF NOT EXISTS ".concat( TILDE )
				.concat( classOfTheAnnotatedFields.getSimpleName() ).concat( TILDE ).concat( "\n" ).concat( "(" );

		for ( String s : createTableStrList ) {

			createTableStr = createTableStr.concat( s ).concat( ",\n" );

		}

		String procName = "insProc_" + classOfTheAnnotatedFields.getSimpleName();
		String tableName = classOfTheAnnotatedFields.getSimpleName();

		String procParams = createTableStrList.toString();

		procParams = "( IN ".concat( procParams );

		procParams = procParams.replace( "[", "" );

		procParams = procParams.replace( "]", "" );

		procParams = procParams.replaceAll( "NOT NULL", "" );

		procParams = procParams.replaceAll( ",", ", IN" );

		sql = sql.concat( procName ).concat( " " ).concat( procParams ).concat( " )" );

		sql = sql.concat( "\n BEGIN INSERT INTO " ).concat( tableName );

		sql = sql.concat( " \n(" );

		String values = " \nVALUES \n(";

		for ( Map.Entry < String, Annotation > a : annotationList.entrySet() ) {

			if ( a.getValue() instanceof AnnotationTableDefinition ) {

				AnnotationTableDefinition myAnnotation = ( AnnotationTableDefinition ) a.getValue();

				if ( !myAnnotation.tableColumnDefinition().equalsIgnoreCase( TableColumnDefinitions.ID_AUTO_INC ) ) {
					createTableStrList.add( a.getKey() + " " + myAnnotation.tableColumnDefinition() );

					sql = sql.concat( a.getKey() ).concat( " , " );
					values = values.concat( a.getKey() ).concat( " , " );

				}

			}

		}

		sql = sql.concat( ")" );

		sql = sql.replace( ", )", ")" );

		values = values.concat( ")" );

		values = values.replace( ", )", ")" );

		sql = sql.concat( values ).concat( ";END" );

		System.out.println( sql );

		Connection myConn = DBUtilsConnectivity.getConnection();
		PreparedStatement mySt = null;

		try {
			mySt = myConn.prepareStatement( sql );
			result = mySt.executeUpdate();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtilsConnectivity.closePreparedStatementAndConnectionOnNewInsertion( mySt, myConn );
		}

		return result;
	}

}
