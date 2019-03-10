package com.lazimisha.utils.dbutils.dbfactory.repository;

import com.lazimisha.utils.dbutils.dbfactory.DBUtilsConnectivity;

public class DBRepositoryInsert {

	private DBRepositoryInsert() {

	}

	public static int saveNew( Object classObject ) {
		int result = 0;

		DBUtilsConnectivity.setClassObject( classObject );
		result = DBUtilsConnectivity.saveNew( classObject.getClass() );

		return result;
	}

}
