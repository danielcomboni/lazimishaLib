package com.lazimisha.utils.dbutils.dbfactory;

import com.lazimisha.utils.dbutils.dbfactory.repository.DBRepositoryInsert;

public class ImageTestRunner {

	public static void main( String [ ] args ) {

		ImagePath.setImagePathBase64( "/home/luqman/Pictures/ac milan.jpg" );

		ImagePath.setImagePathBase64( ImagePath.encodeFilePathToBase64( ImagePath.getImagePathBase64() ) );

		ImageTestModel img = new ImageTestModel();
		img.setImageTitle( "ac" );
		DBRepositoryInsert.saveNew( img );

	}

}
