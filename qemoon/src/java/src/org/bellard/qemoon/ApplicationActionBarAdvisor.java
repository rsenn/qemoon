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
package org.bellard.qemoon;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.intro.config.CustomizableIntroPart;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	CustomizableIntroPart toto;

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
	private IWorkbenchAction newVMAction;

	private IWorkbenchAction exitAction;

	private IWorkbenchAction introAction;

	private IWorkbenchAction aboutAction;
	
	private IWorkbenchAction saveAction;

	private IWorkbenchAction preferenceAction;
	

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
		
	}

	protected void makeActions(IWorkbenchWindow window) {

		newVMAction = ActionFactory.NEW.create(window);
		newVMAction.setText("&New...");
		register(newVMAction);

		saveAction = ActionFactory.SAVE.create(window);
		register(saveAction);
		
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);

		introAction = ActionFactory.INTRO.create(window);
		register(introAction);

		aboutAction = ActionFactory.ABOUT.create(window);
		register(introAction);

		preferenceAction = ActionFactory.PREFERENCES.create(window);
		register(preferenceAction);
		
	}

	protected void fillMenuBar(IMenuManager menuBar) {

		// file menu
		MenuManager fileMenu = new MenuManager("&File",
				IWorkbenchActionConstants.M_FILE);
		menuBar.add(fileMenu);
		fileMenu.add(newVMAction);
		//fileMenu.add(newConfAction);
		fileMenu.add(saveAction);
		fileMenu.add(exitAction);

		// window menu
		MenuManager windowMenu = new MenuManager("&Window",
				IWorkbenchActionConstants.M_WINDOW);
		menuBar.add(windowMenu);
		windowMenu.add(preferenceAction);
		
		// help menu
		MenuManager helpMenu = new MenuManager("&Help",
				IWorkbenchActionConstants.M_HELP);
		menuBar.add(helpMenu);
		helpMenu.add(introAction);
		helpMenu.add(aboutAction);

	}
}
