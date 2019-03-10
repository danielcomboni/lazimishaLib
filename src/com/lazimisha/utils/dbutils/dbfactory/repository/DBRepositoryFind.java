package com.lazimisha.utils.dbutils.dbfactory.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazimisha.utils.dbutils.dbfactory.DBUtilsConnectivity;
import com.lazimisha.utils.dbutils.dbfactory.MethodInvocation;

public class DBRepositoryFind {

	private DBRepositoryFind() {

	}

	private static List < Object > allList;

	public static List < Object > getAllList() {
		return allList;
	}

	public static void setAllList( List < Object > allList ) {
		DBRepositoryFind.allList = allList;
	}

	public static List < ? > findAllList( Class < ? > cls ) {

		DBUtilsConnectivity.createTable( cls );

		Field [ ] fieldArray = cls.getDeclaredFields();
		List < Field > fs = new ArrayList <>();
		for ( Field f : fieldArray ) {
			fs.add( f );
		}

		checkIfForeignKeyContainedInAClass( cls, fs );

		PreparedStatement pst = null;
		Connection connection = DBUtilsConnectivity.getConnection();
		ResultSet rs = null;
		List < Object > clses = new ArrayList <>();
		setAllList( clses );
		try {

			pst = connection.prepareStatement( "select * from ".concat( cls.getSimpleName() ) );
			rs = pst.executeQuery();

			while ( rs.next() ) {

				Object object = getEachRow( cls, rs, fs );

				getAllList().add( object );

			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtilsConnectivity.closeResultSetPreparedStatementAndConnectionOnNewInsertion( rs, pst, connection );
		}

		Map < Class < ? >, List < ? > > map = new HashMap <>();

		map.put( cls, getAllList() );

		DBRepoFindMapClassList.addToMapClassKeyToInnerClassKeyValueObjectList( map, cls );

		return getAllList();
	}

	private static Object getEachRow( Class < ? > cls, ResultSet rs, List < Field > fields ) {

		DBRepoFindMapClassList.getMapClassToClassKeyAndListValue();

		Object t = null;

		try {

			Object object = cls.newInstance();

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
					method = cls.getMethod( methodName, String.class );
					method.invoke( object, rs.getString( f.getName() ) );
				}

				if ( f.getType().equals( BigDecimal.class ) ) {
					String methodName = "set".concat( formatted2 );
					method = cls.getMethod( methodName, BigDecimal.class );
					method.invoke( object, rs.getBigDecimal( f.getName() ) );
				}

				if ( f.getType().equals( boolean.class ) ) {
					String methodName = "set".concat( formatted2 );
					method = cls.getMethod( methodName, boolean.class );
					method.invoke( object, rs.getBoolean( f.getName() ) );
				}

				if ( !f.getType().isPrimitive() && !f.getType().getName().startsWith( "java." ) ) {

					/**
					 * fk id
					 */
					BigDecimal idReferenced = rs.getBigDecimal( f.getName() );

					/**
					 * set the id of the referenced class
					 * 
					 * write a list of mapped classes to the IDs
					 * 
					 * use it to get the specific class value
					 */

					// System.out.println( "type: " + f.getType() );

					if ( DBRepoFindMapClassList.getObjectById( idReferenced, f.getType() ) == null ) {
						DBRepositoryFind_GuaranteeReferencedClass.findAllList( f.getType() );
					}

					DBRepoFindMapClassList.getObjectById( idReferenced, f.getType() );

					if ( DBRepoFindMapClassList.getMapCurrentObjectRequired().values().size() == 1 ) {
						for ( Map.Entry < BigDecimal, Object > m : DBRepoFindMapClassList.getMapCurrentObjectRequired()
								.entrySet() ) {

							MethodInvocation.setObjectValue( "set".concat( formatted2 ), object, m.getValue(),
									f.getType().getName() );

						}
					}

				}

				t = object;

			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return t;

	}

	private static void checkIfForeignKeyContainedInAClass( Class < ? > cls, List < Field > fields ) {

		boolean decision = false;

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

				if ( !f.getType().isPrimitive() && !f.getType().getName().startsWith( "java." ) ) {
					decision = true;
				}

				if ( decision ) {
					DBRepositoryFind_GuaranteeReferencedClass.findAllList( f.getType() );
				}

			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private static Map < BigDecimal, Object > mapCurrentObjectRequired;

	public static Map < BigDecimal, Object > getMapCurrentObjectRequired() {
		return mapCurrentObjectRequired;
	}

	public static void setMapCurrentObjectRequired( Map < BigDecimal, Object > mapCurrentObjectRequired ) {
		DBRepositoryFind.mapCurrentObjectRequired = mapCurrentObjectRequired;
	}
}
