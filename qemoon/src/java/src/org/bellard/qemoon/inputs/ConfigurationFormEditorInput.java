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
/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.bellard.qemoon.inputs;

import org.bellard.qemoon.Activator;
import org.bellard.qemoon.model.VMConfiguration;

public class ConfigurationFormEditorInput extends FormEditorInput {

	private VMConfiguration vmConfiguration;

	public ConfigurationFormEditorInput(VMConfiguration vmConf) {
		this.vmConfiguration = vmConf;
	}

	/**
	 * @return the vmConfiguration
	 */
	public VMConfiguration getVmConfiguration() {
		return vmConfiguration;
	}

	/**
	 * @param vmConfiguration
	 *            the vmConfiguration to set
	 */
	public void setVmConfiguration(VMConfiguration vmConfiguration) {
		this.vmConfiguration = vmConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.inputs.FormEditorInput#getName()
	 */
	@Override
	public String getName() {

		return vmConfiguration.getVim().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bellard.qemoon.inputs.FormEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return Activator.getDefault().getMessages().getString(
				"configuration.tooltip");
	}

}
