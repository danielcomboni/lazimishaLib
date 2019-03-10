package com.lazimisha.utils.dbutils.dbfactory;

import java.math.BigDecimal;
import java.util.Map;

public class DBUtilsTableExistence {

	private DBUtilsTableExistence() {

	}

	private static Map < BigDecimal, Class < ? > > mapOfTableNamesToTheirIDs;

	public static Map < BigDecimal, Class < ? > > getMapOfTableNamesToTheirIDs() {
		return mapOfTableNamesToTheirIDs;
	}

	public static void setMapOfTableNamesToTheirIDs( Map < BigDecimal, Class < ? > > mapOfTableNamesToTheirIDs ) {
		DBUtilsTableExistence.mapOfTableNamesToTheirIDs = mapOfTableNamesToTheirIDs;
	}

}
