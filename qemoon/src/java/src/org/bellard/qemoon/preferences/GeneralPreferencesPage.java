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
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class GeneralPreferencesPage extends QFieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public final static String ID = GeneralPreferencesPage.class.getName();

	/**
	 * @param style
	 */
	public GeneralPreferencesPage() {
		super(Activator.getDefault().getMessages().getString(
				PreferenceConstants.PREFERENCES_SECTION), GRID);
		setDescription(Activator.getDefault().getMessages().getString(
				PreferenceConstants.PREFERENCES_DESCRIPTION));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		setMessage(Activator.getDefault().getMessages().getString(
				PreferenceConstants.PREFERENCES_TITLE));

		// enable custom main hard drive
		QBooleanFieldEditor enableImage = createQBooleanFiedEditor(
				PreferenceConstants.PREFERENCES_QEMU_CUSTOM_PATH_VALUE,
				PreferenceConstants.PREFERENCES_QEMU_CUSTOM_PATH_LABEL);

		final FileFieldEditor qemuPathSelect = createFileFieldEditor(
				PreferenceConstants.PREFERENCES_QEMU_PATH_VALUE,
				PreferenceConstants.PREFERENCES_QEMU_PATH_LABEL, true);

		final FileFieldEditor qemuImagePathSelect = createFileFieldEditor(
				PreferenceConstants.PREFERENCES_QEMUIMG_PATH_VALUE,
				PreferenceConstants.PREFERENCES_QEMUIMG_PATH_LABEL, false);

		final Composite p = getFieldEditorParent();
		enableImage.getChangeControl(getFieldEditorParent()).addListener(
				SWT.Selection, new Listener() {
					public void handleEvent(Event event) {

						if (((Button) event.widget).getSelection()) {
							qemuPathSelect.setEnabled(true, p);
							qemuImagePathSelect.setEnabled(true, p);
						} else {
							qemuPathSelect.setEnabled(false, p);
							qemuImagePathSelect.setEnabled(false, p);
						}
					}
				});

		if (!getPreferenceStore().getBoolean(
				Configuration2Constants.IMAGE_CUSTOM)) {
			qemuPathSelect.setEnabled(false, p);
			qemuImagePathSelect.setEnabled(false, p);
		}

		// createFileFieldEditor(PreferenceConstants.PREFERENCES_QEMU_PATH_VALUE,
		// PreferenceConstants.PREFERENCES_QEMU_PATH_LABEL, false);

		// DirectoryFieldEditor kqemuDfe = new
		// DirectoryFieldEditor(PreferenceConstants.PREFERENCES_KQEMU_PATH_VALUE,
		// Activator.getDefault().getMessages().getString(PreferenceConstants.PREFERENCES_KQEMU_PATH_LABEL),
		// getFieldEditorParent());
		// kqemuDfe.setEmptyStringAllowed(true);
		// addField(kqemuDfe);

		addField(new IntegerFieldEditor(
				PreferenceConstants.PREFERENCES_MONITOR_PORT_VALUE,
				Activator.getDefault().getMessages().getString(
						PreferenceConstants.PREFERENCES_MONITOR_PORT_LABEL),
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(
				PreferenceConstants.PREFERENCES_MONITOR_SOCKETSERVER_VALUE,
				Activator
						.getDefault()
						.getMessages()
						.getString(
								PreferenceConstants.PREFERENCES_MONITOR_SOCKETSERVER_LABEL),
				getFieldEditorParent()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}

	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

}
