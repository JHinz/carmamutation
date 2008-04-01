/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package com.mutation.sampleapp;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public boolean isTrue(boolean t){
		if(t){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isFalse(boolean f){
		if(isTrue(f)){
			return false;
		}else{
			return true;
		}
	}

	public int max(int a, int b){
		if(a > b){
			return a;
		}else{
			return b;
		}
	}
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
