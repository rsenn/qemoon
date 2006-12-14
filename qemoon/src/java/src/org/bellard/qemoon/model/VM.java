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
package org.bellard.qemoon.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.bellard.qemoon.Activator;
import org.bellard.qemoon.EqemuNature;
import org.bellard.qemoon.constants.GlobalConstants;
import org.bellard.qemoon.resources.VMConfigurationPropertiesPersister;
import org.bellard.qemoon.utils.ProjectUtils;
import org.bellard.qemoon.utils.ValidatorUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.PreferenceStore;

public class VM implements Comparable<VM>{

	public final static Logger logger = Logger.getLogger(VM.class);

	private String name;

	private PreferenceStore preferenceStore;

	private VMConfiguration configuration;

	private VMs vms;

	private boolean started;

	private IProject project;

	// TODO delete this line
	private String qemuImagePath;

	private Observer executionObserver;
	
	
	/**
	 * @return the vms
	 */
	public VMs getVms() {
		return vms;
	}

	/**
	 * @param vms
	 *            the vms to set
	 */
	public void setVms(VMs vms) {
		this.vms = vms;
	}

	/**
	 * @param name
	 */
	public VM(String name) {
		this.name = name;
		init();
	}

	/**
	 * @param name
	 */
	public VM(IProject project) {
		this.project = project;
		init();
	}

	public void init() {

		if (this.project == null && ValidatorUtils.isEmptyOrNull(this.name)) {
			String message = "the project and the name can't be null together!";
			logger.error(message);
		}

		boolean newProject = false;

		if (this.project == null) {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					this.name);
			newProject = true;
		}

		if (this.name == null) {
			this.name = this.project.getName();
		}

		IFile config = null;
		try {
			if (!this.project.exists())
				this.project.create(new NullProgressMonitor());

			if (!this.project.isOpen()) {
				this.project.open(new NullProgressMonitor());
			}

			if (newProject && !this.project.hasNature(EqemuNature.NATURE)) {
				ProjectUtils.addNature(this.project, EqemuNature.NATURE);
			}

			if (this.project.hasNature(EqemuNature.NATURE)) {
				config = this.project
						.getFile(GlobalConstants.VM_CONFIGURATION_NAME);

				// vm configuration
				VMConfiguration vmc = new VMConfiguration();
				this.setConfiguration(vmc);

				if (!config.exists()) {
					// create the config file
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					new VMConfigurationPropertiesPersister().saveProperties(
							Activator.getDefault().getOptions(), os);
					config.create(new ByteArrayInputStream(os.toByteArray()),
							true, new NullProgressMonitor());

				}
				logger.debug("read the config");

				preferenceStore = new PreferenceStore(config.getLocation()
						.toOSString());
				preferenceStore.load();

			}

		} catch (CoreException e) {
			String message = "Problem when opening project";
			logger.debug(message, e);
			throw new RuntimeException(message, e);
		} catch (IOException e) {
			String message = "Pb during the configuration read";
			logger.debug(message, e);
			throw new RuntimeException(message, e);
		}

	}

	/**
	 * @return the configuration
	 */
	public VMConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(VMConfiguration configuration) {
		this.configuration = configuration;
		configuration.setVim(this);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the started
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * @param started
	 *            the started to set
	 */
	public void setStarted(boolean started) {
		this.started = started;
	}

	public Collection getOptions() {
		// return getConfiguration().getOptions();
		return null;
	}

	/**
	 * @return the qemuImagePath
	 */
	public String getQemuImagePath() {
		return qemuImagePath;
	}

	/**
	 * @param qemuImagePath
	 *            the qemuImagePath to set
	 */
	public void setQemuImagePath(String qemuImagePath) {
		this.qemuImagePath = qemuImagePath;
	}

	/**
	 * @return the project
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(IProject project) {
		this.project = project;
	}

	/**
	 * @return the preferenceStore
	 */
	public PreferenceStore getPreferenceStore() {
		return preferenceStore;
	}

	/**
	 * @param preferenceStore
	 *            the preferenceStore to set
	 */
	public void setPreferenceStore(PreferenceStore preferenceStore) {
		this.preferenceStore = preferenceStore;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(VM o) {
		String first = this.getName();
		String second = null;
		if (o == null || o.getName() == null) {
			second="";
		}  else {
			second=o.getName();
		}
		if (first == null) {
			first="";
		}
		return first.compareTo(second);
	}

	/**
	 * @return the executionObserver
	 */
	public Observer getExecutionObserver() {
		return executionObserver;
	}

	/**
	 * @param executionObserver the executionObserver to set
	 */
	public void setExecutionObserver(Observer executionObserver) {
		this.executionObserver = executionObserver;
	};

}
