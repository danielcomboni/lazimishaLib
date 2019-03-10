package com.lazimisha.utils.dbutils.dbfactory;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GivePreparedStatementValuesBytePhoto {

	private GivePreparedStatementValuesBytePhoto() {

	}

	private static Map < Integer, Field > byteFields( List < Field > fields ) {
		Map < Integer, Field > fs = new LinkedHashMap <>();
		for ( int i = 0; i < fields.size(); i++ ) {
			if ( fields.get( i ).getType() == byte [ ].class ) {
				fs.put( i, fields.get( i ) );
			}

		}
		return fs;
	}

	public static void giveBytePreparedStatementValues( PreparedStatement preparedStatement, List < Field > fields ) {
		Map < Integer, Field > fs = byteFields( fields );
		// System.out.println( "inside byte: " );
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
				preparedStatement.setBinaryStream( paramIndex, ImagePath.getImageInputStreamFinal() );
				// System.out.println( "byet---: " + preparedStatement.toString() );
			} catch ( Exception e ) {
				e.printStackTrace();
			}

		}

	}

}
