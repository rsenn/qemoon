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
import org.bellard.qemoon.constants.Configuration2Constants;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class HardDiskPreferencePage extends FieldEditorPreferencePage {

	public static final String PREFERENCES_HARDDISK_HDD_LABEL = "preferences.harddisk.hdd.label";
	public static final String PREFERENCES_HARDDISK_HDC_LABEL = "preferences.harddisk.hdc.label";
	public static final String PREFERENCES_HARDDISK_HDB_LABEL = "preferences.harddisk.hdb.label";
	public static final String PREFERENCES_HARDDISK_HDA_LABEL = "preferences.harddisk.hda.label";
	public static final String PREFERENCES_HARDDISK_DESCRIPTION = "preferences.harddisk.description";
	public static final String PREFERENCES_HARDDISK_TITLE = "preferences.harddisk.title";

	/**
	 */
	public HardDiskPreferencePage() {
		super(Activator.getDefault().getMessages().getString(PREFERENCES_HARDDISK_TITLE), GRID);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {

		FileFieldEditor hda = new FileFieldEditor(Configuration2Constants.HDA_VALUE, Activator.getDefault().getMessages()
				.getString(PREFERENCES_HARDDISK_HDA_LABEL),
				getFieldEditorParent());
		FileFieldEditor hdb = new FileFieldEditor(Configuration2Constants.HDB_VALUE, Activator.getDefault().getMessages()
				.getString(PREFERENCES_HARDDISK_HDB_LABEL),
				getFieldEditorParent());
		FileFieldEditor hdc = new FileFieldEditor(Configuration2Constants.HDC_VALUE, Activator.getDefault().getMessages()
				.getString(PREFERENCES_HARDDISK_HDC_LABEL),
				getFieldEditorParent());
		FileFieldEditor hdd = new FileFieldEditor(Configuration2Constants.HDD_VALUE, Activator.getDefault().getMessages()
				.getString(PREFERENCES_HARDDISK_HDD_LABEL),
				getFieldEditorParent());

		hda.setEmptyStringAllowed(true);
		hdb.setEmptyStringAllowed(true);
		hdc.setEmptyStringAllowed(true);
		hdd.setEmptyStringAllowed(true);
		
		addField(hda);
		addField(hdb);
		addField(hdc);
		addField(hdd);

	}
}
