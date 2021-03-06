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
package org.bellard.qemoon.preferences;

import org.bellard.qemoon.Activator;
import org.bellard.qemoon.components.QFieldEditorPreferencePage;
import org.bellard.qemoon.constants.Configuration2Constants;
import org.bellard.qemoon.constants.PreferenceConstants;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class CDROMPreferencePage extends QFieldEditorPreferencePage {

	public static final String PREFERENCES_CDROM_CD_LABEL = "preferences.cdrom.cd.label";

	public static final String PREFERENCES_CDROM_DESCRIPTION = "preferences.cdrom.description";

	/**
	 */
	public CDROMPreferencePage() {
		super(Activator.getDefault().getMessages().getString(
				PreferenceConstants.PREFERENCES_CDROM_TITLE), GRID);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {

		setMessage(Activator.getDefault().getMessages().getString(
				PREFERENCES_CDROM_DESCRIPTION));

		createFileFieldEditor(Configuration2Constants.CD_VALUE,
				PREFERENCES_CDROM_CD_LABEL, true);

	}
}
