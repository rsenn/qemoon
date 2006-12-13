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
package org.bellard.qemoon.utils;

import org.bellard.qemoon.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class FormUtils {

	public final static Text createTextLabelField(Composite composite,
			FormToolkit toolkit, String labelKey, TableWrapData td) {
		toolkit.createLabel(composite, Activator.getDefault().getMessages().getString(labelKey));
		Text text = toolkit.createText(composite, "", SWT.FLAT | SWT.BORDER);
		text.setLayoutData(td);
		return text;
	}

	public final static Text createTextLabelField(Composite composite,
			FormToolkit toolkit, String labelKey) {
		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
		return createTextLabelField(composite, toolkit, labelKey, td);
	}

	public final static Spinner createSpinner(Composite composite,
			FormToolkit toolkit, String labelKey) {
		TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
		toolkit.createLabel(composite, Activator.getDefault().getMessages().getString(labelKey));
		Spinner spinner = new Spinner(composite, SWT.FLAT | SWT.BORDER);
		spinner.setValues(128, 1, 100000, 0, 128, 128);
		spinner.setLayoutData(td);
		return spinner;
	}

	public final static CCombo createCCombo(Composite composite,
			FormToolkit toolkit, String labelKey, String[] items) {
		toolkit.createLabel(composite, Activator.getDefault().getMessages().getString(labelKey));
		CCombo combo = new CCombo(composite, SWT.FLAT | SWT.BORDER);
		combo.setItems(items);
		return combo;
	}

	public final static Text createFileChooser(final Composite composite,
			final FormToolkit toolkit, final String labelKey) {

		final Text text = createTextLabelField(composite, toolkit, labelKey);

		Button buttonSelectDir = toolkit.createButton(composite, "...",
				SWT.PUSH);
		buttonSelectDir.setToolTipText(Activator.getDefault().getMessages()
				.getString("configuration.disk.select"));
		buttonSelectDir.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				FileDialog fileDialog = new FileDialog(composite.getShell(),
						SWT.MULTI);

				// fileDialog.setFilterPath(fileFilterPath);
				fileDialog.setFilterExtensions(new String[] { "*.*" });
				fileDialog.setFilterNames(new String[] { "Any" });
				String file = fileDialog.open();

				text.setText(file.toString());
			}
		});

		return text;
	}

	public final static void fillCombo(CCombo combo, String text) {
		if (text == null) {
			combo.setText("");
		} else {
			combo.setText(text);
		}
	}

	public final static void fillText(Text text, String t) {
		if (t == null) {
			text.setText("");
		} else {
			text.setText(t);
		}
	}

	public final static void fillCheckButton(Button b, String t) {
		if (b.getStyle() == SWT.CHECK) {
			b.setSelection(new Boolean(t));
		}
	}

	public final static void fillUIComponent(Object o, String s) {
		if (o instanceof Text) {
			fillText((Text) o, s);
		} else if (o instanceof CCombo) {
			fillCombo((CCombo) o, s);
		} else if (o instanceof Button) {
			fillCheckButton((Button) o, s);

		}

	}


}
