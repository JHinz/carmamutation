/**
  *  This file is part of Carma (Retroduction.org).
  *
  *  Carma is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  Carma is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with Carma.  If not, see <http://www.gnu.org/licenses/>.
  *
  * Copyright Retroduction.org (c) 2007-2010
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
