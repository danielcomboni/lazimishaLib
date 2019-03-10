package com.lazimisha.utils.dbutils.dbfactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Base64;

public class ImagePath {

	private static byte [ ] byteTest;

	public static void main( String [ ] args ) {

		String base64One = encodeFilePathToBase64( "/home/luqman/Pictures/ac milan.jpg" );
		System.out.println( "one: " + base64One );

		Field [ ] fs = ImagePath.class.getDeclaredFields();

		for ( Field f : fs ) {

			if ( f.getType() == byte [ ].class ) {
				System.out.println( "name array: " + f.getName() );
			}

			// System.out.println( "f name: " + f.getName() );
			// System.out.println( "f type: " + f.getType() );
		}

	}

	private ImagePath() {

	}

	private static String imagePathBase64;

	public static String getImagePathBase64() {
		return imagePathBase64;
	}

	public static void setImagePathBase64( String imagePathBase64 ) {
		ImagePath.imagePathBase64 = imagePathBase64;
	}

	public static String encodeFilePathToBase64( String imagePath ) {
		String base64Image = "";
		File file = new File( imagePath );

		try {
			FileInputStream imageInFile = new FileInputStream( file );
			byte [ ] imageData = new byte [ ( int ) file.length() ];
			imageInFile.read( imageData );
			base64Image = Base64.getEncoder().encodeToString( imageData );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return base64Image;
	}

	/**
	 * use split when there is something like image/. blah
	 * 
	 * mostly it works when use the web
	 * 
	 * if it is using a split, the encodingFilePathToBase64 is not required anymore
	 * 
	 * @return
	 */

	public static InputStream getImageInputStreamFinal() {
		System.out.println( "path---: " + getImagePathBase64() );
		// String splitted = getImagePathBase64().split( "," ) [ 1 ];
		byte [ ] imageByteArray = Base64.getDecoder().decode( getImagePathBase64() );
		InputStream inputStream = new ByteArrayInputStream( imageByteArray );
		return inputStream;
	}

}
