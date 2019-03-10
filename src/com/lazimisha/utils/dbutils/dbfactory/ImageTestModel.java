package com.lazimisha.utils.dbutils.dbfactory;

import java.math.BigDecimal;
import java.util.Arrays;

import com.lazimisha.utils.annotation.AnnotationTableDefinition;
import com.lazimisha.utils.crud.TableColumnDefinitions;

public class ImageTestModel {

	@AnnotationTableDefinition( tableColumnDefinition = TableColumnDefinitions.ID_AUTO_INC )
	private BigDecimal id;
	@AnnotationTableDefinition( tableColumnDefinition = TableColumnDefinitions.VARCHAR_100 )
	private String imageTitle;
	@AnnotationTableDefinition( tableColumnDefinition = TableColumnDefinitions.MEDIUM_BLOB )
	private byte [ ] imageTest;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle( String imageTitle ) {
		this.imageTitle = imageTitle;
	}

	public byte [ ] getImageTest() {
		return imageTest;
	}

	public void setImageTest( byte [ ] imageTest ) {
		this.imageTest = imageTest;
	}

	@Override
	public String toString() {
		return "ImageTestModel [id=" + id + ", imageTitle=" + imageTitle + ", imageTest=" + Arrays.toString( imageTest )
				+ "]";
	}

}
