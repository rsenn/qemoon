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
package org.bellard.qemoon.resources;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class MessageBundle {

	private final static Logger logger = Logger.getLogger(MessageBundle.class);

	private static final String BUNDLE_NAME = "messages";

	private ResourceBundle bundle;

	public MessageBundle() {
		bundle = ResourceBundle.getBundle(BUNDLE_NAME);
	}

	public String getString(String key) {
		logger.debug("get messsage : key=" + key);
		String message = null;
		try {
			message = bundle.getString(key);
		} catch (RuntimeException e) {
			String m = "impossible to find value for key=" + key;
			logger.error(m, e);
			message = "! " + key + " !";
		}
		return message;
	}

	public String[] getStringArray(String key) {
		return bundle.getString(key).split(",");
	}

}
