package com.lazimisha.utils.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DBCreateTable {

	private static final String TILDE = "`";

	public static PreparedStatement preparedStatementCreateTable( PreparedStatement preparedStatement,
			Connection connection, String sqlCreateTable ) {
		PreparedStatement pst = preparedStatement;
		Connection c = connection;

		try {
			pst = c.prepareStatement( sqlCreateTable );
			int result = pst.executeUpdate();
			System.out.println( "table creation: " + result );
		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			closePreparedStatementAndConnectionOnTableCreation( pst, c );
		}
		return pst;
	}

	private static void closePreparedStatementAndConnectionOnTableCreation( PreparedStatement preparedStatement,
			Connection connection ) {

		try {

			if ( preparedStatement != null ) {
				preparedStatement.close();
			}

			if ( connection != null ) {
				connection.close();
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		}

	}

	public static String createTableAnnotation( Class < ? > classOfTheAnnotatedFields ) {

		Field [ ] fields = classOfTheAnnotatedFields.getDeclaredFields();

		Map < String, Field > fieldList = new LinkedHashMap <>();

		for ( Field f : fields ) {
			// System.out.println( "field: " + f.getName() );
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

				createTableStrList.add( a.getKey() + " " + myAnnotation.tableColumnDefinition() );

				// System.out.println( a.getKey() + " " + myAnnotation.tableColumnDefinition()
				// );
			}

		}

		String createTableStr = "CREATE TABLE IF NOT EXISTS ".concat( TILDE )
				.concat( classOfTheAnnotatedFields.getSimpleName() ).concat( TILDE ).concat( "\n" ).concat( "(" );

		for ( String s : createTableStrList ) {

			createTableStr = createTableStr.concat( s ).concat( ",\n" );

		}

		int len = createTableStr.length();

		String allFKs = foreignKeyConstraint( classOfTheAnnotatedFields );

		String allChars = "";

		if ( allFKs != "" && allFKs != null ) {

			for ( int i = 0; i < len - 2; i++ ) {
				allChars = allChars.concat( String.valueOf( createTableStr.charAt( i ) ) );
			}

		} else {

			for ( int i = 0; i < len - 1; i++ ) {
				allChars = allChars.concat( String.valueOf( createTableStr.charAt( i ) ) );
			}

		}

		createTableStr = allChars;

		if ( allFKs != "" && allFKs != null ) {
			createTableStr = createTableStr.concat( ",\n" ).concat( allFKs );
		} else {
			createTableStr = createTableStr.concat( ")" );
		}

		String allChars2 = "";
		int len2 = createTableStr.length();

		for ( int i = 0; i < len2 - 2; i++ ) {
			allChars2 = allChars2.concat( String.valueOf( createTableStr.charAt( i ) ) );
		}

		createTableStr = allChars2.concat( ")" ).concat( "\n" ).concat( "ENGINE = InnoDB" ).concat( ";" );

		System.out.println( "\n" + createTableStr.concat( "\n" ) );

		return createTableStr;

	}

	private static String foreignKeyConstraint( Class < ? > classOfTheAnnotatedFields ) {

		System.out.println();
		System.out.println();

		Field [ ] fields = classOfTheAnnotatedFields.getDeclaredFields();

		Map < String, Field > fieldList = new LinkedHashMap <>();

		for ( Field f : fields ) {
			fieldList.put( f.getName(), f );
		}

		Map < String, Annotation > annotationList = new LinkedHashMap <>();
		for ( Entry < String, Field > f : fieldList.entrySet() ) {
			annotationList.put( f.getValue().getName(),
					f.getValue().getAnnotation( AnnotationTableDefinitionForeignKey.class ) );
		}

		List < String > allForeignKeys = new ArrayList <>();

		String foreignKeys = null;

		for ( Map.Entry < String, Annotation > a : annotationList.entrySet() ) {

			if ( a.getValue() instanceof AnnotationTableDefinitionForeignKey ) {

				AnnotationTableDefinitionForeignKey myAnnotation = ( AnnotationTableDefinitionForeignKey ) a.getValue();

				foreignKeys = "".concat( myAnnotation.foreingKey() )

						.concat( "(" )

						.concat( TILDE )

						.concat( a.getKey() )

						.concat( TILDE )

						.concat( ")" )

						.concat( " REFERENCES " )

						.concat( myAnnotation.referenceClass().getSimpleName() )

						.concat( " (`id`) " )

						.concat( " " ).concat( myAnnotation.onUpdate() )

						.concat( " " ).concat( myAnnotation.onDelete() );

				allForeignKeys.add( foreignKeys );

			}
		}

		String allFKs = "";
		for ( String s : allForeignKeys ) {
			allFKs = allFKs.concat( s ).concat( ",\n" );
		}

		if ( allFKs != "" && allFKs != null ) {
			return "" + allFKs;
		} else {
			return null;
		}
	}

}
