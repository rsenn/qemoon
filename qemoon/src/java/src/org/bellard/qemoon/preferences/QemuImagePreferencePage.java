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
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class QemuImagePreferencePage extends FieldEditorPreferencePage {

	public static final String PREFERENCES_IMAGE_LABEL = "preferences.image.label";

	private static final String PREFERENCES_IMAGE_CHECKLABEL = "preferences.image.checklabel";

	/**
	 */
	public QemuImagePreferencePage() {
		super(Activator.getDefault().getMessages().getString("preferences.image.title"), GRID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {

		MyBooleanFieldEditor enableImage = new MyBooleanFieldEditor(
				Configuration2Constants.IMAGE_ENABLE, Activator.getDefault().getMessages()
						.getString(PREFERENCES_IMAGE_CHECKLABEL),
				getFieldEditorParent());

		final FileFieldEditor imageSelect = new FileFieldEditor(
				Configuration2Constants.IMAGE_VALUE, Activator.getDefault().getMessages()
						.getString(PREFERENCES_IMAGE_LABEL),
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
				Configuration2Constants.IMAGE_ENABLE)) {
			imageSelect.setEnabled(false, getFieldEditorParent());
		}

		addField(enableImage);
		addField(imageSelect);
	}

	class MyBooleanFieldEditor extends BooleanFieldEditor {
		/**
		 * 
		 */
		public MyBooleanFieldEditor() {
			super();
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param name
		 * @param label
		 * @param parent
		 */
		public MyBooleanFieldEditor(String name, String label, Composite parent) {
			super(name, label, parent);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param name
		 * @param labelText
		 * @param style
		 * @param parent
		 */
		public MyBooleanFieldEditor(String name, String labelText, int style,
				Composite parent) {
			super(name, labelText, style, parent);
			// TODO Auto-generated constructor stub
		}

		public Button getChangeControl(Composite parent) {
			return super.getChangeControl(parent);
		}

	}

}
