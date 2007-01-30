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
import org.bellard.qemoon.components.QBooleanFieldEditor;
import org.bellard.qemoon.components.QFieldEditorPreferencePage;
import org.bellard.qemoon.constants.Configuration2Constants;
import org.bellard.qemoon.constants.PreferenceConstants;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class HardDiskPreferencePage extends QFieldEditorPreferencePage {

	public static final String PREFERENCES_HARDDISK_MAIN_LABEL = "preferences.harddisk.main.label";

	public static final String PREFERENCES_HARDDISK_MAIN_CUSTOM = "preferences.harddisk.main.custom.label";

	public static final String PREFERENCES_HARDDISK_MAIN_SNAPSHOT = "preferences.harddisk.main.snapshot.label";

	public static final String PREFERENCES_HARDDISK_HDD_LABEL = "preferences.harddisk.hdd.label";

	public static final String PREFERENCES_HARDDISK_HDC_LABEL = "preferences.harddisk.hdc.label";

	public static final String PREFERENCES_HARDDISK_HDB_LABEL = "preferences.harddisk.hdb.label";

	public static final String PREFERENCES_HARDDISK_HDA_LABEL = "preferences.harddisk.hda.label";

	public static final String PREFERENCES_HARDDISK_DESCRIPTION = "preferences.harddisk.description";

	/**
	 */
	public HardDiskPreferencePage() {
		super(Activator.getDefault().getMessages().getString(
				PreferenceConstants.PREFERENCES_HARDDISK_TITLE), GRID);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {

		// enable custom main hard drive
		QBooleanFieldEditor enableImage = createQBooleanFiedEditor(
				Configuration2Constants.IMAGE_CUSTOM,
				PREFERENCES_HARDDISK_MAIN_CUSTOM);

		final FileFieldEditor imageSelect = createFileFieldEditor(
				Configuration2Constants.IMAGE_VALUE,
				PREFERENCES_HARDDISK_MAIN_LABEL, true);

		enableImage.getChangeControl(getFieldEditorParent()).addListener(
				SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						if (((Button) event.widget).getSelection()) {
							imageSelect
									.setEnabled(true, getFieldEditorParent());
						} else {
							imageSelect.setEnabled(false,
									getFieldEditorParent());
						}
					}
				});

		if (!getPreferenceStore().getBoolean(
				Configuration2Constants.IMAGE_CUSTOM)) {
			imageSelect.setEnabled(false, getFieldEditorParent());
		}

		
		// is main hard drive a snaphsot image
		createQBooleanFiedEditor(Configuration2Constants.SNAPSHOT_VALUE,
				PREFERENCES_HARDDISK_MAIN_SNAPSHOT);


		// others hard drive
		createFileFieldEditor(Configuration2Constants.HDA_VALUE,
				PREFERENCES_HARDDISK_HDA_LABEL, true);
		createFileFieldEditor(Configuration2Constants.HDB_VALUE,
				PREFERENCES_HARDDISK_HDB_LABEL, true);
		createFileFieldEditor(Configuration2Constants.HDC_VALUE,
				PREFERENCES_HARDDISK_HDC_LABEL, true);
		createFileFieldEditor(Configuration2Constants.HDD_VALUE,
				PREFERENCES_HARDDISK_HDD_LABEL, true);

	}

}
