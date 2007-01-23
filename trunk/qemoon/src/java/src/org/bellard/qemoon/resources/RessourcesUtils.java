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
package org.bellard.qemoon.resources;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.bellard.qemoon.Activator;
import org.bellard.qemoon.constants.Configuration2Constants;
import org.bellard.qemoon.constants.GlobalConstants;
import org.bellard.qemoon.model.VM;
import org.bellard.qemoon.utils.ErrorUtils;
import org.bellard.qemoon.utils.ValidatorUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.PreferenceStore;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class RessourcesUtils {

	private final static Logger logger = Logger
			.getLogger(RessourcesUtils.class);

	public final static IWorkspaceRunnable createRenameVMOperation(
			final String oldname, final String newname) {

		// TODO move image if necessary IMAGE_DEFAULT_VALUE
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(oldname);

		IWorkspaceRunnable operation = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				try {
					if (!project.isOpen()) {
						project.open(monitor);
					}
					IProjectDescription description = project.getDescription();
					description.setName(newname);
					project.setDescription(description, monitor);
					project.move(description, true, monitor);
				} catch (CoreException e) {
					String message = "pb when renaming project";
					logger.debug(message, e);
					ErrorUtils.displayErrorDialog("error.project.rename");
				}
			}
		};
		return operation;
	}

	public final static void cloneProject(VM vm) {
		try {
			ResourcesPlugin.getWorkspace().run(createCloneOperation(vm),
					new NullProgressMonitor());
		} catch (CoreException e) {
			String message = "Problem when cloning the project";
			logger.debug(message, e);
			ErrorUtils.displayErrorDialog("error.project.clone");
		}

	}

	public final static IWorkspaceRunnable createCloneOperation(final VM vm) {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// find the new name
		final String oldName = vm.getName();
		String newTmpName = "CloneOf" + oldName;
		if (workspace.getRoot().getProject(newTmpName).exists()) {

			int i = 2;
			while (workspace.getRoot().getProject(newTmpName + i).exists()) {
				i++;
			}
			newTmpName += i;
		}

		// copy the project
		final String newName = newTmpName;
		IWorkspaceRunnable operation = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				try {
					vm.getProject().copy(
							workspace.getRoot().getProject(newName)
									.getFullPath(), true,
							new NullProgressMonitor());

				} catch (CoreException e) {
					String message = "pb when opening project";
					logger.debug(message, e);
					ErrorUtils.displayErrorDialog("error.project.clone");
				}

				// add the project in the navigation view
				VM newVM = new VM(newName);
				Activator.getDefault().getVMNavigationView().addVM(newVM);

				// modify the vm image
				String oldImagePath = vm.getPreferenceStore().getString(
						Configuration2Constants.IMAGE_VALUE);
				String oldVMPath = vm.getProject().getLocation().toOSString();
				logger.info("oldImagePath=" + oldImagePath);
				logger.info("oldVMPath=" + oldVMPath);
				if (!ValidatorUtils.isEmptyOrNull(oldImagePath)) {
					if (oldImagePath.contains(oldVMPath)) {
						String newImagePath = newVM.getProject().getLocation()
								.toOSString()
								+ oldImagePath.substring(oldVMPath.length());
						logger.info("newImagePath=" + newImagePath);
						newVM.getPreferenceStore().setValue(
								Configuration2Constants.IMAGE_VALUE,
								newImagePath);
						// TODO IMAGE_DEFAULT_VALUE
						newVM.getPreferenceStore().setValue(
								Configuration2Constants.IMAGE_CUSTOM,
								vm.getPreferenceStore().getString(
										Configuration2Constants.IMAGE_CUSTOM));
						try {
							newVM.getPreferenceStore().save();
						} catch (IOException e) {
							String message = "Pb when setting the new image path";
							logger.debug(message, e);
							ErrorUtils
									.displayErrorDialog("error.project.clone.image");
						}
					}
				}

			}
		};
		return operation;

	}

	public final static void renameProject(VM vm, final String newname) {
		String oldname = vm.getName();
		try {
			vm.setName(newname);
			logger.debug("rename project...");
			ResourcesPlugin.getWorkspace().run(
					createRenameVMOperation(oldname, newname),
					new NullProgressMonitor());
			vm.setProject(ResourcesPlugin.getWorkspace().getRoot().getProject(
					newname));
			IFile config = vm.getProject().getFile(
					GlobalConstants.VM_CONFIGURATION_NAME);

			PreferenceStore preferenceStore = new PreferenceStore(config
					.getLocation().toOSString());
			preferenceStore.load();
			vm.setPreferenceStore(preferenceStore);
		} catch (CoreException e) {
			String message = "pb during operation execution";
			logger.debug(message, e);
			ErrorUtils.displayErrorDialog("error.project.rename");
			vm.setName(oldname);
		} catch (IOException e) {
			String message = "pb during operation execution";
			logger.debug(message, e);
			ErrorUtils.displayErrorDialog("error.project.rename");
			vm.setName(oldname);
		}

	}

}
