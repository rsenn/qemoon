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
package org.bellard.qemoon.views;

import org.apache.log4j.Logger;
import org.bellard.qemoon.editors.ConfigurationFormEditor;
import org.bellard.qemoon.inputs.ConfigurationFormEditorInput;
import org.bellard.qemoon.model.VM;
import org.bellard.qemoon.model.VMConfiguration;
import org.bellard.qemoon.model.VMs;
import org.bellard.qemoon.provider.VMNavigationViewContentProvider;
import org.bellard.qemoon.provider.VMNavigationViewLabelProvider;
import org.bellard.qemoon.wizards.RenameProjectWizard;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;

public class VMNavigationView extends ViewPart {

	private final static Logger logger = Logger
			.getLogger(VMNavigationView.class);

	public static final String ID = "qemoon.navigationView";

	private TreeViewer viewer;

	private IAction deleteProjectAction;

	private IAction createProjectAction;

	private IAction renameProjectAction;

	/**
	 * Load vms
	 * 
	 * @return
	 */
	// TODO refactor this method outside this view
	public VMs loadVMs() {

		IProject[] projects = null;
		VMs vms = new VMs();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		projects = workspace.getRoot().getProjects();

		// fill the view with vm projects
		for (IProject project : projects) {
			VM vim = new VM(project);
			vms.add(vim);
		}

		return vms;
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);

		viewer.setContentProvider(new VMNavigationViewContentProvider());
		viewer.setLabelProvider(new VMNavigationViewLabelProvider());
		// TODO to modify

		viewer.setInput(loadVMs());
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {

				// Get the class from string
				TreeSelection selection = (TreeSelection) event.getSelection();

				if (selection.getFirstElement() instanceof VM) {
					VM vm = (VM) selection.getFirstElement();
					logger.debug("VM double-clicked. vm.name=" + vm.getName());
					openVMConfigurationEditor((VMConfiguration) vm
							.getConfiguration());

				} else if (selection.getFirstElement() instanceof VMConfiguration) {

					VMConfiguration configuration = (VMConfiguration) selection
							.getFirstElement();
					logger.debug("VM Configuration double-clicked. vm.name="
							+ configuration.getVim().getName());
					openVMConfigurationEditor(configuration);

				}

			}

			/**
			 * @param configuration
			 */
			private void openVMConfigurationEditor(VMConfiguration configuration) {
				ConfigurationFormEditorInput input = new ConfigurationFormEditorInput(
						configuration);

				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					final ConfigurationFormEditor ed = (ConfigurationFormEditor) page
							.openEditor(input, ConfigurationFormEditor.ID, true);

					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					IResourceChangeListener listener = new IResourceChangeListener() {
						public void resourceChanged(IResourceChangeEvent event) {
							logger.debug("Update the title name...");
							ed.updateTitleName();
						}
					};
					workspace.addResourceChangeListener(listener);

				} catch (PartInitException e) {
					logger.debug("pd during the excecution", e);
					// TODO handle exception
				}
			}

		});

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResourceChangeListener listener = new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				logger.debug("Something changed!");
				viewer.refresh();
			}
		};
		workspace.addResourceChangeListener(listener);

		deleteProjectAction = new Action() {
			public void run() {
				ITreeSelection selection = (ITreeSelection) viewer
						.getSelection();
				logger.info("deleteProjectAction executed! selection="
						+ selection);

				VM vm = (VM) selection.getFirstElement();

				// ask confirmation
				String message = "Are you sure you want to delete project '"
						+ vm.getName() + "'?";
				boolean confirmed = MessageDialog.openQuestion(getSite()
						.getShell(), message, message);

				if (confirmed) {
					// delete the project
					try {
						vm.getProject().delete(true, new NullProgressMonitor());
						removeVM(vm);
						IWorkbenchPage page = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage();
						IEditorPart ep = page
								.findEditor(new ConfigurationFormEditorInput(vm
										.getConfiguration()));
						page.closeEditor(ep, false);
					} catch (CoreException e) {
						// TODO add specific exception handling
						String msg = "pb during project removal";
						logger.debug(msg, e);
						// TODO decide what to do with the exception
					}
				}
			}
		};
		deleteProjectAction.setText("Delete");
		deleteProjectAction.setToolTipText("Delete a project");
		// deleteProjectAction.setAccelerator(Action.findKeyCode("DELETE"));
		deleteProjectAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_DELETE));
		deleteProjectAction.setDisabledImageDescriptor(PlatformUI
				.getWorkbench().getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_DELETE_DISABLED));
		deleteProjectAction.setId("delete");

		// create project
		createProjectAction = ActionFactory.NEW.create(PlatformUI
				.getWorkbench().getActiveWorkbenchWindow());
		createProjectAction.setText("&New...");

		// createProjectAction = new Action() {
		// @Override
		// public void run() {
		//
		// // launch the create project wizard
		// // create wizard dialog & launch wizard dialog
		// WizardDialog dialog = new WizardDialog(getSite().getShell(),
		// new NewProjectWizard());
		// dialog.open();
		// }
		// };
		// createProjectAction.setText("Create...");
		// createProjectAction.setToolTipText("Create a project");
		// // createProjectAction.setAccelerator(SWT.INSERT);
		// createProjectAction.setImageDescriptor(PlatformUI.getWorkbench()
		// .getSharedImages().getImageDescriptor(
		// ISharedImages.IMG_TOOL_NEW_WIZARD));
		// createProjectAction.setDisabledImageDescriptor(PlatformUI
		// .getWorkbench().getSharedImages().getImageDescriptor(
		// ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
		// createProjectAction.setId("create");

		// rename project
		renameProjectAction = new Action() {
			@Override
			public void run() {
				VM vm = (VM) ((TreeSelection) viewer.getSelection())
						.getFirstElement();
				// launch the create project wizard
				// create wizard dialog & launch wizard dialog
				WizardDialog dialog = new WizardDialog(getSite().getShell(),
						new RenameProjectWizard(vm, VMNavigationView.this));
				dialog.open();
			}
		};
		renameProjectAction.setText("Rename...");
		renameProjectAction.setToolTipText("Rename a project");
		// renameProjectAction.setAccelerator(SWT.INSERT);
		renameProjectAction.setId("rename");

		hookContextMenu();

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				TreeSelection selection = (TreeSelection) event.getSelection();
				if (selection.getFirstElement() instanceof VM) {
					deleteProjectAction.setEnabled(true);
					renameProjectAction.setEnabled(true);
				} else {
					deleteProjectAction.setEnabled(false);
					renameProjectAction.setEnabled(false);
				}
			}
		});

	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				VMNavigationView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(createProjectAction);
		manager.add(renameProjectAction);
		manager.add(deleteProjectAction);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void removeVM(VM vim) {
		((VMs) viewer.getInput()).remove(vim);
		viewer.refresh();
	}

	public void addVM(VM vim) {

		if (viewer.getInput() == null) {
			viewer.setInput(loadVMs());
		} else {
			((VMs) viewer.getInput()).add(vim);
		}
		viewer.refresh();
	}

	/**
	 * @return the viewer
	 */
	public TreeViewer getViewer() {
		return viewer;
	}

	/**
	 * @param viewer
	 *            the viewer to set
	 */
	public void setViewer(TreeViewer viewer) {
		this.viewer = viewer;
	}
}
