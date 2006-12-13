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
package org.bellard.qemoon.editors;

import org.apache.log4j.Logger;
import org.bellard.qemoon.constants.GlobalConstants;
import org.bellard.qemoon.forms.ConfigurationFormVMPage;
import org.bellard.qemoon.inputs.ConfigurationFormEditorInput;
import org.bellard.qemoon.model.VMConfiguration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class ConfigurationFormEditor extends FormEditor {

	Logger logger = Logger.getLogger(ConfigurationFormEditor.class);

	public final static String ID = "qemoon.ConfigurationFormEditor";

	private boolean dirtyState = false;

	private ConfigurationFormVMPage vimPage;

	// private ConfigurationFormMainPage mainPage;
	//
	// private ConfigurationFormUSBPage usbPage;
	//
	// private ConfigurationFormDebugExpertPage debugExpertPage;

	public ConfigurationFormEditor() {
		super();
	}

	protected FormToolkit createToolkit(Display display) {
		// Create a toolkit that shares colors between editors.
		return new FormToolkit(display);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	@Override
	protected void addPages() {
		try {
			vimPage = new ConfigurationFormVMPage(this);
			addPage(vimPage);
			// mainPage = new ConfigurationFormMainPage(this);
			// addPage(mainPage);
			// usbPage = new ConfigurationFormUSBPage(this);
			// addPage(usbPage);
			// debugExpertPage = new ConfigurationFormDebugExpertPage(this);
			// addPage(debugExpertPage);
		} catch (PartInitException e) {
			logger.debug(e);
		}
		IEditorInput input = getEditorInput();
		if (input instanceof ConfigurationFormEditorInput) {
			updateTitleName();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		logger.debug("do save...");
		//		String oldname = getVMConfiguration().getVim().getName();

		persistModel();
		// update the projet name if necessary
		// if (!oldname.equals(getVMConfiguration().getName())) {
		// // change title
		// setPartName(getVMConfiguration().getName() + " config");
		// // change editor
		// RessourcesUtils.renameProject(oldname, getVMConfiguration()
		// .getName());
		//		}
		setDirty(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		logger.debug("do save as...");
		// not allowed
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	public IFile getConfigFile() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(
				getVMConfiguration().getName());
		IFile config = project.getFile(GlobalConstants.VM_CONFIGURATION_NAME);
		return config;
	}

	public PreferenceStore getPreferenceStore() {
		return getVMConfiguration().getVim().getPreferenceStore();
	}

	public void persistModel() {
		logger.debug("persist the model in the configuration file...");
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		IWorkspaceRunnable operation = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				try {
					getPreferenceStore().save();
				} catch (Exception e) {
					String message = "pb when opening project";
					logger.debug(message, e);
					// TODO choice the exception propagation
				}

			}
		};
		try {
			workspace.run(operation, new NullProgressMonitor());
		} catch (CoreException e) {
			String message = "pb during operation execution";
			logger.debug(message, e);
			// decide what to do with the exception
		}

	}

	@Override
	public boolean isDirty() {
		return dirtyState;
	}

	public void setDirty(boolean d) {
		dirtyState = d;
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}

	@Override
	public void editorDirtyStateChanged() {
		setDirty(true);
	}

	public VMConfiguration getVMConfiguration() {

		return ((ConfigurationFormEditorInput) getEditorInput())
				.getVmConfiguration();
	}

	public void updateTitleName() {
		setPartName(getVMConfiguration().getName() + " config");
	}
	
	
}
