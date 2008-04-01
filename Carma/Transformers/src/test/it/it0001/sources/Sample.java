/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
package sources;

public class Sample {

	/**
	 * 
	 * @param a
	 * @param b
	 * @return 1 if a == 1, 2 if b == 2, 3 else
	 */
	public int decide(int a, int b){
		if(a == b){
			return 7;
		}

		if( a == 1){
			return 1;
		}
		if (b == 2){
			return 2;
		}
		
		
		return 3;
	}
}
