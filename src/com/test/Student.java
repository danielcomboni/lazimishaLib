package com.test;

import java.math.BigDecimal;

import com.lazimisha.utils.annotation.AnnotationTableDefinition;
import com.lazimisha.utils.annotation.AnnotationTableDefinitionForeignKey;
import com.lazimisha.utils.crud.TableColumnDefinitions;

public class Student {

	@AnnotationTableDefinition( tableColumnDefinition = TableColumnDefinitions.ID_AUTO_INC )
	private BigDecimal id;

	@AnnotationTableDefinition( tableColumnDefinition = "VARCHAR(50)" )
	private String firstName;

	@AnnotationTableDefinition( tableColumnDefinition = "VARCHAR(100)" )
	private String otherNames;

	@AnnotationTableDefinition( tableColumnDefinition = "BIGINT" )
	@AnnotationTableDefinitionForeignKey( referenceClass = Course.class, onDelete = TableColumnDefinitions.ON_DELETE_CASCADE, onUpdate = TableColumnDefinitions.ON_UPDATE_CASCADE )
	private Course courseId;

	@AnnotationTableDefinition( tableColumnDefinition = "BIGINT" )
	@AnnotationTableDefinitionForeignKey( referenceClass = Course.class, onDelete = TableColumnDefinitions.ON_DELETE_CASCADE, onUpdate = TableColumnDefinitions.ON_UPDATE_CASCADE )
	private Institution institutionId;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	public String getOtherNames() {
		return otherNames;
	}

	public void setOtherNames( String otherNames ) {
		this.otherNames = otherNames;
	}

	public Course getCourseId() {
		return courseId;
	}

	public void setCourseId( Course courseId ) {
		this.courseId = courseId;
	}

	public Institution getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId( Institution institutionId ) {
		this.institutionId = institutionId;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", otherNames=" + otherNames + ", courseId="
				+ courseId + ", institutionId=" + institutionId + "]";
	}

}
