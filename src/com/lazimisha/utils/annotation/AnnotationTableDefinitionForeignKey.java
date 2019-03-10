package com.lazimisha.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER } )

public @interface AnnotationTableDefinitionForeignKey {

	public String foreingKey() default "FOREIGN KEY  ";

	public Class < ? > referenceClass();

	public String onUpdate() default " ON UPDATE NO ACTION ";

	public String onDelete() default " ON DELETE NO ACTION ";

}
