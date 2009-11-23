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

package com.retroduction.carma.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Utility class for toString implementations of objects
 */
public final class ToStringUtils {
	private ToStringUtils() {
	}

	public static String toString(Object objectToBeStringified) {
		return toString(objectToBeStringified, Object.class);
	}

	private static String toString(Object objectToBeStringified, Class<?> baseClass) {
		if (null == objectToBeStringified) {
			return "null";
		}
		StringBuffer sb = new StringBuffer(objectToBeStringified.getClass().getSimpleName() + ": ");

		Hashtable<String, String> fields = getReflectionFields(objectToBeStringified, baseClass);
		Enumeration<String> enu = fields.keys();
		int i = 0;
		while (enu.hasMoreElements()) { // for each field
			String name = (String) enu.nextElement();
			Object value = fields.get(name);

			if (i > 0) {
				sb.append(",");
			}
			sb.append(name);
			sb.append("=");
			sb.append(value);
			i++;
		}// next field

		return sb.toString();
	}// toString()

	/**
	 * Get all fields that are public or accessible through getXXX() methods of
	 * this business object.
	 * 
	 * @return HashTable with field names (keys) and field values (values).
	 */
	private static Hashtable<String, String> getReflectionFields(Object o, Class<?> baseClass) {
		Hashtable<String, String> result = new Hashtable<String, String>();

		Class<?> currentClass = o.getClass();
		do {
			Field[] fields = currentClass.getDeclaredFields();
			int count = fields.length;
			for (int i = 0; i < count; i++) { // for each field
				Field field = fields[i];
				String name = field.getName();
				try {
					Object value = field.get(o); // may throw
					// IllegalAccessException
					// for private fields
					if (value == null) {
						value = "null";
					}
					result.put((String) name, (String) value);
				} catch (Exception e) {
					// try to call a possible get() method:
					StringBuffer sb = new StringBuffer("get"); // method name
					sb.append(name.toUpperCase().charAt(0));
					if (name.length() > 1) {
						sb.append(name.substring(1));
					}

					try {
						Method method = currentClass.getDeclaredMethod(sb.toString(), (Class[]) null);
						Object value = method.invoke(o, (Object[]) null); // get()
																			// methods
																			// usually
																			// don't
																			// have
																			// arguments
						if (value == null) {
							value = "null";
						}
                        if ( value instanceof String )
						result.put((String) name, (String) value);
					} catch (IllegalAccessException iae) {
						/* ignore */
					} catch (InvocationTargetException ite) {
						/* ignore */
					} catch (NoSuchMethodException nsme) {
						/* ignore */
					}
				}
			}// next field
			currentClass = currentClass.getSuperclass();
		} while (currentClass != null && currentClass != baseClass);

		return result;
	}// getReflectionFields()

}
