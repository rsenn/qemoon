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

import org.bellard.qemoon.model.VM;
import org.bellard.qemoon.resources.RessourcesUtils;
import org.bellard.qemoon.views.VMNavigationView;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class RenameProjectWizard extends Wizard implements IWorkbenchWizard {

	private NewProjectWizardPage newPage;

	private IStructuredSelection selection;

	private VM vm;

	private VMNavigationView viewer;

	public RenameProjectWizard(VM vm, VMNavigationView viewer) {
		this.vm = vm;
		this.viewer = viewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		newPage = new NewProjectWizardPage(selection);
		newPage.setOldName(vm.getName());
		addPage(newPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

		if (vm != null) {
			String newName = newPage.getNameText();

			viewer.removeVM(vm);
			try {
				RessourcesUtils.renameProject(vm, newName);
				vm.setName(newName);
				viewer.addVM(vm);
			} catch (RuntimeException e) {
				viewer.addVM(vm);
			}
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;

	}

}
