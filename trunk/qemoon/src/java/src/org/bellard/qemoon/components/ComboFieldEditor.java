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

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
// TODO implements this class
public class ComboFieldEditor extends FieldEditor {

	private CCombo combo;

	private String oldValue;

	boolean isValid;

	private String[] items;

	/**
	 * Indicates whether the empty string is legal; <code>true</code> by
	 * default.
	 */
	private boolean emptyStringAllowed = true;

	/**
	 * The error message, or <code>null</code> if none.
	 */
	private String errorMessage;

	/**
	 * @param name
	 * @param labelText
	 * @param parent
	 */
	public ComboFieldEditor(String name, String labelText, Composite parent,
			String[] items) {
		init(name, labelText);
		oldValue = "";
		this.items = items;
		errorMessage = JFaceResources
				.getString("StringFieldEditor.errorMessage");//$NON-NLS-1$
		createControl(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
	 */
	@Override
	protected void adjustForNumColumns(int numColumns) {
		GridData gd = (GridData) combo.getLayoutData();
		gd.horizontalSpan = numColumns - 1;
		// We only grab excess space if we have to
		// If another field editor has more columns then
		// we assume it is setting the width.
		gd.grabExcessHorizontalSpace = gd.horizontalSpan == 1;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.widgets.Composite,
	 *      int)
	 */
	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		getLabelControl(parent);

		combo = getComboControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns - 1;
		gd.horizontalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		combo.setLayoutData(gd);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doLoad()
	 */
	@Override
	protected void doLoad() {
		if (combo != null) {
			String value = getPreferenceStore().getString(getPreferenceName());
			combo.setText(value);
			oldValue = value;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
	 */
	@Override
	protected void doLoadDefault() {
		if (combo != null) {
			String value = getPreferenceStore().getDefaultString(
					getPreferenceName());
			combo.setText(value);
		}
		valueChanged();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doStore()
	 */
	@Override
	protected void doStore() {
		getPreferenceStore().setValue(getPreferenceName(), combo.getText());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
	 */
	@Override
	public int getNumberOfControls() {
		return 2;
	}

	/**
	 * Returns this field editor's text control.
	 * <p>
	 * The control is created if it does not yet exist
	 * </p>
	 * 
	 * @param parent
	 *            the parent
	 * @return the text control
	 */
	public CCombo getComboControl(Composite parent) {
		if (combo == null) {
			combo = new CCombo(parent, SWT.SINGLE | SWT.BORDER);
			combo.setFont(parent.getFont());
			combo.setItems(items);
			combo.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}

				public void widgetSelected(SelectionEvent e) {
					valueChanged();
				}

			});

			combo.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent event) {
					combo = null;
				}
			});

		} else {
			checkParent(combo, parent);
		}
		return combo;
	}

	/**
	 * Informs this field editor's listener, if it has one, about a change to
	 * the value (<code>VALUE</code> property) provided that the old and new
	 * values are different.
	 * <p>
	 * This hook is <em>not</em> called when the text is initialized (or reset
	 * to the default value) from the preference store.
	 * </p>
	 */
	protected void valueChanged() {
		setPresentsDefaultValue(false);

		boolean oldState = isValid;

		refreshValidState();

		if (isValid != oldState) {
			fireStateChanged(IS_VALID, oldState, isValid);
		}

		String newValue = combo.getText();
		if (!newValue.equals(oldValue)) {
			fireValueChanged(VALUE, oldValue, newValue);
			oldValue = newValue;
		}
	}

	/**
	 * Checks whether the text input field contains a valid value or not.
	 * 
	 * @return <code>true</code> if the field value is valid, and
	 *         <code>false</code> if invalid
	 */
	protected boolean checkState() {
		boolean result = false;
		if (emptyStringAllowed) {
			result = true;
		}

		if (combo == null) {
			result = false;
		}

		String txt = combo.getText();

		result = (txt.trim().length() > 0) || emptyStringAllowed;

		// call hook for subclasses
		result = result && doCheckState();

		if (result) {
			clearErrorMessage();
		} else {
			showErrorMessage(errorMessage);
		}

		return result;
	}

	/**
	 * Hook for subclasses to do specific state checks.
	 * <p>
	 * The default implementation of this framework method does nothing and
	 * returns <code>true</code>. Subclasses should override this method to
	 * specific state checks.
	 * </p>
	 * 
	 * @return <code>true</code> if the field value is valid, and
	 *         <code>false</code> if invalid
	 */
	protected boolean doCheckState() {
		return true;
	}

	/**
	 * @return the combo
	 */
	public CCombo getCombo() {
		return combo;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#setEnabled(boolean, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void setEnabled(boolean enabled, Composite parent) {
		super.setEnabled(enabled, parent);
		getComboControl(parent).setEnabled(enabled);
	}

	
	
	
}
