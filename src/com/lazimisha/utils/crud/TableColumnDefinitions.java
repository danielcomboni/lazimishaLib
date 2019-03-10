package com.lazimisha.utils.crud;

public class TableColumnDefinitions {

	private TableColumnDefinitions() {

	}

	public static final String ID_AUTO_INC = " BIGINT PRIMARY KEY AUTO_INCREMENT";
	public static final String DATE = "DATE";
	public static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";
	public static final String DATE_TIME = " DATETIME";
	public static final String DATE_TIME_NOW = " DATETIME DEFAULT NOW()";
	public static final String CURRENT_SYSTEM_DATE = " SELECT CURDATE() ";
	public static final String VARCHAR_BIGGEST = " VARCHAR(255)";
	public static final String VARCHAR_50 = "VARCHAR(50)";
	public static final String VARCHAR_100 = "VARCHAR(100)";
	public static final String BIG_INT = "BIGINT";
	public static final String TINY_INT = "TINYINT";
	public static final String LONG_BLOB = "LONGBLOB";
	public static final String BLOB = "BLOB";
	public static final String CLOB = "CLOB";
	public static final String MEDIUM_BLOB = "MEDIUMBLOB";
	public static final String DECIMAL_50_5 = "DECIMAL(50,5)";
	public static final String TEXT = "TEXT";
	public static final String MEDIUM_TEXT = "MEDIUMTEXT";
	public static final String LONG_TEXT = "LONGTEXT";

	public static final String ON_DELETE_CASCADE = "ON DELETE CASCADE";
	public static final String ON_DELETE_NO_ACTION = "ON DELETE NO ACTION";
	public static final String ON_DELETE_RESTRICT = "ON DELETE RESTRICT";
	public static final String ON_DELETE_SET_NULL = "ON DELETE SET NULL";

	public static final String ON_UPDATE_CASCADE = "ON UPDATE CASCADE";
	public static final String ON_UPDATE_NO_ACTION = "ON UPDATE NO ACTION";
	public static final String ON_UPDATE_RESTRICT = "ON UPDATE RESTRICT";
	public static final String ON_UPDATE_SET_NULL = "ON UPDATE SET NULL";

	public static final String specify( String definition ) {
		return " ".concat( definition ).concat( " " );
	}

	/**
	 * CONSTRAINTS
	 */
	public static final String NOT_NULL = " NOT NULL";
	public static final String UNIQUE = " UNIQUE";

}
