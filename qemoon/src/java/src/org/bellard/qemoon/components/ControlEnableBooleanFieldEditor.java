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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class ControlEnableBooleanFieldEditor extends
		org.eclipse.jface.preference.BooleanFieldEditor {

	private Composite parent;

	private List<Control> toEnableList = new ArrayList<Control>();

	/**
	 * @param name
	 * @param label
	 * @param parent
	 */
	public ControlEnableBooleanFieldEditor(String name, String label, Composite parent) {
		super(name, label, parent);
		this.parent = parent;
	}

	/**
	 * @param name
	 * @param labelText
	 * @param style
	 * @param parent
	 */
	public ControlEnableBooleanFieldEditor(String name, String labelText, int style,
			Composite parent) {
		super(name, labelText, style, parent);
		this.parent = parent;
	}

	public Button getButton() {
		return getChangeControl(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.BooleanFieldEditor#doLoad()
	 */
	@Override
	protected void doLoad() {
		super.doLoad();
		enableList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.BooleanFieldEditor#doLoadDefault()
	 */
	@Override
	protected void doLoadDefault() {
		super.doLoadDefault();
		enableList();
	}

	public void enableList() {
		for (Control c : toEnableList) {
			c.setEnabled(getBooleanValue());
		}
	}

	public void addToEnable(final Control control) {
		toEnableList.add(control);
		getChangeControl(parent).addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection()) {
					control.setEnabled(true);
				} else {
					control.setEnabled(false);
				}
			}
		});
	}

}
