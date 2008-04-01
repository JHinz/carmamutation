/**
 *
 *   Copyright Alexander Rau, Mike Groezinger, Retroduction.org - All rights reserved
 *
 *   This file is part of Carma. Carma is licensed under the OSL 3.0. The OSL 3.0 is
 *   available here: http://www.opensource.org/licenses/osl-3.0.php
 *
 */
public class Sample {

	public int decide2(boolean a, boolean b) {

		boolean x = a && (b ||(100 < 1000));
		
		boolean y = (a || b);

		boolean z = !a;

		return 4;
	}
}
