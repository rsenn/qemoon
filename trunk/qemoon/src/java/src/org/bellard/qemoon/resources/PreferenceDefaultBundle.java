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

public class PreferenceDefaultBundle {

	private final static Logger logger = Logger
			.getLogger(PreferenceDefaultBundle.class);

	private static final String OS_BUNDLE_NAME = "osspecific-preferences";

	private static final String DEFAULT_BUNDLE_NAME = "preferences";

	private ResourceBundle osbundle;

	private ResourceBundle defaultbundle;

	public PreferenceDefaultBundle() {
		osbundle = ResourceBundle.getBundle(OS_BUNDLE_NAME);
		defaultbundle = ResourceBundle.getBundle(DEFAULT_BUNDLE_NAME);
	}

	public String getOsspecificString(String key) {
		logger.debug("get os specific preference value : key=" + key);
		return osbundle.getString(key);
	}

	public String getDefaultString(String key) {
		logger.debug("get default preference value : key=" + key);
		return defaultbundle.getString(key);
	}

}
