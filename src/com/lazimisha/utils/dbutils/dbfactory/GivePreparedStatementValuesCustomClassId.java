package com.lazimisha.utils.dbutils.dbfactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GivePreparedStatementValuesCustomClassId {

	private GivePreparedStatementValuesCustomClassId() {

	}

	private static Map < Integer, Field > customClassFields( List < Field > fields ) {
		Map < Integer, Field > fs = new LinkedHashMap <>();
		for ( int i = 0; i < fields.size(); i++ ) {

			if ( !fields.get( i ).getType().getClass().isPrimitive()
					&& !fields.get( i ).getType().getName().startsWith( "java." ) ) {
				fs.put( i, fields.get( i ) );
			}

		}
		return fs;
	}

	public static void giveCustomClassPreparedStatementValues( PreparedStatement preparedStatement,
			List < Field > fields, Map < Class < ? >, BigDecimal > mapOfObjectToClass, Object obj ) {
		Map < Integer, Field > fs = customClassFields( fields );
		Method method = null;
		for ( Map.Entry < Integer, Field > f : fs.entrySet() ) {
			try {
				System.out.println();
				/**
				 * get the name of the field in the model class
				 */
				String formatted = f.getValue().getName();

				/**
				 * extract first character
				 * 
				 * and
				 * 
				 * set first character to upper case
				 */
				String firstCharacter = String.valueOf( formatted.charAt( 0 ) ).toUpperCase();
				List < String > allChars = new ArrayList <>();
				for ( int i = 0; i < formatted.length(); i++ ) {
					allChars.add( String.valueOf( formatted.charAt( i ) ) );
				}

				/**
				 * concatenate the remaining characters to the first character
				 */
				String formatted2 = firstCharacter;
				allChars.remove( 0 );
				for ( String s : allChars ) {
					formatted2 = formatted2.concat( s );
				}

				int paramIndex = f.getKey().intValue() + 1;

				String methodName = "get".concat( formatted2 );
				method = obj.getClass().getDeclaredMethod( methodName );
				Object objectOfReferencedClass = method.invoke( obj );

				String methodName2 = "getId";
				method = objectOfReferencedClass.getClass().getDeclaredMethod( methodName2 );
				BigDecimal val = ( BigDecimal ) method.invoke( objectOfReferencedClass );

				preparedStatement.setBigDecimal( paramIndex, val );

			} catch ( SecurityException | IllegalArgumentException | SQLException | NoSuchMethodException
					| IllegalAccessException | InvocationTargetException e ) {
				e.printStackTrace();
			}
		}
	}

	private static Object classObject;

	public static Object getClassObject() {
		return classObject;
	}

	public static void setClassObject( Object classObject ) {
		GivePreparedStatementValuesCustomClassId.classObject = classObject;
	}

}
