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

import org.apache.log4j.Logger;
import org.bellard.qemoon.Activator;
import org.bellard.qemoon.constants.Configuration2Constants;
import org.bellard.qemoon.model.VM;
import org.bellard.qemoon.utils.ErrorUtils;
import org.bellard.qemoon.views.VMNavigationView;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * @author France Telecom
 * @Copyright France Telecom S.A. 2006
 */
public class NewExampleProjectWizard extends Wizard implements INewWizard {

	public final static Logger logger = Logger
			.getLogger(NewExampleProjectWizard.class);

	public final static String ID = NewExampleProjectWizard.class.getName();

	private IStructuredSelection selection;

	/**
	 * 
	 */
	public NewExampleProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	private NewProjectWizardPage newPage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		newPage = new NewProjectWizardPage(selection);
		addPage(newPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

		String name = newPage.getNameText();

		try {
			// name check has been already done
			// create the new vm
			VM v = new VM(name);
			// add the vm to the view
			// copy freedosPath
			IPath imagePath = Activator.getDefault().getPlatformPath()
					.append("linux.img");

			 // IFile destination = v.getProject().getFile("freedos.dsk");
			// destination.create(new BufferedInputStream(new FileInputStream(
			// freedosPath.toOSString())), false,
			// new NullProgressMonitor());
			v.getPreferenceStore().setValue(
					Configuration2Constants.IMAGE_CUSTOM, true);
			v.getPreferenceStore().setValue(
					Configuration2Constants.IMAGE_VALUE,
					imagePath.toString());
			VMNavigationView view = Activator.getDefault()
					.getVMNavigationView();

			view.addVM(v);

			return true;
		} catch (Exception e) {
			String message = "Problem during the project creation operation";
			logger.error(message, e);
		}
		// display error dialog
		ErrorUtils.displayErrorDialog("error.project.create");
		return false;
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
