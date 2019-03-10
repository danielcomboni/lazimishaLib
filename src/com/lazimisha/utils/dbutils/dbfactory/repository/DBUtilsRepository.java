package com.lazimisha.utils.dbutils.dbfactory.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lazimisha.utils.dbutils.dbfactory.DBUtilsConnectivity;

public class DBUtilsRepository {

	private DBUtilsRepository() {

	}

	public static List < Object > findAllList( Class < ? > cls, Object obj ) {

		Field [ ] fieldArray = cls.getDeclaredFields();
		List < Field > fs = new ArrayList <>();
		for ( Field f : fieldArray ) {
			fs.add( f );
		}

		PreparedStatement pst = null;
		Connection connection = DBUtilsConnectivity.getConnection();
		ResultSet rs = null;

		List < Object > clses = new ArrayList <>();

		try {

			pst = connection.prepareStatement( "select * from ".concat( cls.getSimpleName() ) );
			rs = pst.executeQuery();

			while ( rs.next() ) {
				obj = getEachRow( cls, obj, rs, fs );
				clses.add( obj );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return clses;
	}

	private static Object getEachRow( Class < ? > classObject, Object obj, ResultSet rs, List < Field > fields ) {

		try {
			for ( Field f : fields ) {

				String formatted = f.getName();
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

				Method method = null;

				if ( f.getType().equals( String.class ) ) {
					String methodName = "set".concat( formatted2 );
					method = classObject.getMethod( methodName, String.class );
					method.invoke( obj, rs.getString( f.getName() ) );
				}

				if ( f.getType().equals( BigDecimal.class ) ) {
					String methodName = "set".concat( formatted2 );
					method = classObject.getMethod( methodName, BigDecimal.class );
					method.invoke( obj, rs.getBigDecimal( f.getName() ) );
				}

			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return obj;

	}

	public static void getRowsFromResultSet( Class < ? > classObject, Object obj, String setterNameString,
			String setValue, ResultSet rs, List < Field > fields, List < Class < ? > > clsList ) {
		try {

			while ( rs.next() ) {

				for ( Field f : fields ) {

					String formatted = f.getName();
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

					Method method = null;

					if ( f.getType().equals( String.class ) ) {
						String methodName = "set".concat( formatted2 );
						method = classObject.getMethod( methodName, String.class );
						method.invoke( obj, rs.getString( f.getName() ) );
					}

					if ( f.getType().equals( BigDecimal.class ) ) {
						String methodName = "set".concat( formatted2 );
						method = classObject.getMethod( methodName, BigDecimal.class );
						method.invoke( obj, rs.getBigDecimal( f.getName() ) );
					}

					clsList.add( ( Class < ? > ) obj );

				}

			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

}
