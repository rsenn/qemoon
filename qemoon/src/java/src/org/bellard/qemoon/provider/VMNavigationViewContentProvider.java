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
package org.bellard.qemoon.provider;

import org.apache.log4j.Logger;
import org.bellard.qemoon.model.VM;
import org.bellard.qemoon.model.VMConfiguration;
import org.bellard.qemoon.model.VMs;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class VMNavigationViewContentProvider implements
		IStructuredContentProvider, ITreeContentProvider {

	private final static Logger logger = Logger
			.getLogger(VMNavigationViewContentProvider.class);

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		logger.debug("inputChanged: viewer=" + v + ", oldInput=" + oldInput
				+ ", newInput=" + newInput);
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof VMConfiguration) {
			return ((VMConfiguration) child).getVim();
		} else 
			if (child instanceof VM) {
			return ((VM) child).getVms();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof VM) {
			return new Object[] { ((VM) parent).getConfiguration() };
		} else 
			if (parent instanceof VMs) {
			return ((VMs) parent).getL().toArray();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof VM)
			return ((VM) parent).getConfiguration() != null;
		return false;
	}
}
