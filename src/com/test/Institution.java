package com.test;

import java.math.BigDecimal;

import com.lazimisha.utils.annotation.AnnotationTableDefinition;
import com.lazimisha.utils.crud.TableColumnDefinitions;

public class Institution {

	@AnnotationTableDefinition( tableColumnDefinition = TableColumnDefinitions.ID_AUTO_INC )
	private BigDecimal id;

	@AnnotationTableDefinition( tableColumnDefinition = TableColumnDefinitions.TEXT )
	private String institutionName;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName( String institutionName ) {
		this.institutionName = institutionName;
	}

	@Override
	public String toString() {
		return "Institution [id=" + id + ", institutionName=" + institutionName + "]";
	}

}
