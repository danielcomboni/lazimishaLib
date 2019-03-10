package com.lazimisha.utils.dbutils.dbfactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;

public class GivePreparedStatementUpdateIDValue {

	private GivePreparedStatementUpdateIDValue() {

	}

	public static void giveBigDecimalPreparedStatementUpdateID( PreparedStatement preparedStatement,
			Class < ? > classOfTheAnnotatedFields, BigDecimal id ) {

		try {
			preparedStatement.setBigDecimal( classOfTheAnnotatedFields.getDeclaredFields().length, id );
		} catch ( Exception e ) {
			e.printStackTrace();

		}

	}

}
