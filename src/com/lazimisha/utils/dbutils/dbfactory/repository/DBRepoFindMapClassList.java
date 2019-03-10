package com.lazimisha.utils.dbutils.dbfactory.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazimisha.utils.dbutils.dbfactory.MethodInvocation;

public class DBRepoFindMapClassList {

	private DBRepoFindMapClassList() {

	}

	private static Map < Class < ? >, Map < Class < ? >, List < ? > > > mapClassToClassKeyAndListValue;

	private static Map < Class < ? >, Map < BigDecimal, Object > > mapClassToClassKeyAndMapValue;

	private static Map < BigDecimal, Class < ? > > mapOfIDToObject;

	private static Map < BigDecimal, Object > mapCurrentObjectRequired;

	public static Map < Class < ? >, Map < Class < ? >, List < ? > > > getMapClassToClassKeyAndListValue() {
		return mapClassToClassKeyAndListValue;
	}

	public static void setMapClassToClassKeyAndListValue(
			Map < Class < ? >, Map < Class < ? >, List < ? > > > mapClassToClassKeyAndListValue ) {
		DBRepoFindMapClassList.mapClassToClassKeyAndListValue = mapClassToClassKeyAndListValue;
	}

	public static Map < Class < ? >, Map < BigDecimal, Object > > getMapClassToClassKeyAndMapValue() {
		return mapClassToClassKeyAndMapValue;
	}

	public static void setMapClassToClassKeyAndMapValue(
			Map < Class < ? >, Map < BigDecimal, Object > > mapClassToClassKeyAndMapValue ) {
		DBRepoFindMapClassList.mapClassToClassKeyAndMapValue = mapClassToClassKeyAndMapValue;
	}

	public static Map < BigDecimal, Class < ? > > getMapOfIDToObject() {
		return mapOfIDToObject;
	}

	public static void setMapOfIDToObject( Map < BigDecimal, Class < ? > > mapOfIDToObject ) {
		DBRepoFindMapClassList.mapOfIDToObject = mapOfIDToObject;
	}

	public static Map < BigDecimal, Object > getMapCurrentObjectRequired() {
		return mapCurrentObjectRequired;
	}

	public static void setMapCurrentObjectRequired( Map < BigDecimal, Object > mapCurrentObjectRequired ) {
		DBRepoFindMapClassList.mapCurrentObjectRequired = mapCurrentObjectRequired;
	}

	public static void addToMapClassKeyToInnerClassKeyValueObjectList( Map < Class < ? >, List < ? > > map,
			Class < ? > cls ) {

		if ( getMapClassToClassKeyAndListValue() == null || getMapClassToClassKeyAndListValue().isEmpty() ) {

			Map < Class < ? >, Map < Class < ? >, List < ? > > > m = new HashMap <>();
			m.put( cls, map );

			setMapClassToClassKeyAndListValue( m );
		}

		else {

			getMapClassToClassKeyAndListValue().put( cls, map );

		}
	}

	public static void addToMapClassKeyToInnerBigDecimalKeyValueMap( Map < BigDecimal, Object > map, Class < ? > cls ) {

		if ( getMapClassToClassKeyAndMapValue() == null || getMapClassToClassKeyAndMapValue().isEmpty() ) {

			Map < Class < ? >, Map < BigDecimal, Object > > m = new HashMap <>();
			m.put( cls, map );

			setMapClassToClassKeyAndMapValue( m );

		}

		else {

			getMapClassToClassKeyAndMapValue().put( cls, map );

		}
	}

	private static Object objectById;

	public static Object getObjectById() {
		return objectById;
	}

	public static void setObjectById( Object objectById ) {
		DBRepoFindMapClassList.objectById = objectById;
	}

	public static Map < BigDecimal, Object > mapCurrentObject = new HashMap <>();

	public static Object getObjectById( BigDecimal idRef, Class < ? > cls ) {

		for ( Map.Entry < Class < ? >, Map < Class < ? >, List < ? > > > s : getMapClassToClassKeyAndListValue()
				.entrySet() ) {

			if ( s.getKey().isAssignableFrom( cls ) ) {

				s.getValue().values().parallelStream().forEachOrdered( e -> {
					e.parallelStream().forEachOrdered( ee -> {

						Object obj2 = MethodInvocation.newInstanceByClass( cls );
						obj2 = ee;
						mapCurrentObject.put( MethodInvocation.getBigDecimalValue( "getId", ee ), obj2 );

						objectById = obj2;
						if ( idRef.intValue() == MethodInvocation.getBigDecimalValue( "getId", objectById )
								.intValue() ) {

							Map < BigDecimal, Object > map = new HashMap <>();
							map.put( idRef, obj2 );
							setMapCurrentObjectRequired( map );

							objectById = obj2;
							Object ob = obj2;
							setObjectById( ob );

						}

					} );

				} );

			}
		}
		return getObjectById();
	}

}
