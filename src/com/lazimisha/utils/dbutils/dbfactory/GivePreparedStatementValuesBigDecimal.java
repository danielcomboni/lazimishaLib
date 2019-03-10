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

public class GivePreparedStatementValuesBigDecimal {

	private GivePreparedStatementValuesBigDecimal() {

	}

	private static Map < Integer, Field > bigDecimalFields( List < Field > fields ) {
		Map < Integer, Field > fs = new LinkedHashMap <>();
		for ( int i = 0; i < fields.size(); i++ ) {

			if ( fields.get( i ).getType().equals( BigDecimal.class ) ) {
				fs.put( i, fields.get( i ) );
			}

		}
		return fs;
	}

	public static void giveBigDecimalPreparedStatementValues( PreparedStatement preparedStatement,
			List < Field > fields, Class < ? > classOfTheAnnotatedFields, Object obj ) {
		Map < Integer, Field > fs = bigDecimalFields( fields );
		Method method = null;
		for ( Map.Entry < Integer, Field > f : fs.entrySet() ) {
			try {
				String formatted = f.getValue().getName();
				String firstCharacter = String.valueOf( formatted.charAt( 0 ) ).toUpperCase();
				List < String > allChars = new ArrayList <>();
				for ( int i = 0; i < formatted.length(); i++ ) {
					allChars.add( String.valueOf( formatted.charAt( i ) ) );
				}
				String formatted2 = firstCharacter;
				allChars.remove( 0 );
				for ( String s : allChars ) {
					formatted2 = formatted2.concat( s );
				}
				int paramIndex = f.getKey().intValue() + 1;
				String methodName = "get".concat( formatted2 );
				method = classOfTheAnnotatedFields.getDeclaredMethod( methodName );
				BigDecimal val = ( BigDecimal ) method.invoke( obj );
				preparedStatement.setBigDecimal( paramIndex, val );
			} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | SQLException e ) {
				e.printStackTrace();
			}

		}

	}

}
