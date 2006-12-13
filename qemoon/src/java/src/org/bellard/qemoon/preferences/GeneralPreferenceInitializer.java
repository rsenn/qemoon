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
		// store
		// .setDefault(
		// PreferenceConstants.PREFERENCES_KQEMU_PATH_VALUE,
		// Activator
		// .getDefault()
		// .getPreferenceDefault()
		// .getOsspecificString(
		// PreferenceConstants.PREFERENCES_KQEMU_PATH_VALUE));

		// build qemu path
		String qemuPath = Activator
				.getDefault()
				.getPlatformPath()
				.append(
						Activator
								.getDefault()
								.getPreferenceDefault()
								.getOsspecificString(
										PreferenceConstants.PREFERENCES_QEMU_PATH_VALUE))
				.toOSString();
		
		// store properties
		store.setDefault(PreferenceConstants.PREFERENCES_QEMU_PATH_VALUE,
				qemuPath);
		store.setDefault(PreferenceConstants.PREFERENCES_MONITOR_PORT_VALUE,
				Activator.getDefault().getPreferenceDefault().getDefaultString(
						PreferenceConstants.PREFERENCES_MONITOR_PORT_VALUE));

	}
}
