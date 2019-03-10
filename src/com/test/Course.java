package com.test;

import java.math.BigDecimal;

import com.lazimisha.utils.annotation.AnnotationTableDefinition;
import com.lazimisha.utils.crud.TableColumnDefinitions;

public class Course {

	@AnnotationTableDefinition( tableColumnDefinition = TableColumnDefinitions.ID_AUTO_INC )
	private BigDecimal id;

	@AnnotationTableDefinition( tableColumnDefinition = "TEXT" )
	private String courseName;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName( String courseName ) {
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", courseName=" + courseName + "]";
	}

}
