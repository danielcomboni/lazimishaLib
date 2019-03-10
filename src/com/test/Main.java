package com.test;

import java.math.BigDecimal;

import com.lazimisha.utils.dbutils.dbfactory.DBUtilsConnectivity;
import com.lazimisha.utils.dbutils.dbfactory.repository.DBRepositoryFind;
import com.lazimisha.utils.dbutils.dbfactory.repository.DBRepositoryInsert;

public class Main {

	public static void main( String [ ] args ) {

		/**
		 * trying to retrieve from a table that does not exist will automatically create
		 * it
		 * 
		 * 
		 */
		// DBRepositoryFind.findAllList( Institution.class );
		//
		// System.out.println( "Insts: " + DBRepositoryFind.getAllList() );
		//
		// DBRepositoryFind.findAllList( Course.class );
		// System.out.println( "Courses: " + DBRepositoryFind.getAllList() );
		//
		// DBRepositoryFind.findAllList( Student.class );
		//
		// System.out.println( "Sts: " + DBRepositoryFind.getAllList() );

		/**
		 * inserting into tables
		 * 
		 */

		/**
		 * foreign key constraints have to be honoured, so inserting into parent tables
		 * first
		 */
		Institution institution = new Institution();
		institution.setInstitutionName( "Makerere university" );
		DBRepositoryInsert.saveNew( institution );

		/**
		 * this if statement is place immediately after the INSERT action on Institution
		 * table because its prepared statement (plus connection) is (are) being
		 * referenced to, in order to get the generated key (ID)
		 */
		if ( institution.getId() == null || institution.getId() == BigDecimal.ZERO ) {
			institution.setId( DBUtilsConnectivity.getGeneratedKey() );
		}

		Course course = new Course();
		course.setCourseName( "Bachelors of Computer Science" );
		DBRepositoryInsert.saveNew( course );

		Student student = new Student();
		student.setCourseId( course );
		student.setInstitutionId( institution );
		student.setFirstName( "Kim" );
		student.setOtherNames( "Jong Un" );

		if ( course.getId() == null || course.getId() == BigDecimal.ZERO ) {
			course.setId( DBUtilsConnectivity.getGeneratedKey() );
		}

		DBRepositoryInsert.saveNew( student );

		/**
		 * retrieving after insertion
		 */

		DBRepositoryFind.findAllList( Institution.class );

		System.out.println( "Insts: " + DBRepositoryFind.getAllList() );

		DBRepositoryFind.findAllList( Course.class );
		System.out.println( "Courses: " + DBRepositoryFind.getAllList() );

		DBRepositoryFind.findAllList( Student.class );
		System.out.println( "Sts: " + DBRepositoryFind.getAllList() );

	}

}
