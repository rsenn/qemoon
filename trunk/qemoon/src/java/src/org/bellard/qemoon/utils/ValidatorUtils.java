/*
 * ========================================================================
 *
 * qemoon - a gui frontend for the qemu emulator written in the java programming language with the eclipse rcp framework.
 * Copyright (C) 2006 Eric Bellard.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * ========================================================================
 */
/**
 * 
 */
package org.bellard.qemoon.utils;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class ValidatorUtils {

	/**
	 * Return trus if s is empty or null
	 * 
	 * @param s
	 * @return
	 */
	public final static boolean isEmptyOrNull(String s) {
		return s == null || "".equals(s);

	}

	/**
	 * 
	 * Checks if the value can safely be converted to a int primitive.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * 
	 * 
	 */

	public static Integer formatInt(String value) {

		if (value == null) {
			return null;
		}

		try {
			return new Integer(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static boolean isInt(String value) {

		return (formatInt(value) != null);

	}

}
