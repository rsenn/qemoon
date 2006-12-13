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
/*
 * QemuThread.java
 *
 * Created on January 19, 2006, 11:42 PM
 *
 */

package org.bellard.qemoon.runtime;

import java.util.Observable;

import org.apache.log4j.Logger;
import org.bellard.qemoon.commands.CommandGenerator;
import org.bellard.qemoon.model.VM;

/**
 * 
 * @author cbourque
 */
public class QemuThread extends Observable implements Runnable {

	private Thread thread;

	private CommandGenerator commandGenerator;

	private VM vim;

	private String qemuPath;

	private boolean daemon;

	private Logger logger;

	private RuntimeWrapper runtime;

	/**
	 * Constructor
	 * 
	 * @param vm
	 * @param qemuPath
	 * @param daemon
	 * @param logger
	 */
	public QemuThread(VM vm, String qemuPath, boolean daemon, Logger logger) {
		this.qemuPath = qemuPath;
		this.vim = vm;
		this.daemon = daemon;
		this.logger = logger;
		init();
	}

	/**
	 * @return the runtime
	 */
	public RuntimeWrapper getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime
	 *            the runtime to set
	 */
	public void setRuntime(RuntimeWrapper runtime) {
		this.runtime = runtime;
	}

	public void init() {
		this.commandGenerator = new CommandGenerator(qemuPath, vim
				.getPreferenceStore(), vim.getName());
	}

	public void start() {
		thread = new Thread(this);
		thread.setName(vim.getName());
		thread.setDaemon(daemon);
		thread.start();
	}

	public void run() {

		// build command line from configuration
		String[] cmd = commandGenerator.buidCommandArgument();
		// create runtime
		runtime = new RuntimeWrapper(vim.getName(), logger);
		try {
			// set the vm as started
			vim.setStarted(true);
			// launch the vm
			int exitValue = runtime.exec(cmd);
			if (countObservers() > 0) {
				setChanged();
				notifyObservers(new Integer(exitValue));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// set the vm as stopped
			vim.setStarted(false);
		}
	}

	/**
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}

	/**
	 * @param thread
	 *            the thread to set
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}

}
