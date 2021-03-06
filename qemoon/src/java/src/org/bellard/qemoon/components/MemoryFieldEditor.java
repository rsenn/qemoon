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
package org.bellard.qemoon.components;

import org.bellard.qemoon.Activator;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class MemoryFieldEditor extends IntegerFieldEditor {

	/**
	 * @param name
	 * @param labelText
	 * @param parent
	 * @param textLimit
	 */
	public MemoryFieldEditor(String name, String labelText, Composite parent,
			int textLimit) {
		super(name, labelText, parent, textLimit);
	}

	/**
	 * @param name
	 * @param labelText
	 * @param parent
	 */
	public MemoryFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}

	protected boolean checkState() {

		boolean supercheck = super.checkState();

		if (supercheck) {
			int value = getIntValue();
			if (value % 4 != 0) {
				showErrorMessage(Activator.getDefault().getMessages()
						.getString("preferences.memory.4multiple"));
				return false;
			} else {
				clearErrorMessage();
				return true;
			}
		}
		return supercheck;

	}

}
