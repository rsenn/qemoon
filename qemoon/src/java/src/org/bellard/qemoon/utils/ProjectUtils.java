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

import org.apache.log4j.Logger;
import org.bellard.qemoon.EqemuNature;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class ProjectUtils {
	private final static Logger logger = Logger.getLogger(ProjectUtils.class);

	public final static void addNature(IProject project, String nature) {
		if (project == null || nature == null || nature.equals("")) {
			return;
		}
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = nature;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (CoreException e) {
			logger.debug(e);
		}
	}

	public final static void removeNature(IProject project, String nature) {
		if (project == null || nature == null || nature.equals("")) {
			return;
		}
		try {

			IProjectDescription description = project.getDescription();
			if (description.hasNature(nature)) {
				String[] natures = description.getNatureIds();
				String[] newNatures = new String[natures.length - 1];
				int i = 0;
				for (String n : natures) {
					if (!n.equals(nature)) {
						newNatures[i] = n;
						i++;
					}
				}
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
			}
		} catch (CoreException e) {
			logger.debug(e);
		}
	}

	public final static void initProject(IProject project) {
		boolean newProject = false;
		try {
			if (!project.exists()) {
				project.create(new NullProgressMonitor());
				newProject = true;
			}
			if (!project.isOpen()) {
				project.open(new NullProgressMonitor());
			}

			if (newProject && !project.hasNature(EqemuNature.NATURE)) {
				ProjectUtils.addNature(project, EqemuNature.NATURE);
			}
		} catch (CoreException e) {
			String message = "Pb when creating project";
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}

	}

	public final static IProject createProject(String name) {

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				name);
		initProject(project);
		return project;

	}
}
