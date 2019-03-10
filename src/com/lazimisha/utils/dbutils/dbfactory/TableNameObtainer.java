package com.lazimisha.utils.dbutils.dbfactory;

public class TableNameObtainer {

	private TableNameObtainer() {

	}

	private static final String TILDE = "`";

	public static String getTableName( Class < ? > cls ) {

		return "".concat( TILDE ).concat( cls.getSimpleName() ).concat( TILDE ).concat( " " );

	}

}
