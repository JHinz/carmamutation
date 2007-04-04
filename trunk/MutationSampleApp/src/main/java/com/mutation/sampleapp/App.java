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
