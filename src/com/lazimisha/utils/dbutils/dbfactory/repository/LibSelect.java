package com.lazimisha.utils.dbutils.dbfactory.repository;

public class LibSelect {

	private LibSelect() {

	}

	public static String selectAll( Class < ? > cls ) {
		return "SELECT * FROM ".concat( cls.getSimpleName() );
	}

	public static String selectByColumn( Class < ? > cls, String specifyCommaSeparatedColumnNames ) {
		return "SELECT ".concat( specifyCommaSeparatedColumnNames ).concat( " FROM " ).concat( cls.getSimpleName() );
	}

	public static String selectById( Class < ? > cls ) {
		return "SELECT * FROM ".concat( cls.getSimpleName() ).concat( " WHERE id = ? " );
	}

}
