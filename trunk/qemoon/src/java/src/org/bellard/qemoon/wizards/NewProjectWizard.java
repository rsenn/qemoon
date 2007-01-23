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

import java.io.IOException;

import org.apache.log4j.Logger;
import org.bellard.qemoon.Activator;
import org.bellard.qemoon.commands.QImageCommandGenerator;
import org.bellard.qemoon.constants.Configuration2Constants;
import org.bellard.qemoon.constants.ConfigurationOptionsConstants;
import org.bellard.qemoon.constants.PreferenceConstants;
import org.bellard.qemoon.model.VM;
import org.bellard.qemoon.runtime.RuntimeWrapper;
import org.bellard.qemoon.utils.ValidatorUtils;
import org.bellard.qemoon.views.VMNavigationView;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * @author France Telecom
 * @Copyright France Telecom S.A. 2006
 */
public class NewProjectWizard extends Wizard implements INewWizard {

	public final static Logger logger = Logger
			.getLogger(NewProjectWizard.class);

	public final static String ID = NewProjectWizard.class.getName();

	private IStructuredSelection selection;

	/**
	 * 
	 */
	public NewProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	private NewProjectWizardPage newPage;

	private ProjectConfigWizardPage configPage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		newPage = new NewProjectWizardPage(selection);
		addPage(newPage);
		configPage = new ProjectConfigWizardPage(selection);
		addPage(configPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

		String name = newPage.getNameText();
		// name check has been already done
		// create the new vm
		VM v = new VM(name);

		// if memory filled, set memory
		if (!ValidatorUtils.isEmptyOrNull(configPage.getMemoryText())) {
			v.getPreferenceStore().setValue(
					Configuration2Constants.MEMORY_VALUE,
					configPage.getMemoryText());
		}

		// create the disk
		String qemuimgpath = Activator.getDefault().getPreferenceStore()
				.getString(PreferenceConstants.PREFERENCES_QEMUIMG_PATH_VALUE);

		String diskpath = v.getProject().getProjectRelativePath().append(
				ConfigurationOptionsConstants.DEFAULT_DISK_NAME).toOSString();

		// if size filled, set the size
		int size = 0;
		if (!ValidatorUtils.isEmptyOrNull(configPage.getHardDiskSizeText())) {
			size = ValidatorUtils.formatInt(configPage.getHardDiskSizeText());
		}

		QImageCommandGenerator qicg = QImageCommandGenerator
				.buildCreateCommand(qemuimgpath, size, diskpath, 0, false, null);
		RuntimeWrapper rtw = new RuntimeWrapper(v.getName() + "-img", Logger
				.getLogger(v.getName() + "-img"));
		try {
			rtw.exec(qicg.buildCommandArgument());
			v.getPreferenceStore().setValue(
					Configuration2Constants.IMAGE_VALUE, diskpath);
			v.getPreferenceStore().setValue(
					Configuration2Constants.IMAGE_DEFAULT_VALUE, diskpath);

		} catch (IOException e) {
			String message = "io problem when executing qemu-img";
			logger.debug(message, e);
			throw new RuntimeException(message, e);
		} catch (InterruptedException e) {
			String message = "Thread problem when executing qemu-img";
			logger.debug(message, e);
			throw new RuntimeException(message, e);
		}

		// add the vm to the view
		VMNavigationView view = Activator.getDefault().getVMNavigationView();
		view.addVM(v);
		return true;
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}
