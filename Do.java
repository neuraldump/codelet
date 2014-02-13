package com.codelet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * This is the base method to handle path mappings.
 * 
 * @author Senthu Sivasambu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Do {

	public static enum METHODS{
		
		DOGET("GET"),DOPOST("POST");
		
		private String name = null;
		
		METHODS(String name){
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public String getName(){
			return name;
		}
	}
	
	String action();
	METHODS method() default METHODS.DOGET;
	
}
