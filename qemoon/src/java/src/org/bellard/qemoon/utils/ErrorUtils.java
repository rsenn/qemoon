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

import org.bellard.qemoon.Activator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

/**
 * @author Eric Bellard - eric.bellard@gmail.com
 * 
 */
public class ErrorUtils {

	/**
	 * Displays an error dialog box Find the title and message in the
	 * message.properties file. Add ".title" to the key for the title key Add
	 * ".message" to the key for the message key
	 * 
	 * @param messageKey
	 */
	public final static void displayErrorDialog(String messageKey) {
		displayErrorDialog(messageKey + ".title", messageKey + ".message");
	}

	/**
	 * Displays an error dialog box. Find the title and message in the
	 * message.properties file.
	 * 
	 * @param titleKey
	 * @param messageKey
	 */
	public final static void displayErrorDialog(String titleKey,
			String messageKey) {
		String title = Activator.getDefault().getMessages().getString(titleKey);
		String message = Activator.getDefault().getMessages().getString(
				messageKey);
		MessageDialog.openError(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), title, message);

	}

}
