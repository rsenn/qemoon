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
package org.bellard.qemoon.wizards;

import org.bellard.qemoon.Activator;
import org.bellard.qemoon.utils.ValidatorUtils;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author France Telecom
 * @Copyright France Telecom S.A. 2006
 */
public class ProjectConfigWizardPage extends WizardPage {

	private ISelection selection;

	private Text memoryText;

	private Text diskSizeText;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public ProjectConfigWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("New VM Configuration Project...");
		setDescription("This wizard creates a new VM Configuration project.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(Activator.getDefault().getMessages().getString(
				"preferences.memory.title"));

		memoryText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		memoryText.setLayoutData(gd);
		memoryText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("Mb");

		label = new Label(container, SWT.NULL);
		label.setText(Activator.getDefault().getMessages().getString(
				"preferences.harddisk.title"));

		diskSizeText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		diskSizeText.setLayoutData(gd);
		diskSizeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("Mb");

		memoryText.setFocus();
		setPageComplete(false);
		setControl(container);
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {

		String tmemory = memoryText.getText();
		if (!ValidatorUtils.isEmptyOrNull(tmemory)) {
			if (!ValidatorUtils.isInt(tmemory)) {
				updateStatus(Activator.getDefault().getMessages().getString(
						"preferences.memory.4multiple"));
				return;
			}
			int lmemory = ValidatorUtils.formatInt(tmemory);
			if (lmemory % 4 != 0) {
				updateStatus(Activator.getDefault().getMessages().getString(
						"preferences.memory.4multiple"));
				return;
			}
		}

		String tdisk = diskSizeText.getText();
		if (!ValidatorUtils.isEmptyOrNull(tdisk)) {
			if (!ValidatorUtils.isInt(tdisk)) {
				updateStatus(Activator.getDefault().getMessages().getString(
						"preferences.harddisk.integer"));
				return;
			}
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getMemoryText() {
		return memoryText.getText();
	}

	public String getHardDiskSizeText() {
		return diskSizeText.getText();
	}

	/**
	 * @return the selection
	 */
	public ISelection getSelection() {
		return selection;
	}

}
