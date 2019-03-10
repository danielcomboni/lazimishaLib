package com.lazimisha.utils.dbutils.dbfactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class MethodInvocation {

	private MethodInvocation() {

	}

	public static BigDecimal getBigDecimalValue( String methodName, Object obj ) {

		Method method = null;
		BigDecimal bigDecimalVal = BigDecimal.ZERO;
		try {
			method = obj.getClass().getDeclaredMethod( methodName );
			bigDecimalVal = ( BigDecimal ) method.invoke( obj );

		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e ) {
			e.printStackTrace();
		}

		return bigDecimalVal;
	}

	public static void setBigDecimalValue( String methodName, Object obj, BigDecimal val ) {
		Method method = null;
		try {
			method = obj.getClass().getMethod( methodName, BigDecimal.class );
			method.invoke( obj, val );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e ) {
			e.printStackTrace();
		}

	}

	public static void setObjectValue( String methodName, Object obj, Object objectValueSet, String className ) {
		Method method = null;
		try {
			method = obj.getClass().getMethod( methodName, Class.forName( className ) );
			method.invoke( obj, objectValueSet );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | ClassNotFoundException e ) {
			e.printStackTrace();
		}

	}

	public static Object getObjectValue( String methodName, Object obj ) {
		Method method = null;
		Object ob = null;
		try {
			method = obj.getClass().getDeclaredMethod( methodName );
			ob = ( Object ) method.invoke( obj );
		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e ) {
			e.printStackTrace();
		}
		return ob;
	}

	public static Object newInstance( Object obj ) {
		Object ob = null;
		try {
			ob = obj.getClass().newInstance();
		} catch ( InstantiationException | IllegalAccessException e ) {
			e.printStackTrace();
		}
		return ob;
	}

	public static Object newInstanceByClass( Class < ? > cls ) {
		Object ob = null;
		try {
			ob = cls.newInstance();
		} catch ( InstantiationException | IllegalAccessException e ) {
			e.printStackTrace();
		}
		return ob;
	}

}
