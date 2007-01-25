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
public class QemuImagePreferencePage extends FieldEditorPreferencePage {

	/**
	 */
	public QemuImagePreferencePage() {
		super(Activator.getDefault().getMessages().getString(PreferenceConstants.PREFERENCES_IMAGE_TITLE), GRID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {

		QBooleanFieldEditor enableImage = new QBooleanFieldEditor(
				Configuration2Constants.IMAGE_CUSTOM, Activator.getDefault().getMessages()
						.getString(PreferenceConstants.PREFERENCES_IMAGE_CHECKLABEL),
				getFieldEditorParent());

		final FileFieldEditor imageSelect = new FileFieldEditor(
				Configuration2Constants.IMAGE_VALUE, Activator.getDefault().getMessages()
						.getString(PreferenceConstants.PREFERENCES_IMAGE_LABEL),
				getFieldEditorParent());
		imageSelect.setEmptyStringAllowed(true);

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

		
		QBooleanFieldEditor snapshotImage = new QBooleanFieldEditor(
				Configuration2Constants.SNAPSHOT_VALUE, Activator.getDefault().getMessages()
						.getString(PreferenceConstants.PREFERENCES_IMAGE_SNAPSHOT),
				getFieldEditorParent());
		
		
		addField(enableImage);
		addField(imageSelect);
		addField(snapshotImage);
	}

}
