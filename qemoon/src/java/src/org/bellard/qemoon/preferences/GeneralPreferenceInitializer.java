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
package org.bellard.qemoon.preferences;

import org.bellard.qemoon.Activator;
import org.bellard.qemoon.constants.PreferenceConstants;
import org.bellard.qemoon.resources.PreferenceDefaultBundle;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class GeneralPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		PreferenceDefaultBundle pdb = Activator.getDefault()
				.getPreferenceDefault();
		IPath platformPath = Activator.getDefault().getPlatformPath();

		// build qemu path
		String qemuPath = platformPath
				.append(
						pdb
								.getOsspecificString(PreferenceConstants.PREFERENCES_QEMU_PATH_VALUE))
				.toOSString();

		String qemuimgPath = platformPath
				.append(
						pdb
								.getOsspecificString(PreferenceConstants.PREFERENCES_QEMUIMG_PATH_VALUE))
				.toOSString();

		// store properties
		store.setDefault(PreferenceConstants.PREFERENCES_QEMU_PATH_VALUE,
				qemuPath);
		store.setDefault(PreferenceConstants.PREFERENCES_QEMUIMG_PATH_VALUE,
				qemuimgPath);
		store
				.setDefault(
						PreferenceConstants.PREFERENCES_MONITOR_PORT_VALUE,
						pdb
								.getDefaultString(PreferenceConstants.PREFERENCES_MONITOR_PORT_VALUE));
		store
				.setDefault(
						PreferenceConstants.PREFERENCES_QEMU_CUSTOM_PATH_VALUE,
						pdb
								.getDefaultString(PreferenceConstants.PREFERENCES_QEMU_CUSTOM_PATH_VALUE));
		store
				.setDefault(
						PreferenceConstants.PREFERENCES_MONITOR_SOCKETSERVER_VALUE,
						pdb
								.getOsspecificString(PreferenceConstants.PREFERENCES_MONITOR_SOCKETSERVER_VALUE));

	}
}
