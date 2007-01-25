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
import org.bellard.qemoon.constants.Configuration2Constants;
import org.bellard.qemoon.constants.PreferenceConstants;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class HardDiskPreferencePage extends FieldEditorPreferencePage {

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

		QBooleanFieldEditor enableImage = createQBoolean(
				Configuration2Constants.IMAGE_CUSTOM,
				PREFERENCES_HARDDISK_MAIN_CUSTOM);

		final FileFieldEditor imageSelect = createHardDrive(
				Configuration2Constants.IMAGE_VALUE,
				PREFERENCES_HARDDISK_MAIN_LABEL);

		createQBoolean(Configuration2Constants.SNAPSHOT_VALUE,
				PREFERENCES_HARDDISK_MAIN_SNAPSHOT);

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

		// others hard drive
		createHardDrive(Configuration2Constants.HDA_VALUE,
				PREFERENCES_HARDDISK_HDA_LABEL);
		createHardDrive(Configuration2Constants.HDB_VALUE,
				PREFERENCES_HARDDISK_HDB_LABEL);
		createHardDrive(Configuration2Constants.HDC_VALUE,
				PREFERENCES_HARDDISK_HDC_LABEL);
		createHardDrive(Configuration2Constants.HDD_VALUE,
				PREFERENCES_HARDDISK_HDD_LABEL);

	}

	/**
	 * Create hard drive
	 * 
	 * @param value
	 * @param label
	 */
	protected FileFieldEditor createHardDrive(String value, String label) {
		FileFieldEditor hd = new FileFieldEditor(value, Activator.getDefault()
				.getMessages().getString(label), getFieldEditorParent());
		hd.setEmptyStringAllowed(true);
		addField(hd);
		return hd;
	}

	protected QBooleanFieldEditor createQBoolean(String value, String label) {
		QBooleanFieldEditor qboolfield = new QBooleanFieldEditor(value,
				Activator.getDefault().getMessages().getString(label),
				getFieldEditorParent());

		addField(qboolfield);
		return qboolfield;
	}

}
