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
package org.bellard.qemoon;

import org.apache.log4j.Logger;
import org.bellard.qemoon.utils.ProjectUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class EqemuNature implements IProjectNature {

	Logger logger = Logger.getLogger(EqemuNature.class);

	public final static String NATURE = "org.bellard.qemoon.nature";

	private IProject project;

	public void configure() throws CoreException {
		ProjectUtils.addNature(project, NATURE);
	}

	public void deconfigure() throws CoreException {
		ProjectUtils.removeNature(project, NATURE);
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;

	}

}
