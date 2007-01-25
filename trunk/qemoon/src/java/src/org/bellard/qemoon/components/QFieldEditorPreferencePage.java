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
package org.bellard.qemoon.components;

import org.bellard.qemoon.Activator;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 *
 */
public abstract class QFieldEditorPreferencePage extends FieldEditorPreferencePage {

	/**
	 * @param style
	 */
	public QFieldEditorPreferencePage(int style) {
		super(style);
	}

	/**
	 * @param title
	 * @param image
	 * @param style
	 */
	public QFieldEditorPreferencePage(String title, ImageDescriptor image, int style) {
		super(title, image, style);
	}

	/**
	 * @param title
	 * @param style
	 */
	public QFieldEditorPreferencePage(String title, int style) {
		super(title, style);
	}

	protected QBooleanFieldEditor createQBooleanFiedEditor(String value, String label) {
		QBooleanFieldEditor qboolfield = new QBooleanFieldEditor(value,
				Activator.getDefault().getMessages().getString(label),
				getFieldEditorParent());
	
		addField(qboolfield);
		return qboolfield;
	}

	/**
	 * Create hard drive
	 * 
	 * @param value
	 * @param label
	 */
	protected FileFieldEditor createFileFieldEditor(String value, String label, boolean emptyAllowed) {
		FileFieldEditor hd = new FileFieldEditor(value, Activator.getDefault()
				.getMessages().getString(label), getFieldEditorParent());
		hd.setEmptyStringAllowed(emptyAllowed);
		addField(hd);
		return hd;
	}

	protected ComboFieldEditor createComboFieldEditor(String value, String labelKey, String valuesKey) {
		ComboFieldEditor combo = new ComboFieldEditor(value, Activator
				.getDefault().getMessages().getString(labelKey),
				getFieldEditorParent(), Activator.getDefault().getMessages()
						.getStringArray(valuesKey));
	
		addField(combo);
		return combo;
	}



}
