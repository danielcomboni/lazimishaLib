package com.lazimisha.utils.dbutils.dbfactory.repository;

import java.math.BigDecimal;

import com.lazimisha.utils.dbutils.dbfactory.DBUtilsConnectivity;

public class DBRepositoryUpdate {

	private DBRepositoryUpdate() {

	}

	public static int update( Class < ? > cls, Object classObject, BigDecimal id ) {
		int result = 0;

		DBUtilsConnectivity.setClassObject( classObject );
		result = DBUtilsConnectivity.update( cls, id );

		return result;
	}

}
